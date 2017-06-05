package io.github.rezmike.foton.ui.screens.profile

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.mortar.ScreenScoper
import io.github.rezmike.foton.ui.abstracts.AbstractView

class ProfileView(context: Context, attrs: AttributeSet) : AbstractView<ProfilePresenter, ProfileView>(context,attrs){
    override fun initDagger(context: Context?) {
        ScreenScoper.getDaggerComponent<ProfileScreen.Component>(context!!).inject(this)
    }
}
