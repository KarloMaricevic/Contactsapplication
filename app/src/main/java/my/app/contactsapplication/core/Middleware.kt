package my.app.contactsapplication.core

import io.reactivex.Observable
import java.util.*

interface Middleware<I : MviIntent,S> {
    fun bind(intents : Observable<I>,state : Observable<S>) : Observable<I>
}