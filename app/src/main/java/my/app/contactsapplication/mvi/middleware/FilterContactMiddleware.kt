package my.app.contactsapplication.mvi.middleware

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.withLatestFrom
import my.app.contactsapplication.core.Middleware
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.domain.Contact
import my.app.contactsapplication.ui.ContactsListFragment.*
import java.lang.Exception

class FilterContactMiddleware : Middleware<MviIntent,ContactListViewState> {
    override fun bind(
        intents: Observable<MviIntent>,
        state: Observable<ContactListViewState>
    ): Observable<MviIntent> {
        return intents
            .ofType<ContactListMviIntent.QueryMviIntent>()
            .withLatestFrom(state) { intent, latestState ->
                try {
                    if(intent.query != "") {
                        val filteredAllContactsList =
                            Contact.filterContactList(latestState.allContactList, intent.query)
                        val filteredFavoritesContactsList =
                            Contact.filterContactList(latestState.favoritesContactsList, intent.query)
                        return@withLatestFrom FilterInternalMviIntent.FilteringSuccessful(
                            latestState.allContactList,
                            intent.query,
                            filteredAllContactsList,
                            filteredFavoritesContactsList
                        )
                    } else{
                        return@withLatestFrom  FilterInternalMviIntent.FilteringSuccessful(latestState.allContactList,intent.query,null,null)
                    }
                }
            catch (e : Exception)
            {
                return@withLatestFrom FilterInternalMviIntent.FilteringFailed(latestState.allContactList,intent.query,e)
            }
            }
    }
}
