package com.example.thelastworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class collection extends AppCompatActivity {
    private CollectionDb collectionDb;
    private List<IndexImage> collectImageList=new ArrayList<>();

    private collectionAdapter collectionAdapter;

    private SwipeRefreshLayout swipeRefresh;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionDb=new CollectionDb(this,"collect.db",null,1);
        initImageList();
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.collection_recycler);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        collectionAdapter=new collectionAdapter(collectImageList);
        recyclerView.setAdapter(collectionAdapter);

        //刷新
        swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.collection_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshImageList();
            }
        });
        //--
        Toolbar toolbar = (Toolbar) findViewById(R.id.collection_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.collection_drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.collection_nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setCheckedItem(R.id.nav_collect);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_index:
                        mDrawerLayout.closeDrawers();
                        Intent intent2 = new Intent(MyApplication.getContext(), MainActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_label:
                        mDrawerLayout.closeDrawers();
                        Intent intent4 = new Intent(MyApplication.getContext(), classification.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_collect:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_about:
                        mDrawerLayout.closeDrawers();
                        Intent intent = new Intent(MyApplication.getContext(), About.class);
                        startActivity(intent);
                        break;
                    default:
                }
                return true;
            }
        });
    }
    private void initImageList() {
        if(collectImageList.isEmpty()!=true){
            collectImageList.clear();
        }
        Uri uri = Uri.parse("content://com.example.collection.provider/collection");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String url = cursor.getString(cursor.getColumnIndex("imgurl"));
                Log.d("collection", "query " + url);
                IndexImage indexImage=new IndexImage(url);
                collectImageList.add(indexImage);
            }
            cursor.close();
        }
    }

    //刷新方法
    private void refreshImageList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initImageList();
                        collectionAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
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
