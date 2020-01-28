package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactsSubcomponent

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.contactsapplication.di.ViewModelKey
import my.app.contactsapplication.viewmodels.AllContactsViewModel


@Module
abstract class AllContactsViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(AllContactsViewModel::class)
    abstract fun bindViewModel(allContactsViewModel: AllContactsViewModel) : ViewModel
}