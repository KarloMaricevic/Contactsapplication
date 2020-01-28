package my.app.contactsapplication

import android.app.Application
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.AllContactModelStoreSubcomponent
import my.app.contactsapplication.di.AllContactModelStoreSubcomponent.ContactListSubcomponent.ContactListSubcomponent
import my.app.contactsapplication.di.AppComponent
import my.app.contactsapplication.di.DaggerAppComponent

class BaseApplication : Application() {

    lateinit var mAppComponent : AppComponent
    private var mAllContactModelStoreSubcomponent: AllContactModelStoreSubcomponent? = null

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.factory().create(this)
    }

    fun getAllContactModelStoreSubcomponent() : AllContactModelStoreSubcomponent{
        if(mAllContactModelStoreSubcomponent == null)
        {
            mAllContactModelStoreSubcomponent = mAppComponent.getAllContactModelStoreSubcomponent().create()
        }
        return mAllContactModelStoreSubcomponent!!
    }

    fun deleteAllContactModelStoreSubcomponent(){
        mAllContactModelStoreSubcomponent =  null
    }


}