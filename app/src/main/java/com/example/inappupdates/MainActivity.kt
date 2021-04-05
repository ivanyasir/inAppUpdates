package com.example.inappupdates

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

private const val UPDATE_REQUEST_CODE = 123

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            Log.e("update",""+ it.updateAvailability())
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                Log.e("update","update available")
                appUpdateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this,
                    UPDATE_REQUEST_CODE
                )
            }
        }.addOnSuccessListener {
            Log.e("update", "Failed to check for update: $it")
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val shortcutManager = getSystemService(ShortcutManager::class.java)
//
//            if (shortcutManager!!.isRequestPinShortcutSupported) {
//                // Assumes there's already a shortcut with the ID "my-shortcut".
//                // The shortcut must be enabled.
//                val pinShortcutInfo = ShortcutInfo.Builder(this, "my-shortcut").build()
//
//                // Create the PendingIntent object only if your app needs to be notified
//                // that the user allowed the shortcut to be pinned. Note that, if the
//                // pinning operation fails, your app isn't notified. We assume here that the
//                // app has implemented a method called createShortcutResultIntent() that
//                // returns a broadcast intent.
//                val pinnedShortcutCallbackIntent =
//                    shortcutManager.createShortcutResultIntent(pinShortcutInfo)
//
//                // Configure the intent so that your app's broadcast receiver gets
//                // the callback successfully.For details, see PendingIntent.getBroadcast().
//                val successCallback = PendingIntent.getBroadcast(
//                    this, /* request code */ 0,
//                    pinnedShortcutCallbackIntent, /* flags */ 0
//                )
//
//                shortcutManager.requestPinShortcut(
//                    pinShortcutInfo,
//                    successCallback.intentSender
//                )
//            }
//        }

    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                Log.e("update","update available on resume")
                appUpdateManager.startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE, this, UPDATE_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_CANCELED) {
        finish()
    }
    }
}