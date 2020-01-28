package my.app.contactsapplication.ui.ContactsListFragment

import my.app.contactsapplication.core.MviIntent

sealed class ContactListMviIntent : MviIntent {
    object ContactListFragmentResumed : ContactListMviIntent()
    data class QueryMviIntent(val query : String) : ContactListMviIntent()
    object InitializeContacts : ContactListMviIntent()
}