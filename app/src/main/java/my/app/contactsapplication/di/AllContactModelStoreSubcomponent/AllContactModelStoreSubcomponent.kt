package my.app.contactsapplication.di.AllContactModelStoreSubcomponent

import dagger.Subcomponent
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactsSubcomponent.AllContactsSubcomponent
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactsSubcomponent.AllContactsSubcomponentFactory
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.ContactListSubcomponent.ContactListSubcomponent
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.ContactListSubcomponent.ContactListSubcomponentFactory
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.FavoritesContactsSubcomponent.FavoritesContactsSubcomponent
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.FavoritesContactsSubcomponent.FavoritesContactsSubcomponentFactory
import my.app.contactsapplication.di.scope.PerAllContactModelStoreSubcomponent


@PerAllContactModelStoreSubcomponent
@Subcomponent
    (modules = [ContactListSubcomponentFactory::class,
                AllContactsSubcomponentFactory::class,
                FavoritesContactsSubcomponentFactory::class])
interface AllContactModelStoreSubcomponent {

    fun getContactListSubcomponentFactory() : ContactListSubcomponent.Factory
    fun getAllContactSubcomponentFactory() : AllContactsSubcomponent.Factory
    fun getFavoritesContactsSubcomponentFactory() : FavoritesContactsSubcomponent.Factory

    @Subcomponent.Factory
    interface Factory
    {
        fun create() : AllContactModelStoreSubcomponent
    }

}