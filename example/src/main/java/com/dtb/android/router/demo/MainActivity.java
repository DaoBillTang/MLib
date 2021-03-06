package com.dtb.android.router.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.dtb.android.router.demo.testinject.TestObj;
import com.dtb.android.router.demo.testinject.TestParcelable;
import com.dtb.android.router.demo.testinject.TestSerializable;
import com.dtb.android.router.demo.testservice.HelloService;
import com.dtb.android.router.demo.testservice.SingleService;
import com.dtb.router.facade.Postcard;
import com.dtb.router.facade.callback.NavCallback;
import com.dtb.router.launcher.Router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
    }

    public static Activity getThis() {
        return activity;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openLog:
                Router.openLog();
                break;
            case R.id.openDebug:
                Router.openDebug();
                break;
            case R.id.init:
                // 调试模式不是必须开启，但是为了防止有用户开启了InstantRun，但是
                // 忘了开调试模式，导致无法使用Demo，如果使用了InstantRun，必须在
                // 初始化之前开启调试模式，但是上线前需要关闭，InstantRun仅用于开
                // 发阶段，线上开启调试模式有安全风险，可以使用BuildConfig.DEBUG
                // 来区分环境
                Router.openDebug();
                Router.init(getApplication());
                break;
            case R.id.normalNavigation:
                Router.getInstance()
                        .build("/test/activity2")
                        .navigation();
                break;
            case R.id.kotlinNavigation:
                Router.getInstance()
                        .build("/kotlin/test")
                        .withString("name", "老王")
                        .withInt("age", 23)
                        .navigation();
                break;
            case R.id.normalNavigationWithParams:
                // Router.getInstance()
                //         .build("/test/activity2")
                //         .withString("key1", "value1")
                //         .navigation();

                Uri testUriMix = Uri.parse("Router://m.aliyun.com/test/activity2");
                Router.getInstance().build(testUriMix)
                        .withString("key1", "value1")
                        .navigation();

                break;
            case R.id.oldVersionAnim:
                Router.getInstance()
                        .build("/test/activity2")
                        .withTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                        .navigation(this);
                break;
            case R.id.newVersionAnim:
                if (Build.VERSION.SDK_INT >= 16) {
                    ActivityOptionsCompat compat = ActivityOptionsCompat.
                            makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);

                    Router.getInstance()
                            .build("/test/activity2")
                            .withOptionsCompat(compat)
                            .navigation();
                } else {
                    Toast.makeText(this, "API < 16,不支持新版本动画", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.interceptor:
                Router.getInstance()
                        .build("/test/activity4")
                        .navigation(this, new NavCallback() {
                            @Override
                            public void onArrival(Postcard postcard) {

                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.d("Router", "被拦截了");
                            }
                        });
                break;
            case R.id.navByUrl:
                Router.getInstance()
                        .build("/test/webview")
                        .withString("url", "file:///android_asset/scheme-test.html")
                        .navigation();
                break;
            case R.id.autoInject:
                TestSerializable testSerializable = new TestSerializable("Titanic", 555);
                TestParcelable testParcelable = new TestParcelable("jack", 666);
                TestObj testObj = new TestObj("Rose", 777);
                List<TestObj> objList = new ArrayList<>();
                objList.add(testObj);

                Map<String, List<TestObj>> map = new HashMap<>();
                map.put("testMap", objList);

                Router.getInstance().build("/test/activity1")
                        .withString("name", "老王")
                        .withInt("age", 18)
                        .withBoolean("boy", true)
                        .withLong("high", 180)
                        .withString("url", "https://a.b.c")
                        .withSerializable("ser", testSerializable)
                        .withParcelable("pac", testParcelable)
                        .withObject("obj", testObj)
                        .withObject("objList", objList)
                        .withObject("map", map)
                        .navigation();
                break;
            case R.id.navByName:
                ((HelloService) Router.getInstance().build("/yourservicegroupname/hello").navigation()).sayHello("mike");
                break;
            case R.id.navByType:
                Router.getInstance().navigation(HelloService.class).sayHello("mike");
                break;
            case R.id.navToMoudle1:
                Router.getInstance().build("/module/1").navigation();
                break;
            case R.id.navToMoudle2:
                // 这个页面主动指定了Group名
                Router.getInstance().build("/module/2", "m2").navigation();
                break;
            case R.id.destroy:
                Router.getInstance().destroy();
                break;
            case R.id.failNav:
                Router.getInstance().build("/xxx/xxx").navigation(this, new NavCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.d("Router", "找到了");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.d("Router", "找不到了");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.d("Router", "跳转完了");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.d("Router", "被拦截了");
                    }
                });
                break;
            case R.id.callSingle:
                Router.getInstance().navigation(SingleService.class).sayHello("Mike");
                break;
            case R.id.failNav2:
                Router.getInstance().build("/xxx/xxx").navigation();
                break;
            case R.id.failNav3:
                Router.getInstance().navigation(MainActivity.class);
                break;
            case R.id.normalNavigation2:
                Router.getInstance()
                        .build("/test/activity2")
                        .navigation(this, 666);
                break;
            case R.id.getFragment:
                Fragment fragment = (Fragment) Router.getInstance().build("/test/fragment").navigation();
                Toast.makeText(this, "找到Fragment:" + fragment.toString(), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 666:
                Log.e("activityResult", String.valueOf(resultCode));
                break;
            default:
                break;
        }
    }
}
