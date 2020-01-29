package my.app.contactsapplication.mvi.middleware

import my.app.contactsapplication.core.InternalMviIntent
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.domain.Contact

sealed class FilterInternalMviIntent : InternalMviIntent {
    data class FilteringContacts(val allContacts: List<Contact>, val query: String)  : FilterInternalMviIntent()
    data class FilteringSuccessful(val allContacts : List<Contact>,val query : String ,val filteredAllContactList : List<Contact>?,val filteredFavoritesContactList : List<Contact>?) : FilterInternalMviIntent()
    data class FilteringFailed(val allContacts: List<Contact>?,val query: String,val throwable: Throwable) : FilterInternalMviIntent()
}