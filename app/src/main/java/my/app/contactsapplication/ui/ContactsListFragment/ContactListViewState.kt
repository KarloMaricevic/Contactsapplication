package my.app.contactsapplication.ui.ContactsListFragment

import android.drm.DrmStore
import android.widget.MultiAutoCompleteTextView
import my.app.contactsapplication.domain.Contact

data class ContactListViewState(
    val requestReedPermission: Boolean = true,
    val isLoadingContacts : Boolean = false,
    val isFilteringContacts : Boolean = false,
    val allContactList : List<Contact> = listOf(),
    val favoritesContactsList : List<Contact> = listOf(),
    val filteredAllContactsList: List<Contact>? = null,
    val filteredFavoritesContactList: List<Contact>? = null,
    val errorLoadingContacts: Boolean  = false,
    val errorFilteringContacts : Boolean = false
)
