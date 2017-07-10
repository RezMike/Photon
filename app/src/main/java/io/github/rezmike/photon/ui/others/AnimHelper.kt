package io.github.rezmike.photon.ui.others

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

object AnimHelper {
    fun accentAnim(view: View) {
        val anim1 = ObjectAnimator.ofFloat(view, "translationY", 2f)
        val anim2 = ObjectAnimator.ofFloat(view, "translationY", -2f)
        val anim3 = ObjectAnimator.ofFloat(view, "translationY", 0f)
        val set = AnimatorSet()
        set.playSequentially(anim1, anim2, anim3)
        set.interpolator = LinearInterpolator()
        set.duration = 100
        set.start()
    }
}