package io.github.rezmike.photon.ui.screens.profile

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.photon.ui.abstracts.AbstractView
import io.github.rezmike.photon.utils.DaggerService

class ProfileView(context: Context, attrs: AttributeSet) : AbstractView<ProfilePresenter, ProfileView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<ProfileScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
