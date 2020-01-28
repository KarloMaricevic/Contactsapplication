package my.app.contactsapplication.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import my.app.contactsapplication.core.InternalMviIntent
import my.app.contactsapplication.domain.Contact
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ContactRepository @Inject constructor(val context : Context)  {

    private val mContentResolver = context.contentResolver!!

    fun getAllContacts() : Single<List<List<Contact>>>
    {
        val query : Single<List<List<Contact>>> = Single.create {
            val contactList = ArrayList<Contact>()
            val favoritesList = ArrayList<Contact>()

            val cur  =
                mContentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    arrayOf(
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.LOOKUP_KEY,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.STARRED,
                        ContactsContract.Contacts.PHOTO_ID
                    ),
                    null,
                    null,
                    null
                )

            while (cur != null && cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val stared = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.STARRED))
                val thumbnailUri = getPhotoThumbnaul(id.toLong())
                contactList.add(Contact(id.toInt(),name,stared,null,null,thumbnailUri))
                if (stared == 1)
                {
                    favoritesList.add(Contact(id.toInt(),name,stared,null,null,thumbnailUri))
                }
            }
            cur?.close()
            it.onSuccess(listOf(contactList.sortedBy { it.mName },favoritesList.sortedBy { it.mName }))
        }
        return query.subscribeOn(Schedulers.io())
    }





    private fun getPhotoThumbnaul(contactId : Long) : Uri {
        val personUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactId)
        return Uri.withAppendedPath(personUri,ContactsContract.Contacts.Photo.DISPLAY_PHOTO)
    }



}