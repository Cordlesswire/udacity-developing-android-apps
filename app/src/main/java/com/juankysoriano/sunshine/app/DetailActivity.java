package com.juankysoriano.sunshine.app;

import android.os.Bundle;

public class DetailActivity extends NavigationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }
}
