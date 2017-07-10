package io.github.rezmike.photon.ui.abstracts

import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wang.avi.AVLoadingIndicatorView
import io.github.rezmike.photon.R

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
            dismissProgress()
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
        val progress = progressDialog?.findViewById(R.id.progress) as AVLoadingIndicatorView?
        progress?.smoothToShow()
    }

    fun hideProgress() {
        if (progressDialog == null) return
        isDialogShowing = false;
        dismissProgress()
    }

    private fun dismissProgress() {
        val progress = progressDialog?.findViewById(R.id.progress) as AVLoadingIndicatorView?
        progress?.smoothToHide()
        progressDialog?.dismiss()
    }
}
