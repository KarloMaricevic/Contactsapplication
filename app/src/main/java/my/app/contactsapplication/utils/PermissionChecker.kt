package my.app.contactsapplication.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class PermissionChecker {
    companion object{
        fun hasReedContactsPermission(context : Context) : Boolean{
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
                return true
            }
            return ContextCompat.checkSelfPermission(context,android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
        }

        fun hasWriteContactsPermission(context: Context) : Boolean{
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
                return true
            }
            return ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED


        }

    }





}