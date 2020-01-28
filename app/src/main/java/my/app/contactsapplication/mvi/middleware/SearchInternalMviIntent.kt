package my.app.contactsapplication.mvi.middleware

import my.app.contactsapplication.domain.Contact
import my.app.contactsapplication.core.InternalMviIntent

sealed class SearchInternalMviIntent :
    InternalMviIntent {
    object SearchingAllContacts : SearchInternalMviIntent()
    data class SearchAllContactSuccess(val allContactList: List<Contact>,val favoritesContactList : List<Contact>) : SearchInternalMviIntent()
    data class SearchAllContactFailure(val error: Throwable) : SearchInternalMviIntent()
}
