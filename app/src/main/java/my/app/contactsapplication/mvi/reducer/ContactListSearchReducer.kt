package my.app.contactsapplication.mvi.reducer

import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.Reducer
import my.app.contactsapplication.mvi.middleware.FilterInternalMviIntent
import my.app.contactsapplication.mvi.middleware.SearchInternalMviIntent
import my.app.contactsapplication.ui.ContactsListFragment.*
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactListSearchReducer @Inject constructor() : Reducer<ContactListViewState, MviIntent> {
    override fun reduce(state: ContactListViewState, intent: MviIntent): ContactListViewState {
        return when(state) {
            is ContactsLoaded ->
                when (intent){
                    is SearchInternalMviIntent.SearchingAllContacts -> {
                        return LoadingContacts
                    }
                    is SearchInternalMviIntent.SearchAllContactSuccess -> {
                        return ContactsLoaded(intent.allContactList,intent.favoritesContactList,null,null,null)
                    }
                    is SearchInternalMviIntent.SearchAllContactFailure -> {
                        return ErrorLoadingContacts(intent.error)
                    }
                    is FilterInternalMviIntent.FilteringSuccessful -> return ContactsLoaded(state.allContactList,state.favoriteContactList,intent.query,intent.filteredAllContactList,intent.filteredFavoritesContactList)
                    else -> return state
                }
            is LoadingContacts ->{
                when (intent){
                    is SearchInternalMviIntent.SearchAllContactSuccess -> return ContactsLoaded(intent.allContactList,intent.favoritesContactList,null,null,null)
                    is SearchInternalMviIntent.SearchAllContactFailure -> return ErrorLoadingContacts(intent.error)
                    else -> return state
                }
            }
            is FilteringContacts -> {
                when(intent){
                    is FilterInternalMviIntent.FilteringSuccessful -> return ContactsLoaded(state.allContactList,state.favoriteContactList,state.query,intent.filteredAllContactList,intent.filteredFavoritesContactList)
                    is FilterInternalMviIntent.FilteringFailed ->return ErrorFilteringContacts(state.allContactList,state.favoriteContactList,state.query,intent.throwable)
                    else -> state
                }
            }
            else ->
                when(intent) {
                    is  ContactListMviIntent.InitializeContacts -> return LoadingContacts
                    else -> return state
                }
        }
    }
}