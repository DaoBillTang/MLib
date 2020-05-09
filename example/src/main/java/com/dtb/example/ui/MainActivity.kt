package com.dtb.example.ui

import android.os.Bundle
import com.dtb.core.base.DtbBaseActivity
import com.dtb.example.R


/**
 * @author: dtb
 * @email: wendaoyuqin1@gmail.com
 * @createTime: 19-3-11下午5:23
 */
class MainActivity : DtbBaseActivity() {
    override fun getLayoutResource(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add product list fragment if this is first creation

        // Add product list fragment if this is first creation
//        if (savedInstanceState == null) {
//            val fragment = ProductListFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit()
//        }
    }


//    /** Shows the product detail fragment  */
//    fun show(product: Product) {
//        val productFragment: ProductFragment = ProductFragment.forProduct(product.getId())
//        supportFragmentManager
//            .beginTransaction()
//            .addToBackStack("product")
//            .replace(
//                R.id.fragment_container,
//                productFragment, null
//            ).commit()
//    }
}