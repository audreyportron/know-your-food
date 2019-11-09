package com.apo.template.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apo.template.R
import com.apo.template.domain.categories.Category
import com.apo.template.ui.product.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Launcher)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        startActivity(HomeActivity.getIntent(this))
        finish()
    }

    override fun onPause() {
        super.onPause()

    }

    private fun onSuccess(categories: List<Category>) {
        startActivity(HomeActivity.getIntent(this))
        finish()
    }

    private fun onError(t: Throwable) {
        AlertDialog.Builder(this)
            .create().apply {
                setTitle(R.string.error)
                setMessage(getString(R.string.error_message))
                setButton(
                    AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok)
                ) { dialog, which -> finish() }

            }.show()
    }
}
