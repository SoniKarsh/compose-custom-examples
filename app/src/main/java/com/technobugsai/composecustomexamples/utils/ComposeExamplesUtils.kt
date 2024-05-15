package com.technobugsai.composecustomexamples.utils

object ComposeExamplesUtils {
    enum class AnimationState {
        Expanded, Collapsed
    }

    fun AnimationState.isExpanded(): Boolean {
        return this == AnimationState.Expanded
    }
}