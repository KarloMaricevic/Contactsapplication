package my.app.contactsapplication.mvi.middleware

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import my.app.contactsapplication.core.Middleware
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.repository.ContactRepository
import my.app.contactsapplication.ui.ContactsListFragment.ContactListMviIntent
import my.app.contactsapplication.ui.ContactsListFragment.ContactListViewState

class GetContactMiddleware(private val mContactRepository: ContactRepository) : Middleware<MviIntent,ContactListViewState> {

    override fun bind(intents: Observable<MviIntent>, state : Observable<ContactListViewState>) : Observable<MviIntent>
    {
        return intents.ofType<ContactListMviIntent.InitializeContacts>()
            .flatMap<MviIntent> {
                return@flatMap mContactRepository
                    .getAllContacts().toObservable()
                    .map<MviIntent> { SearchInternalMviIntent.SearchContactSuccess(it[0], it[1])}
                    .onErrorReturn {SearchInternalMviIntent.SearchContactFailure(Throwable("Error loading contacts"))}
                    .startWith(SearchInternalMviIntent.SearchingContacts)
            }
    }
}