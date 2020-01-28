package my.app.contactsapplication.mvi.store

import android.util.Log
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import my.app.contactsapplication.mvi.middleware.GetContactMiddleware
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.MviView
import my.app.contactsapplication.mvi.middleware.FilterContactMiddleware
import my.app.contactsapplication.repository.ContactRepository
import my.app.contactsapplication.ui.ContactsListFragment.ContactListViewState
import javax.inject.Inject

class AllContactStore @Inject constructor(private val modelStore: ContactListModelStore, private val contactRepository: ContactRepository){


    private val actions : PublishRelay<MviIntent> = PublishRelay.create<MviIntent>()



    fun wire() : Disposable {
        val disposable = CompositeDisposable()

        disposable += modelStore.process(actions)

        disposable += Observable.merge<MviIntent>(
            GetContactMiddleware(contactRepository).bind(actions, modelStore.getState()),
            FilterContactMiddleware().bind(actions,modelStore.getState()))
            .subscribe(
                {
                    actions.accept(it)
                },
                {e ->
                    throw e
                }
            )
        return disposable

    }

    fun bind(view : MviView<MviIntent,ContactListViewState>) : Disposable{
        val disposable = CompositeDisposable()
        disposable +=  modelStore.getState().observeOn(AndroidSchedulers.mainThread()).subscribe(
            {
                view.render(it)
            },

            {
                Log.e("error",it.message)
            }
        )
        disposable += view.intents().subscribe(actions::accept)
        return disposable
    }
}