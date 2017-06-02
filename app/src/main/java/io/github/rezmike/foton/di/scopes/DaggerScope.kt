package io.github.rezmike.foton.di.scopes

import javax.inject.Scope
import kotlin.reflect.KClass

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class DaggerScope(val value: KClass<*>)
