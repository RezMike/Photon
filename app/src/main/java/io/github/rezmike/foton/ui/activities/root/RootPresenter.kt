package io.github.rezmike.foton.ui.activities.root

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.dto.ActivityResultDto
import io.github.rezmike.foton.data.storage.dto.DialogResult
import io.github.rezmike.foton.ui.dialogs.login.LoginDialog
import io.github.rezmike.foton.ui.dialogs.login.LoginPresenter
import io.github.rezmike.foton.ui.others.MenuItemHolder
import io.github.rezmike.foton.utils.ConstantManager
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import rx.subjects.PublishSubject
import javax.inject.Inject

class RootPresenter : Presenter<RootActivity>() {

    @Inject
    lateinit var model: AccountModel

    private val activityResultDtoSub = PublishSubject.create<ActivityResultDto>()

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        initDialogs()
    }

    override fun dropView(view: RootActivity?) {
        dismissDialogs()
        super.dropView(view)
    }

    override fun extractBundleService(view: RootActivity): BundleService {
        return BundleService.getBundleService(view)
    }

    fun getActivityResultSubject() = activityResultDtoSub!!

    fun checkPermissionAndRequestIfNotGranted(permissions: Array<String>, requestCode: Int): Boolean {
        val allGranted = view?.isAllGranted(permissions, true) ?: return false
        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.requestPermissions(permissions, requestCode)
            }
        }
        return allGranted
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        activityResultDtoSub.onNext(ActivityResultDto(requestCode, resultCode, intent))
    }

    fun onRequestPermissionResult(requestCode: Int, permissions: Array<String>, grantRes: IntArray) {
        if (view != null && requestCode == ConstantManager.REQUEST_PERMISSION_CAMERA && grantRes.size == 2) {
            if (grantRes[0] == PackageManager.PERMISSION_DENIED) {
                view.showMessage(R.string.photo_upload_need_camera_permission)
            }
            if (grantRes[1] == PackageManager.PERMISSION_DENIED) {
                view.showMessage(R.string.photo_upload_need_write_permission)
            }
        }
    }

    fun isUserAuth() = model.isUserAuth()

    fun sharePhoto(link: String) {
        getRootView()?.sendSharingIntent(link)
    }

    //region ======================== Dialogs ========================

    private var loginDialogPresenter: LoginPresenter? = null

    private var loginDialog: LoginDialog? = null

    fun showLoginDialog(onResult: (DialogResult) -> Unit = {}) {
        loginDialogPresenter = LoginPresenter(model)
        loginDialogPresenter?.setOnResultListener {
            loginDialogPresenter = null
            loginDialog = null
            onResult(it)
        }
        loginDialog = LoginDialog(view!!)
        loginDialogPresenter?.takeView(loginDialog)
        loginDialogPresenter?.show()
    }

    private fun initDialogs() {
        if (loginDialogPresenter != null) {
            loginDialog = LoginDialog(view)
            loginDialogPresenter?.takeView(loginDialog)
            loginDialogPresenter?.show()
        }
    }

    private fun dismissDialogs() {
        if (loginDialogPresenter != null) {
            loginDialogPresenter?.dismiss()
        }
        loginDialog = null
    }

    //endregion

    fun onClickMain() {
        view?.showMainScreen()
    }

    fun onClickProfile() {
        if (model.isUserAuth()) {
            view?.showProfileScreen()
        } else {
            view?.showAuthScreen()
        }
    }

    fun onClickUpload() {
        view?.showUploadScreen()
    }

    fun getRootView(): RootActivity? = view

    inner class ActionBarBuilder {
        private var isGoBack = false
        private var isVisible = true
        private var title: String = ""
        private var overFlowIconRes: Int? = null
        private var items: ArrayList<MenuItemHolder> = ArrayList()

        fun setBackArrow(enable: Boolean): ActionBarBuilder {
            isGoBack = enable
            return this
        }

        fun setVisible(enable: Boolean): ActionBarBuilder {
            isVisible = enable
            return this
        }

        fun setTitle(title: String): ActionBarBuilder {
            this.title = title
            return this
        }

        fun setOverFlowIcon(iconRes: Int?): ActionBarBuilder {
            overFlowIconRes = iconRes
            return this
        }

        fun addAction(menuItem: MenuItemHolder): ActionBarBuilder {
            items.add(menuItem)
            return this
        }

        fun build() {
            val activity = view ?: return
            activity.setVisibleBar(isVisible)
            if (isVisible) {
                activity.setTitleBar(title)
                activity.setBackArrow(isGoBack)
                activity.setMenuItems(items)
                activity.setOverFlowIcon(overFlowIconRes)
            }
        }
    }
}