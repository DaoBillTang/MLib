package com.dtb.android.router.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.dtb.router.facade.Postcard;
import com.dtb.router.facade.callback.NavCallback;
import com.dtb.router.launcher.Router;


public class SchemeFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        直接通过ARouter处理外部Uri
        Uri uri = getIntent().getData();
        Router.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }
}
