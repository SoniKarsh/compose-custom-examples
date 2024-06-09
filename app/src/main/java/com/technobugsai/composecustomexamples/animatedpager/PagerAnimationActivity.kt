package com.technobugsai.composecustomexamples.animatedpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat

class PagerAnimationActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )
    }
}