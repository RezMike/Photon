package io.github.rezmike.photon.utils

import android.app.Activity
import android.view.LayoutInflater
import android.widget.FrameLayout
import flow.*
import io.github.rezmike.photon.R
import io.github.rezmike.photon.ui.abstracts.AbstractScreen
import io.github.rezmike.photon.ui.activities.root.RootActivity
import mortar.MortarScope

class TreeKeyDispatcher(val activity: Activity) : Dispatcher {

    override fun dispatch(traversal: Traversal, callback: TraversalCallback) {
        val inState = traversal.getState(traversal.destination.top())
        val outState = if (traversal.origin == null) null else traversal.getState(traversal.origin!!.top())

        val inKey: AbstractScreen<*> = inState.getKey()
        val outKey: AbstractScreen<*>? = outState?.getKey()

        if (inKey.getLayout() == outKey?.getLayout()) {
            callback.onTraversalCompleted()
            return
        }

        val rootFrame = activity.findViewById(R.id.root_frame) as FrameLayout
        val currentView = rootFrame.getChildAt(0)

        //create new view
        val layout = inKey.getLayoutResId()
        if (layout == 0) throw IllegalStateException("LayoutResId for screen ${inKey.getScopeName()} can't be 0")
        val screenContext = ScreenScoper.createScreenContext(activity, inKey)
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
                ScreenScoper.destroyTearDownScope(activity, outKey)
            }
        }
        rootFrame.addView(newView)
        (activity as RootActivity).setCurrentBottomItem(inKey.currentBottomItem)
        callback.onTraversalCompleted()
    }
}


