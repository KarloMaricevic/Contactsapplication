package my.app.contactsapplication.di.AllContactModelStoreSubcomponent.ContactListSubcomponent

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import my.app.contactsapplication.di.ViewModelKey
import my.app.contactsapplication.viewmodels.ContactListViewModel

@Module
abstract class ContactListViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(ContactListViewModel::class)
    abstract fun bindViewModel(contactListViewModel: ContactListViewModel) : ViewModel
}