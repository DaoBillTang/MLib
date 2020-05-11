package com.alibaba.android.arouter.demo.testactivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.demo.R;
import com.dtb.mrouter.facade.annotation.Autowired;
import com.dtb.mrouter.facade.annotation.Route;

@Route(path = "/test/activity2")
public class Test2Activity extends AppCompatActivity {

    @Autowired
    String key1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        String value = getIntent().getStringExtra("key1");
        if (!TextUtils.isEmpty(value)) {
            Toast.makeText(this, "exist param :" + value, Toast.LENGTH_LONG).show();
        }

        setResult(999);
    }
}
