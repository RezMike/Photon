package io.github.rezmike.foton.ui.activities

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import io.github.rezmike.foton.R
import kotlinx.android.synthetic.main.progress_splash.*

open class BaseActivity : AppCompatActivity() {

    val IS_PROGRESS_SHOWING = "IS_PROGRESS_SHOWING"

    private var progressDialog: ProgressDialog? = null
    private var isDialogShowing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getBoolean(IS_PROGRESS_SHOWING)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean(IS_PROGRESS_SHOWING, isDialogShowing)
    }

    override fun onStart() {
        super.onStart()
        if (isDialogShowing) showProgress()
    }

    override fun onStop() {
        super.onStop()
        if (progressDialog?.isShowing ?: false) {
            isDialogShowing = true
            progressDialog?.dismiss()
        }
    }

    fun showProgress() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this, R.style.CustomDialog)
            progressDialog?.setCancelable(false)
            progressDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog?.show()
        progressDialog?.setContentView(R.layout.progress_splash)
        progress.show()
    }

    fun hideProgress() {
        if (progressDialog == null) return
        progress.hide()
        isDialogShowing = false;
        progressDialog?.dismiss()
    }
}
