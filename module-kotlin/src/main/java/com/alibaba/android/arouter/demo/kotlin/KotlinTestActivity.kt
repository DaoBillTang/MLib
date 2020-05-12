package com.alibaba.android.arouter.demo.kotlin

import android.app.Activity
import android.os.Bundle
import com.dtb.router.facade.annotation.Autowired
import com.dtb.router.facade.annotation.Route
import com.dtb.router.launcher.Router
import kotlinx.android.synthetic.main.activity_kotlin_test.*

@Route(path = "/kotlin/test")
class KotlinTestActivity : Activity() {

    @Autowired
    @JvmField var name: String? = null
    @Autowired
    @JvmField var age: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Router.getInstance().inject(this)  // Start auto inject.

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_test)

        content.text = "name = $name, age = $age"
    }
}
