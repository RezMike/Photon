package io.github.rezmike.foton.ui.activities.root

import dagger.Provides
import io.github.rezmike.foton.di.scopes.RootScope

@dagger.Module
class RootModule {
    @Provides
    @RootScope
    fun provideRootPresenter() = RootPresenter()

    @Provides
    @RootScope
    fun provideAccountModel() = AccountModel()
}