package com.technobugsai.composecustomexamples.utils.models

import com.technobugsai.composecustomexamples.R

data class DataModel(
    val name: String,
    val url: String,
    val localPath: Int = R.drawable.ic_launcher_background
)