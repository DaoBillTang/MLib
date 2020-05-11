package com.alibaba.android.arouter.demo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.dtb.mrouter.facade.Postcard;
import com.dtb.mrouter.facade.callback.NavCallback;
import com.dtb.mrouter.launcher.ARouter;


public class SchemeFilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        直接通过ARouter处理外部Uri
        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }
}
