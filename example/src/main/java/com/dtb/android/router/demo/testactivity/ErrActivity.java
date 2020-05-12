package com.dtb.android.router.demo.testactivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dtb.android.router.demo.R;
import com.dtb.router.facade.annotation.Route;

@Route(path = "/test/ErrActivity")
public class ErrActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_err);
    }
}

