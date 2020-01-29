package my.app.contactsapplication.mvi.middleware

import my.app.contactsapplication.domain.Contact
import my.app.contactsapplication.core.InternalMviIntent

sealed class SearchInternalMviIntent :
    InternalMviIntent {
    object SearchingContacts : SearchInternalMviIntent()
    data class SearchContactSuccess(val allContactList: List<Contact>,val favoritesContactList : List<Contact>) : SearchInternalMviIntent()
    data class SearchContactFailure(val error: Throwable) : SearchInternalMviIntent()
}
