package my.app.contactsapplication.mvi.reducer

import android.util.Log
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.Reducer
import my.app.contactsapplication.mvi.middleware.FilterInternalMviIntent
import my.app.contactsapplication.mvi.middleware.SearchInternalMviIntent
import my.app.contactsapplication.ui.ContactsListFragment.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactListSearchReducer @Inject constructor() : Reducer<ContactListViewState, MviIntent> {
    override fun reduce(state: ContactListViewState, intent: MviIntent): ContactListViewState {
        return when (intent)
        {
            is ContactListMviIntent.RequestReedPermission -> ContactListViewState()
            is SearchInternalMviIntent.SearchingAllContacts ->
                ContactListViewState().copy(
                    requestReedPermission = false,
                    isLoadingContacts = true
                )
            is SearchInternalMviIntent.SearchAllContactSuccess ->
                ContactListViewState().copy(
                    requestReedPermission = false,
                    allContactList = intent.allContactList,
                    favoritesContactsList = intent.favoritesContactList
                )
            is SearchInternalMviIntent.SearchAllContactFailure ->
                ContactListViewState().copy(
                    requestReedPermission = false,
                    errorLoadingContacts = true
                )
            is FilterInternalMviIntent.FilteringContacts ->
                state.copy(
                    isFilteringContacts = true
                )
            is FilterInternalMviIntent.FilteringSuccessful ->
                state.copy(
                    isFilteringContacts = false,
                    filteredAllContactsList = intent.filteredAllContactList,
                    filteredFavoritesContactList = intent.filteredFavoritesContactList
                )
            is FilterInternalMviIntent.FilteringFailed ->
                ContactListViewState().copy(
                    errorFilteringContacts = true
                )
            else -> {
                return state
            }
        }
    }
}
