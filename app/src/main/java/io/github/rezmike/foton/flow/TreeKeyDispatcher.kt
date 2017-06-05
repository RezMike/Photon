package io.github.rezmike.foton.flow

import android.app.Activity
import android.view.LayoutInflater
import io.github.rezmike.foton.ui.abstracts.AbstractScreen
import android.widget.FrameLayout
import io.github.rezmike.foton.R
import flow.*
import io.github.rezmike.foton.mortar.ScreenScoper
import mortar.MortarScope


class TreeKeyDispatcher(activity: Activity) : Dispatcher {

    private var mActivity: Activity = activity

    override fun dispatch(traversal: Traversal, callback: TraversalCallback) {
        var inState = traversal.getState(traversal.destination.top())
        var outState = if (traversal.origin == null) null else traversal.getState(traversal.origin!!.top())

        val inKey: AbstractScreen<*> = inState.getKey()
        val outKey: AbstractScreen<*>? = if (outState == null) null else outState.getKey()

        val rootFrame = mActivity.findViewById(R.id.root_frame) as FrameLayout
        val currentView = rootFrame.getChildAt(0)

        if (inKey.equals(outKey)) {
            callback.onTraversalCompleted()
            return
        }
        //create new view

        val layout = inKey.getLayoutResId()
        val screenContext = ScreenScoper.createScreenContext(mActivity, inKey)!!
        val flowContext = traversal.createContext(inKey, screenContext)
        val newView = LayoutInflater.from(flowContext).inflate(layout, rootFrame, false)

        //restore state to new view
        inState.restore(newView)


        //delete old view
        if (currentView != null) {
            rootFrame.removeView(currentView)
            if (traversal.direction == Direction.BACKWARD) {
                MortarScope.getScope(currentView.context).destroy()
            } else if ((inKey !is TreeKey) && outKey != null) {
                ScreenScoper.destroyTearDownScope(mActivity, outKey)
            }
        }
        rootFrame.addView(newView)
        callback.onTraversalCompleted()
    }
}


