package my.app.contactsapplication.domain

import android.net.Uri
import androidx.room.*



data class Contact(

    val mUid: Int,

    val mName: String?,

    val mStared : Int,

    val mPhoneNumber: String?,

    val mPictureLink: String?,

    val mThumbnailUri: Uri?
) {
    fun hasName(query: String) : Boolean{
        if(mName != null && mName.contains(query,true))
            return true
        return false
    }




    companion object {

        fun filterContactList(contactList: List<Contact>, query: String): List<Contact> {
            val newContactList = ArrayList<Contact>()
            for (contact in contactList) {
                if (contact.hasName(query)) {
                    newContactList.add(contact)
                }
            }
            return newContactList
        }
    }

}