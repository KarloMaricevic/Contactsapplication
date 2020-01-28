package my.app.contactsapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import my.app.contactsapplication.R
import my.app.contactsapplication.databinding.ItemContactsBinding
import my.app.contactsapplication.di.scope.PerFragment
import my.app.contactsapplication.domain.Contact
import javax.inject.Inject

@PerFragment
class ContactsAdapter @Inject constructor(private val mContext : Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {



     private var mData : List<Contact> = ArrayList<Contact>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactsViewHolder(ItemContactsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contact = mData[position]
        val contactViewHolder = (holder as ContactsViewHolder)
        contactViewHolder.bind(contact)
        Glide.with(mContext)
            .load(contact.mThumbnailUri)
            .error(R.drawable.ic_person_filled)
            .apply(RequestOptions.circleCropTransform())
            .into(contactViewHolder.getImageView())
    }

    override fun getItemCount(): Int {
        return mData.count()

    }


    fun setData(newData : List<Contact>)
    {
        mData = newData
        notifyDataSetChanged()
    }

}

private class ContactsViewHolder (private val mBinding : ItemContactsBinding)
    : RecyclerView.ViewHolder(mBinding.root){


    fun bind(item : Contact) {
        mBinding.contact = item
        mBinding.executePendingBindings()
    }


    fun getImageView() : ImageView
    {
        return mBinding.thumbnailImageView
    }

}