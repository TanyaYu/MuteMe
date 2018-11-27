package com.tanyayuferova.muteme.ui

import android.os.Bundle
import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    abstract val layout: Int //@LayoutRes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        ButterKnife.bind(this)
    }
}