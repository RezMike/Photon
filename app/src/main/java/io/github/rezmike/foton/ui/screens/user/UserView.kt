package io.github.rezmike.foton.ui.screens.user

import android.content.Context
import android.util.AttributeSet
import io.github.rezmike.foton.ui.abstracts.AbstractView
import io.github.rezmike.foton.utils.DaggerService

class UserView(context: Context, attrs: AttributeSet?) : AbstractView<UserPresenter, UserView>(context, attrs) {

    override fun initDagger(context: Context) {
        DaggerService.getDaggerComponent<UserScreen.Component>(context).inject(this)
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}
