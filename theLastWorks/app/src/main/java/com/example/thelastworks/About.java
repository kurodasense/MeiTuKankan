package com.example.thelastworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class About extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.about_drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.about_nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
        }
        navView.setCheckedItem(R.id.nav_about);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                NavigationView navView = (NavigationView) findViewById(R.id.about_nav_view);
                switch(item.getItemId()){
                    case R.id.nav_index:
                        mDrawerLayout.closeDrawers();
                        Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_label:
                        mDrawerLayout.closeDrawers();
                        Intent intent4 = new Intent(MyApplication.getContext(), classification.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_collect:
                        mDrawerLayout.closeDrawers();
                        Intent intent2 = new Intent(MyApplication.getContext(), collection.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_about:
                        mDrawerLayout.closeDrawers();
                        break;
                    default:
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }
}
