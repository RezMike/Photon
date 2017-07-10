package io.github.rezmike.photon.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import dagger.Provides
import io.github.rezmike.photon.App
import io.github.rezmike.photon.BuildConfig
import io.github.rezmike.photon.R
import io.github.rezmike.photon.di.components.AppComponent
import io.github.rezmike.photon.di.scopes.SplashScope
import io.github.rezmike.photon.ui.activities.root.RootActivity
import io.github.rezmike.photon.utils.DaggerService
import kotlinx.android.synthetic.main.activity_splash.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

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

    override fun onPause() {
        super.onPause()
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

    fun showProgress() {
        progress.smoothToShow()
    }

    fun hideProgress() {
        progress.smoothToHide()
    }

    fun showMessage(message: String) {
        Snackbar.make(root_frame, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.main_repeate, { presenter.init() })
                .show()
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

    fun showRootActivity() {
        val activityIntent = Intent(this, RootActivity::class.java)
        startActivity(activityIntent)
        finish()
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
