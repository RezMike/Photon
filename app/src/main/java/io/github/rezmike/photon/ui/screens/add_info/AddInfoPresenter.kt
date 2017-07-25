package io.github.rezmike.photon.ui.screens.add_info

import android.os.Bundle
import android.text.TextUtils
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.screens.AbstractPresenter
import io.github.rezmike.photon.utils.DaggerService
import mortar.MortarScope
import java.lang.StringBuilder

class AddInfoPresenter(val photoUri: String, var albumIdSelected: String?) : AbstractPresenter<AddInfoView, AddInfoModel, AddInfoPresenter>() {

    private val tags = ArrayList<String>()

    override fun initDagger(scope: MortarScope) {
        DaggerService.getDaggerComponent<AddInfoScreen.Component>(scope).inject(this)
    }

    override fun initActionBar() {
        rootPresenter.ActionBarBuilder()
                .setBackArrow(true)
                .setTitle(view.context.getString(R.string.add_info_title))
                .build()
    }

    override fun onLoad(savedInstanceState: Bundle?) {
        super.onLoad(savedInstanceState)
        view?.setPhoto(photoUri)
        model.getAlbumList()
                .subscribe({ view?.initListAlbum(it, albumIdSelected) }, { getRootView()?.showError(it) })
        view.initFlexBox(tags)
    }

    fun onClickItem(albumId: String) {
        if (albumIdSelected == albumId) {
            albumIdSelected = null
        } else {
            albumIdSelected = albumId
        }
        view.selectAlbum(albumIdSelected)
    }

    fun onClickDeleteTag(item: String) {
        val index = tags.indexOf(item)
        tags.removeAt(index)
        view.initFlexBox(tags)
    }

    fun onClickAddTag(tag: String) {
        if (!TextUtils.isEmpty(tag)) {
            val builder = StringBuilder(tag.replace(" ", "")).trim()
            val formatTag = builder.toString().toLowerCase()
            if (!tags.contains(formatTag)) {
                tags.add(formatTag)
                view.addTagOnFlexBox(formatTag)
            }
            view.clearTagEditText()
        }
    }
}
