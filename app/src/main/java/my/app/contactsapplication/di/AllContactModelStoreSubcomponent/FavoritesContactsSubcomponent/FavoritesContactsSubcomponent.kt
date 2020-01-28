package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.FavoritesContactsSubcomponent

import dagger.Subcomponent
import my.app.contactsapplication.ui.ContactsListFragment.FavoritesContactsFragment


@Subcomponent
    (modules = [FavoritesContactsViewModelModule::class])
interface FavoritesContactsSubcomponent {
    fun inject(favoritesContactsFragment: FavoritesContactsFragment)

    @Subcomponent.Factory
    interface Factory
    {
        fun create() : FavoritesContactsSubcomponent
    }

}