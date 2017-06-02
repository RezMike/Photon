package io.github.rezmike.foton.ui.abstracts

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Screen(val value: Int)
