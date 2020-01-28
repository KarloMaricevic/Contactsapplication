package my.app.contactsapplication.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import my.app.contactsapplication.ui.ContactsListFragment.FavoritesContactsFragment
import my.app.contactsapplication.ui.ContactsListFragment.AllContactsFragment
import java.lang.IndexOutOfBoundsException


const val ALL_CONTACTS_PAGE_INDEX = 0
const val FAVORITES_CONTACTS_PAGE_INDEX =1

class ContactListPagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {



    private val tabFragmentCreators: Map<Int, () -> Fragment> = mapOf(
        ALL_CONTACTS_PAGE_INDEX to { AllContactsFragment() },
        FAVORITES_CONTACTS_PAGE_INDEX to { FavoritesContactsFragment() }
    )



    override fun getItemCount() = tabFragmentCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}