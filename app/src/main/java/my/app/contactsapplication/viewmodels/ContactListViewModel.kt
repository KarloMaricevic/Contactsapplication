package my.app.contactsapplication.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.MviView
import my.app.contactsapplication.mvi.store.AllContactStore
import my.app.contactsapplication.ui.ContactsListFragment.ContactListView
import my.app.contactsapplication.ui.ContactsListFragment.ContactListViewState
import javax.inject.Inject

class ContactListViewModel @Inject constructor(private val store : AllContactStore) : ViewModel() {
    private val wiring = store.wire()
    private var viewBinding : Disposable? = null


    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view : MviView<MviIntent,ContactListViewState>){
        viewBinding = store.bind(view)
    }

    fun unbind(){
        viewBinding?.dispose()
    }
}