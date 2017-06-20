package io.github.rezmike.foton.ui.activities.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import dagger.Provides
import flow.Flow
import io.github.rezmike.foton.App
import io.github.rezmike.foton.BuildConfig

import io.github.rezmike.foton.R
import io.github.rezmike.foton.di.components.AppComponent
import io.github.rezmike.foton.di.scopes.SplashScope
import io.github.rezmike.foton.ui.abstracts.BaseActivity
import io.github.rezmike.foton.ui.activities.root.RootActivity
import io.github.rezmike.foton.utils.DaggerService
import kotlinx.android.synthetic.main.activity_splash.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        DaggerService.getDaggerComponent<SplashComponent>(this).inject(this)
        presenter.takeView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.init()
    }

    override fun onDestroy() {
        presenter.dropView(this)
        super.onDestroy()
    }

    override fun getSystemService(name: String?): Any {
        val activityScope = MortarScope.findChild(applicationContext, javaClass.name) ?:
                MortarScope.buildChild(applicationContext)
                        .withService(DaggerService.SERVICE_NAME, createComponent())
                        .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                        .build(javaClass.name)

        return if (activityScope.hasService(name)) {
            activityScope.getService(name)
        } else {
            super.getSystemService(name)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }

    private fun createComponent(): Any? = DaggerSplashActivity_SplashComponent.builder()
            .appComponent(App.appComponent)
            .splashModule(SplashModule())
            .build()

    //region ======================== ISplashView ========================

    fun showRootActivity() {
        val activityIntent = Intent(this, RootActivity::class.java)
        startActivity(activityIntent)
    }

    fun showMessage(message: String) {
        Snackbar.make(root_frame, message, Snackbar.LENGTH_LONG).show()
    }

    fun showMessage(stringResId: Int) {
        showMessage(getString(stringResId))
    }

    fun showError(e: Throwable) {
        if (BuildConfig.DEBUG) {
            showMessage(e.message ?: "Some error")
            e.printStackTrace()
        } else {
            showMessage(R.string.sorry_something_wrong)
            //FirebaseCrash.log("ROOT VIEW EXCEPTION")
            //FirebaseCrash.report(e)
        }
    }
    //endregion


    //region ======================== DI ========================

    @dagger.Module
    class SplashModule {
        @Provides
        @SplashScope
        fun providePresenter() = SplashPresenter()

        @Provides
        @SplashScope
        fun provideModel() = SplashModel()
    }

    @dagger.Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(SplashModule::class))
    @SplashScope
    interface SplashComponent {

        fun inject(activity: SplashActivity)

        fun inject(presenter: SplashPresenter)

    }

    //endregion
}
