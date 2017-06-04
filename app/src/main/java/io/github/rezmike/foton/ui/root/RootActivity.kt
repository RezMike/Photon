package io.github.rezmike.foton.ui.root

import android.os.Bundle
import com.squareup.picasso.Picasso
import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.modules.PicassoCacheModule
import io.github.rezmike.foton.di.scopes.RootScope
import io.github.rezmike.foton.ui.abstracts.BaseActivity

class RootActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
    }

    //region ======================== DI ========================

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RootModule::class, PicassoCacheModule::class))
    @RootScope
    interface RootComponent {
        fun inject(activity: RootActivity)

        fun inject(presenter: RootPresenter)

        //val rootPresenter: RootPresenter

        //fun getAccountModel() : AccountModel

        //fun getRootPresenter() : RootPresenter

        fun getPicasso(): Picasso
    }

    //endregion
}