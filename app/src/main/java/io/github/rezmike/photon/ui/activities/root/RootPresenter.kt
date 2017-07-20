package io.github.rezmike.photon.ui.activities.root

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import io.github.rezmike.photon.R
import io.github.rezmike.photon.data.storage.dto.ActivityResultDto
import io.github.rezmike.photon.data.storage.dto.DialogResult
import io.github.rezmike.photon.ui.dialogs.DialogManager
import io.github.rezmike.photon.ui.others.MenuItemHolder
import io.github.rezmike.photon.utils.ConstantManager
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import rx.subjects.PublishSubject
import javax.inject.Inject

class RootPresenter : Presenter<RootActivity>() {

    @Inject
    lateinit var model: AccountModel

    lateinit var dialogManager: DialogManager

    private val activityResultDtoSub = PublishSubject.create<ActivityResultDto>()

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
        dialogManager = DialogManager(model, this)
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        dialogManager.showHiddenDialogs(view)
    }

    override fun dropView(view: RootActivity?) {
        dialogManager.dismissDialogs()
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

    fun showLoginDialog(onResult: (DialogResult) -> Unit = {}) {
        dialogManager.showLoginDialog(view, onResult)
    }

    fun showRegisterDialog(onResult: (DialogResult) -> Unit) {
        dialogManager.showRegisterDialog(view, onResult)
    }

    fun showAlbumDialog(onResult: (DialogResult) -> Unit = {}) {
        dialogManager.showAlbumDialog(view, onResult)
    }

    fun showEditProfileDialog(onResult: (DialogResult) -> Unit = {}) {
        dialogManager.showEditProfileDialog(view, onResult)
    }

    fun showAvatarDialog(onResult: (DialogResult) -> Unit = {}) {
        dialogManager.showAvatarDialog(view, onResult)
    }

    //endregion

    //region ======================== Events ========================

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

    //endregion

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