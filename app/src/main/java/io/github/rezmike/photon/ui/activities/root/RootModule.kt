package io.github.rezmike.photon.ui.activities.root

import dagger.Provides
import io.github.rezmike.photon.di.scopes.RootScope

@dagger.Module
class RootModule {
    @Provides
    @RootScope
    fun provideRootPresenter() = RootPresenter()

    @Provides
    @RootScope
    fun provideAccountModel() = AccountModel()
}