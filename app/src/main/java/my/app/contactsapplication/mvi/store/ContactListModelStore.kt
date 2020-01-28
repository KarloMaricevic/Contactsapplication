package my.app.contactsapplication.mvi.store

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.Disposable
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.di.scope.PerAllContactModelStoreSubcomponent
import my.app.contactsapplication.mvi.reducer.ContactListSearchReducer
import my.app.contactsapplication.ui.ContactsListFragment.ContactListViewState
import my.app.contactsapplication.ui.ContactsListFragment.RequestReedPermission
import javax.inject.Inject

@PerAllContactModelStoreSubcomponent
class ContactListModelStore @Inject constructor(private val reducer : ContactListSearchReducer) {
    private val state = BehaviorRelay.createDefault<ContactListViewState>(RequestReedPermission)
    lateinit var disposable: Disposable


    fun process(intents : PublishRelay<MviIntent>) : Disposable{
        disposable = intents
            .map {
                return@map reducer.reduce(state.value!!,it)
            }
            .subscribe({
                state.accept(it)
            },
                {

            })
        return disposable
    }



    fun getState() = state

}