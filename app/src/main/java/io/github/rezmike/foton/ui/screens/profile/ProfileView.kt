package io.github.rezmike.foton.ui.screens.profile

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class ProfileView(context: Context, attrs: AttributeSet) : AbstractView<ProfilePresenter, ProfileView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<ProfileScreen.Component>(context).inject(this)
    }
}
