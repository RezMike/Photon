package io.github.rezmike.foton.ui.root

import dagger.Provides
import io.github.rezmike.foton.di.scopes.RootScope

@dagger.Module
class RootModule {
    @Provides
    @RootScope
    fun provideRootPresenter() = RootPresenter()
}