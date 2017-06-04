package io.github.rezmike.foton.ui.abstracts

import android.view.View
import mortar.ViewPresenter
import javax.inject.Inject

open class AbstractPresenter<V : View, M : AbstractModel> : ViewPresenter<V>() {

    @Inject
    protected lateinit var mModel: M
}