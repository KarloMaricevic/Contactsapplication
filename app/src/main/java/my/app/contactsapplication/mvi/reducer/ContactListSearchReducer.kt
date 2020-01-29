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
            is ContactListMviIntent.RequestReedPermission ->
                ContactListViewState().copy(
                    requestReedPermission = true
                )
            is SearchInternalMviIntent.SearchingContacts ->
                ContactListViewState().copy(
                    isLoadingContacts = true
                )
            is SearchInternalMviIntent.SearchContactSuccess ->
                ContactListViewState().copy(
                    allContactList = intent.allContactList,
                    favoritesContactsList = intent.favoritesContactList
                )
            is SearchInternalMviIntent.SearchContactFailure ->
                ContactListViewState().copy(
                    errorLoadingContacts = true
                )
            is FilterInternalMviIntent.FilteringContacts ->
                state.copy(
                    isFilteringContacts = true,
                    query = intent.query
                )
            is FilterInternalMviIntent.FilteringSuccessful ->
                state.copy(
                    filteredAllContactsList = intent.filteredAllContactList,
                    filteredFavoritesContactList = intent.filteredFavoritesContactList
                )
            is FilterInternalMviIntent.FilteringFailed ->
                state.copy(
                    errorFilteringContacts = true,
                    query = intent.query
                )
            else -> {
                return state
            }
        }
    }
}
