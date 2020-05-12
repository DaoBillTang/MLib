package com.dtb.android.arouter.demo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.dtb.android.arouter.demo.R;
import com.dtb.mrouter.facade.annotation.Route;


@Route(path = "/test/webview")
public class TestWebview extends Activity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);


        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl(getIntent().getStringExtra("url"));
    }
}
