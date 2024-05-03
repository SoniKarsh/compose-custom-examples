package com.technobugsai.composecustomexamples.utils

import com.technobugsai.composecustomexamples.R
import com.technobugsai.composecustomexamples.utils.models.DataModel

object TestData {

    fun getList(): ArrayList<DataModel> {
        return arrayListOf(
            DataModel("Product 1", "", R.drawable.one),
            DataModel("Product 2", "", R.drawable.two),
            DataModel("Product 3", "", R.drawable.three),
            DataModel("Product 4", "", R.drawable.four),
            DataModel("Product 5", "", R.drawable.five),
            DataModel("Product 6", "", R.drawable.six),
            DataModel("Product 7", "", R.drawable.seven),
            DataModel("Product 8", "", R.drawable.eight),
            DataModel("Product 9", "", R.drawable.nine),
            DataModel("Product 10", "", R.drawable.ten),
            DataModel("Product 11", "", R.drawable.eleven),
            DataModel("Product 12", "", R.drawable.twelve),
            DataModel("Product 13", "", R.drawable.thirteen),
            DataModel("Product 14", "", R.drawable.fourteen),
        )
    }

}