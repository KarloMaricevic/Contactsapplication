package my.app.contactsapplication.core

import io.reactivex.Observable

interface MviView<I : MviIntent ,S> {
    fun intents() : Observable<I>
    fun render(state : S)
}