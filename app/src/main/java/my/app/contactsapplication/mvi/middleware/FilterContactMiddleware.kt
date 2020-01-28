package my.app.contactsapplication.mvi.middleware

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.withLatestFrom
import my.app.contactsapplication.core.InternalMviIntent
import my.app.contactsapplication.core.Middleware
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.domain.Contact
import my.app.contactsapplication.ui.ContactsListFragment.*

class FilterContactMiddleware : Middleware<MviIntent,ContactListViewState> {
    override fun bind(
        intents: Observable<MviIntent>,
        state: Observable<ContactListViewState>
    ): Observable<MviIntent> {
        return intents
            .ofType<ContactListMviIntent.QueryMviIntent>()
            .withLatestFrom(state){ intent, state  ->
                 if(state is ContactsLoaded) {
                     val filteredAllContactsList = Contact.filterContactList(state.allContactList!!, intent.query)
                     val filteredFavoritesContactsList = Contact.filterContactList(state.favoriteContactList,intent.query)
                     return@withLatestFrom FilterInternalMviIntent.FilteringSuccessful(state.allContactList,intent.query,filteredAllContactsList,filteredFavoritesContactsList) as MviIntent
                 } else(
                     return@withLatestFrom FilterInternalMviIntent.FilteringFailed(null,intent.query,TypeCastException("state not ContactLoaded"))  )
                 }
             }

    }
