package com.technobugsai.composecustomexamples.utils

import com.technobugsai.composecustomexamples.R
import com.technobugsai.composecustomexamples.utils.models.DataModel

object TestData {

    const val CACHE_IMG_DIR_NAME = "image_cache"

    fun getList(): ArrayList<DataModel> {
        return arrayListOf(
            DataModel("Product 1", "https://i.ibb.co/t3wHbwc/two.webp", R.drawable.one),
            DataModel("Product 2", "https://i.ibb.co/8XwTLm0/twelve.webp", R.drawable.two),
            DataModel("Product 3", "https://i.ibb.co/sJPgTfG/three.webp", R.drawable.three),
            DataModel("Product 4", "https://i.ibb.co/Ypk6g07/thirteen.webp", R.drawable.four),
            DataModel("Product 5", "https://i.ibb.co/RSYtVHT/eight.webp", R.drawable.five),
            DataModel("Product 6", "https://i.ibb.co/30c885S/eleven.webp", R.drawable.six),
            DataModel("Product 7", "https://i.ibb.co/FH1Kq00/five.webp", R.drawable.seven),
            DataModel("Product 8", "https://i.ibb.co/Cn2pq5q/four.webp", R.drawable.eight),
            DataModel("Product 9", "https://i.ibb.co/8ztQZ1d/fourteen.webp", R.drawable.nine),
            DataModel("Product 10", "https://i.ibb.co/B4B11rk/nine.webp", R.drawable.ten),
            DataModel("Product 11", "https://i.ibb.co/48R7bqB/one.webp", R.drawable.eleven),
            DataModel("Product 12", "https://i.ibb.co/Wgh3Xfm/seven.webp", R.drawable.twelve),
            DataModel("Product 13", "https://i.ibb.co/BrFS7BQ/six.webp", R.drawable.thirteen),
            DataModel("Product 14", "https://i.ibb.co/5hs6SS6/ten.webp", R.drawable.fourteen),
        )
    }

}