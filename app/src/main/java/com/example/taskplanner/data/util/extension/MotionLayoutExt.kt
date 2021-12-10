package com.example.taskplanner.data.util.extension

import androidx.constraintlayout.motion.widget.MotionLayout

fun MotionLayout.setAfterAnimOver(func: () -> Unit) {
    addTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

        override fun onTransitionChange(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            func.invoke()
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
        }
    })
}

fun MotionLayout.setActionOnSpecifiedProgress(
    transitionProgress: Float, actionBeforeProgress: () -> Unit, actionAfterProgress: () -> Unit
) {
    addTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
            actionBeforeProgress.invoke()
        }

        override fun onTransitionChange(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
            if (progress > transitionProgress)
                actionAfterProgress.invoke()
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
        }

    })
}