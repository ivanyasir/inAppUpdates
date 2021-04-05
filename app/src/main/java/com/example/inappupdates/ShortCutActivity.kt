package com.example.inappupdates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShortCutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_short_cut)

        val icon = Intent.ShortcutIconResource.fromContext(this, R.mipmap.ic_inupdates)

        val intent = Intent()

        val launchIntent = Intent(this, MainActivity::class.java)

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent)
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "tes")
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon)

        setResult(RESULT_OK, intent)

    }
}