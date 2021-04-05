package com.example.inappupdates

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Explode
import android.view.Window

class ShortCutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        with(window) {
//            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
//            // set set the transition to be shown when the user enters this activity
//            enterTransition = Explode()
//            // set the transition to be shown when the user leaves this activity
//            exitTransition = Explode()
//        }
        setContentView(R.layout.activity_short_cut)

//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        val icon = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_inupdates)

        val intent = Intent()

        val launchIntent = Intent(this, MainActivity::class.java)

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent)
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "tes")
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon)

        setResult(RESULT_OK, intent)

    }
}