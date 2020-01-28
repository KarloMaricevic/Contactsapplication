package my.app.contactsapplication.core

interface Reducer<S,I : MviIntent> {
    fun reduce(state: S, intent: I) : S
}