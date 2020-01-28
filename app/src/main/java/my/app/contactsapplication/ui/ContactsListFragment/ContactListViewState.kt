package my.app.contactsapplication.ui.ContactsListFragment

import android.drm.DrmStore
import my.app.contactsapplication.domain.Contact

sealed class ContactListViewState
    object RequestReedPermission : ContactListViewState()
    object LoadingContacts : ContactListViewState()
    data class FilteringContacts(val allContactList: List<Contact>,val favoriteContactList : List<Contact>, val query: String) : ContactListViewState()
    data class ContactsLoaded (val allContactList : List<Contact>,val favoriteContactList : List<Contact>,val query: String?,val filteredAllContactList : List<Contact>?,val filteredFavoritesContactList : List<Contact>?): ContactListViewState()
    data class ErrorLoadingContacts (val error : Throwable?) : ContactListViewState()
    data class ErrorFilteringContacts(val contactList: List<Contact>,val favoriteContactList : List<Contact>,val query: String,val throwable: Throwable) : ContactListViewState()
