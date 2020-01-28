package my.app.contactsapplication.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactModelStoreSubcomponentFactory
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactModelStoreSubcomponent
import javax.inject.Singleton


@Singleton
@Component
    (modules = [AllContactModelStoreSubcomponentFactory::class])
interface AppComponent {

    fun getAllContactModelStoreSubcomponent() : AllContactModelStoreSubcomponent.Factory

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context) : AppComponent
    }

}