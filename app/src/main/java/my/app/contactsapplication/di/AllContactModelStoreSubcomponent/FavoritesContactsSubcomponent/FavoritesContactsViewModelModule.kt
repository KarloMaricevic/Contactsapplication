package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.FavoritesContactsSubcomponent

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.contactsapplication.di.ViewModelKey
import my.app.contactsapplication.viewmodels.FavoritesContactViewModel


@Module
abstract class FavoritesContactsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavoritesContactViewModel::class)
    abstract fun bindViewModel(favoritesContactViewModel: FavoritesContactViewModel) : ViewModel
}