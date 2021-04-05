package com.example.inappupdates

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

private const val UPDATE_REQUEST_CODE = 123

class MainActivity : AppCompatActivity() {

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        initView()

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

    }

    private fun initView() {
        val btnNext = findViewById<Button>(R.id.btn_next)
        btnNext.setOnClickListener {
            val i = Intent(this, ShortCutActivity::class.java)

            val pairList = Pair(it, "imageTransition")
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this, pairList)

            startActivity(i, options.toBundle())

        }
        val btnCircle = findViewById<ImageView>(R.id.img_circle)
        btnCircle.setOnClickListener {
            val i = Intent(this, ShortCutActivity::class.java)

            val pairList = Pair(it, "imageTransition")
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                this, pairList)

            startActivity(i, options.toBundle())

        }

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