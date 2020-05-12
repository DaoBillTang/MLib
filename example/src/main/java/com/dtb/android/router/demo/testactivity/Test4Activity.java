package com.dtb.android.router.demo.testactivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import com.dtb.android.router.demo.R;
import com.dtb.router.facade.annotation.Route;

@Route(path = "/test/activity4")
public class Test4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        ((TextView)findViewById(R.id.test)).setText("I am " + Test4Activity.class.getName());
        String extra = getIntent().getStringExtra("extra");
        if (!TextUtils.isEmpty(extra)) {
            ((TextView)findViewById(R.id.test2)).setText(extra);
        }
    }
}
