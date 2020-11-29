package com.example.thelastworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Broadcast Recevier 监听网络变化
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    //---------
    private DrawerLayout mDrawerLayout;

    private SwipeRefreshLayout swipeRefresh;
    //服务器文件夹
    private String url = "http://134.175.231.43/android_index/";
    //文件夹图片名
    private IndexImage[] indexImages={new IndexImage(url+"01.jpg"),new IndexImage(url+"02.jpg")
    ,new IndexImage(url+"03.jpg"),new IndexImage(url+"04.jpg"),new IndexImage(url+"05.jpg")
    ,new IndexImage(url+"06.jpg"),new IndexImage(url+"07.jpg"),new IndexImage(url+"08.jpg")};

    private List<String> indexUrlList = new ArrayList<>();

    private List<IndexImage> indexImageList = new ArrayList<>();

    private IndexImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到toolbar实例，然后传入实例。
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //滑动菜单界面
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        //加载菜单
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_index);//设置首页默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_index:
                        mDrawerLayout.closeDrawers();
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
                        Intent intent = new Intent(MyApplication.getContext(), About.class);
                        startActivity(intent);
                        break;
                    default:
                }
                return true;
            }
        });
        //初始化主页图片
        initIndexImages();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //设置主页图片列数为2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new IndexImageAdapter(indexImageList);//把图片列表放进adapter中
        recyclerView.setAdapter(adapter);

        //主页下拉刷新更新图片
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIndexImages();
            }
        });

        //初始化ImageLoader
        ImageLoader.getInstance().init(ImageLoaderConfigs.getImageLoaderConfiguration(this));

        //broadcast
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

    }

    class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context, "network is available", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);

    }

    //下拉刷新
    private void refreshIndexImages(){
        Toast.makeText(this, "fresh",Toast.LENGTH_SHORT).show();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                break;
            case R.id.delete:
                break;
            case R.id.settings:
                break;
            default:

        }
        return true;
    }

    private void initIndexImages(){
        indexImageList.clear();
        for(int i=0;i<indexImages.length;i++){
            indexImageList.add(indexImages[i]);
        }
    }



}
