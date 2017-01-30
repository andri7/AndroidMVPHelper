package com.ufkoku.demo_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ufkoku.demo_app.R;
import com.ufkoku.demo_app.ui.activity.retainable.paging.PagingActivity;
import com.ufkoku.demo_app.ui.fragments.FragmentsActivity;
import com.ufkoku.demo_app.ui.activity.retainable.RetainableActivity;
import com.ufkoku.demo_app.ui.activity.savable.SavableActivity;
import com.ufkoku.demo_app.ui.fragments.retainable.paging.PagingFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        findViewById(R.id.retainable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, RetainableActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.savable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, SavableActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.pagingActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PagingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.fragments).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, FragmentsActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.pagingFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new PagingFragment(), "PagingFragment")
                        .addToBackStack("PagingFragment")
                        .commit();
            }
        });
    }
}
