package io.github.rezmike.foton.ui.activities.root

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.view.ViewPager
import io.github.rezmike.foton.R
import io.github.rezmike.foton.data.storage.dto.ActivityResultDto
import io.github.rezmike.foton.utils.ConstantManager
import io.github.rezmike.foton.utils.DaggerService
import mortar.MortarScope
import mortar.Presenter
import mortar.bundler.BundleService
import rx.subjects.PublishSubject

class RootPresenter : Presenter<RootActivity>() {

    companion object {
        val DEFAULT_MODE = 0
        val TAB_MODE = 1
    }

    private val activityResultDtoSub = PublishSubject.create<ActivityResultDto>()

    override fun onEnterScope(scope: MortarScope) {
        super.onEnterScope(scope)
        DaggerService.getDaggerComponent<RootActivity.RootComponent>(scope).inject(this)
    }

    override fun extractBundleService(view: RootActivity): BundleService {
        return BundleService.getBundleService(view)
    }

    fun getActivityResultSubject() = activityResultDtoSub

    fun checkPermissionAndRequestIfNotGranted(permissions: Array<String>, requestCode: Int): Boolean {
        val allGranted = view?.isAllGranted(permissions, true) ?: return false
        if (!allGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.requestPermissions(permissions, requestCode)
            }
        }
        return allGranted
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
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

    fun onClickMain() {
        view?.showMainScreen()
    }

    fun onClickProfile() {
        view?.showProfileScreen()
    }

    fun onClickUpload() {
        view?.showUploadScreen()
    }

    fun getRootView(): RootActivity? = view

    fun newActionBarBuilder() = ActionBarBuilder()

    inner class ActionBarBuilder {
        private var isGoBack = false
        private var isVisible = true
        private var title: CharSequence = ""
        private var overFlowIconRes: Int? = null
        private var items: MutableList<MenuItemHolder> = mutableListOf()
        private var pager: ViewPager? = null
        private var toolbarMode = DEFAULT_MODE

        fun setBackArrow(enable: Boolean): ActionBarBuilder {
            isGoBack = enable
            return this
        }

        fun setVisible(enable: Boolean): ActionBarBuilder {
            isVisible = enable
            return this
        }

        fun setTitle(title: CharSequence): ActionBarBuilder {
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

        fun setTab(pager: ViewPager): ActionBarBuilder {
            toolbarMode = TAB_MODE
            this.pager = pager
            return this
        }

        fun build() {
            val activity = view ?: return
            activity.setVisibleBar(isVisible)
            if (isVisible) {
                activity.setTitleBar(title)
                activity.setBackArrow(isGoBack)
                activity.setMenuItem(items.toList())
                activity.setOverFlowIcon(overFlowIconRes)
            }
            if (toolbarMode == TAB_MODE) {
                activity.setTabLayout(pager!!)
            } else {
                activity.removeTabLayout()
            }
        }
    }
}