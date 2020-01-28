package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactsSubcomponent

import dagger.Subcomponent
import my.app.contactsapplication.ui.ContactsListFragment.AllContactsFragment

@Subcomponent
    (modules = [AllContactsViewModelModule::class])
interface AllContactsSubcomponent {
    fun inject(allContactsFragment: AllContactsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create() : AllContactsSubcomponent
    }

}