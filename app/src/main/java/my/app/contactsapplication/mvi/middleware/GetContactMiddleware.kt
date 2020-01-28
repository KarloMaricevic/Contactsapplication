package my.app.contactsapplication.mvi.middleware

import io.reactivex.Observable
import io.reactivex.rxkotlin.ofType
import my.app.contactsapplication.core.Middleware
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.repository.ContactRepository
import my.app.contactsapplication.ui.ContactsListFragment.ContactListMviIntent
import my.app.contactsapplication.ui.ContactsListFragment.ContactListViewState
import my.app.contactsapplication.ui.ContactsListFragment.ErrorLoadingContacts
import java.lang.Exception

class GetContactMiddleware(val contactRepository: ContactRepository) : Middleware<MviIntent,ContactListViewState> {

    override fun bind(intents: Observable<MviIntent>, state : Observable<ContactListViewState>) : Observable<MviIntent>
    {
        return intents.ofType<ContactListMviIntent.InitializeContacts>()
            .flatMap<MviIntent> {
                contactRepository
                    .getAllContacts()
                    .toObservable()
                    .map<SearchInternalMviIntent> { return@map SearchInternalMviIntent.SearchAllContactSuccess(it.get(0),it.get(1)) }
                    .onErrorReturn { SearchInternalMviIntent.SearchAllContactFailure(Exception("Error loading contacts")) }
                    .startWith(SearchInternalMviIntent.SearchingAllContacts)
            }
    }
}