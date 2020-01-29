package my.app.contactsapplication.ui.ContactsListFragment


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.queryTextChanges
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import my.app.contactsapplication.BaseApplication
import my.app.contactsapplication.R
import my.app.contactsapplication.adapter.ALL_CONTACTS_PAGE_INDEX
import my.app.contactsapplication.adapter.ContactListPagerAdapter
import my.app.contactsapplication.adapter.FAVORITES_CONTACTS_PAGE_INDEX
import my.app.contactsapplication.databinding.FragmentContactListBinding
import my.app.contactsapplication.core.MviIntent
import my.app.contactsapplication.core.MviView
import my.app.contactsapplication.utils.PermissionChecker
import my.app.contactsapplication.viewmodels.ContactListViewModel
import my.app.contactsapplication.viewmodels.ViewModelProviderFactory
import java.io.InvalidClassException
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject

const val REED_CONTACT_PERMISSION_CODE = 14

class ContactListFragment : Fragment() ,ContactListView, SearchView.OnQueryTextListener {



    @Inject
    lateinit var mViewModelProviderFactory: ViewModelProviderFactory

    private lateinit var mBinding : FragmentContactListBinding

    private lateinit var mViewModelStore : ContactListViewModel

    private val mIntentPusher = PublishRelay.create<ContactListMviIntent>()

    private val mDisposables = CompositeDisposable()

    private  var mSnackbar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as BaseApplication)
            .getAllContactModelStoreSubcomponent()
            .getContactListSubcomponentFactory()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModelStore = ViewModelProvider(this,mViewModelProviderFactory).get(ContactListViewModel::class.java)
        mBinding = FragmentContactListBinding.inflate(inflater)
        mBinding.viewPager.adapter = ContactListPagerAdapter(this)
        TabLayoutMediator(mBinding.tabs,mBinding.viewPager){ tab,position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        return mBinding.root
    }


    override fun onResume() {
        super.onResume()
        mViewModelStore.bind(this as MviView<MviIntent, ContactListViewState>)
        mIntentPusher.accept(ContactListMviIntent.RequestReedPermission)


        (mBinding.toolbar.menu.findItem(R.id.action_search).actionView as SearchView).setOnQueryTextListener(this)
        mDisposables.add(mBinding.fabAddContact.clicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val action =
                    ContactListFragmentDirections.actionContactListFragmentToEditContactFragment()
                view!!.findNavController().navigate(action)
            }
        )
    }

    override fun onDestroy() {
        mViewModelStore.unbind()
        mDisposables.dispose()
        super.onDestroy()
    }


    private fun requestPermission()
    {
        if(!PermissionChecker.hasReedContactsPermission(context!!))
        {
            requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS),
                REED_CONTACT_PERMISSION_CODE)
        }
        else{
            mIntentPusher.accept(ContactListMviIntent.InitializeContacts)
        }
    }


    private fun getTabIcon(position: Int) : Int{
        return when(position) {
            ALL_CONTACTS_PAGE_INDEX -> R.drawable.contacts_tab_selector
            FAVORITES_CONTACTS_PAGE_INDEX -> R.drawable.favorites_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int) : String? {
        return  when(position){
            ALL_CONTACTS_PAGE_INDEX  ->  getString(R.string.contacts_title)
            FAVORITES_CONTACTS_PAGE_INDEX -> getString(R.string.favorites_title)
            else -> null
        }
    }



    override fun intents(): Observable<ContactListMviIntent> {
        return mIntentPusher
    }



    override fun render(state: ContactListViewState) {
        when {
            state.requestReedPermission -> {
                requestPermission()
            }
            state.errorLoadingContacts -> {
                initLoadingErrorSnackbar()
            }
            state.errorFilteringContacts -> {
                initFilteringErrorSnackbar(state)
            }
            else -> {
                mSnackbar?.dismiss()
                mSnackbar = null
            }
        }
    }

    private fun initLoadingErrorSnackbar(){
        mSnackbar = Snackbar
            .make(mBinding.root,R.string.snackbar_text_loading_contact_error ,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.snackbar_action_text) {mIntentPusher.accept(ContactListMviIntent.InitializeContacts)}
        mSnackbar?.show()
    }

    private fun initFilteringErrorSnackbar(state : ContactListViewState){
        mSnackbar = Snackbar
            .make(mBinding.root,R.string.snackbar_text_filtering_error ,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.snackbar_action_text) {mIntentPusher.accept(ContactListMviIntent.QueryMviIntent(state.query!!))}
        mSnackbar?.show()

    }




    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null )
            mIntentPusher.accept(ContactListMviIntent.QueryMviIntent(query))
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null )
            mIntentPusher.accept(ContactListMviIntent.QueryMviIntent(newText))
        return true
    }
}


interface ContactListView : MviView<ContactListMviIntent,ContactListViewState>{
}