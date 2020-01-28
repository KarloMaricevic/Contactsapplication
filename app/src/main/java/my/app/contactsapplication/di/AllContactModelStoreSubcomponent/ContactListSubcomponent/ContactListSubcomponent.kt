package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.ContactListSubcomponent

import dagger.Subcomponent
import my.app.contactsapplication.ui.ContactsListFragment.ContactListFragment


@Subcomponent(
    modules = [ContactListViewModelModule::class]
)
interface ContactListSubcomponent {
    fun inject(allContactListFragment: ContactListFragment)

    @Subcomponent.Factory
    interface Factory
    {
        fun create() : ContactListSubcomponent
    }

}