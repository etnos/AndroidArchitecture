package com.example.androidmvi.mainUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmvi.R
import timber.log.Timber

/**
 * Simple activity. Load Main Fragment and nothing more
 */
class MainActivity : AppCompatActivity() {

    // just to make it simpler
    private val fragment = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.i("onCreate")

        // load Main Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, fragment)
            .commit()
    }
}
