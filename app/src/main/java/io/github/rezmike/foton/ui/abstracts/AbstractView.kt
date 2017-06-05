package io.github.rezmike.foton.ui.abstracts

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
abstract class AbstractView<P : AbstractPresenter<V, *, P>, V : AbstractView<P, V>>(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    @Inject
    lateinit var presenter: P

    init {
        @Suppress("LeakingThis")
        initDagger(context)
    }

    abstract fun initDagger(context: Context)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) presenter.takeView(this as V)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!isInEditMode) presenter.dropView(this as V)
    }

}