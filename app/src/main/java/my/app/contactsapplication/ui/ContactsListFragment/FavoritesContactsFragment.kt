package my.app.contactsapplication.ui.ContactsListFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import my.app.contactsapplication.BaseApplication
import my.app.contactsapplication.R
import my.app.contactsapplication.adapter.ContactsAdapter
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.MviView
import my.app.contactsapplication.databinding.FragmentContactsRecyclerBinding
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.FavoritesContactsSubcomponent.FavoritesContactsViewModelModule
import my.app.contactsapplication.viewmodels.AllContactsViewModel
import my.app.contactsapplication.viewmodels.FavoritesContactViewModel
import my.app.contactsapplication.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class FavoritesContactsFragment : Fragment(),FavoritesContactView {

    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory

    private lateinit var mBinindg : FragmentContactsRecyclerBinding

    private  lateinit var mViewModelStore : FavoritesContactViewModel

    private lateinit var mAdapter : ContactsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAllContactModelStoreSubcomponent()
            .getFavoritesContactsSubcomponentFactory()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mViewModelStore = ViewModelProvider(parentFragment as ViewModelStoreOwner,mViewModelProviderFactory).get(FavoritesContactViewModel::class.java)
        mAdapter = ContactsAdapter(activity!!.applicationContext)
        mBinindg = FragmentContactsRecyclerBinding.inflate(inflater)
        mBinindg.recyclerViewContacts.layoutManager = LinearLayoutManager(context)
        mBinindg.recyclerViewContacts.adapter = mAdapter
        return mBinindg.root
    }


    override fun onResume() {
        super.onResume()
        mViewModelStore.bind(this as MviView<MviIntent, ContactListViewState>)
    }

    override fun onDestroy() {
        mViewModelStore.unbind()
        super.onDestroy()
    }


    override fun intents(): Observable<FavoritesContactsMviIntent> {
        return Observable.empty()
    }

    override fun render(state: ContactListViewState) {
        when(state) {
            is ContactsLoaded ->{

                if(state.filteredFavoritesContactList != null){
                    mAdapter.setData(state.filteredFavoritesContactList)
                }
                else{
                    mAdapter.setData(state.favoriteContactList)
                }
            }
        }
    }
}



interface FavoritesContactView : MviView<FavoritesContactsMviIntent,ContactListViewState>
