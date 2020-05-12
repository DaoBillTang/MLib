package com.dtb.android.router.demo.testservice;

import android.content.Context;
import android.widget.Toast;

import com.dtb.router.facade.Postcard;
import com.dtb.router.facade.annotation.Route;
import com.dtb.router.facade.service.DegradeService;
import com.dtb.router.launcher.Router;

@Route(path = "/service/Lost")
public class LostService implements DegradeService {
    private Context mContext;

    @Override
    public void onLost(Context context, Postcard postcard) {
        Toast.makeText(mContext, "跳转" + postcard.getPath() + "失败，进入保底流程", Toast.LENGTH_SHORT).show();
        Router.getInstance().build("/base/ErrActivity").navigation();
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
    }
}
