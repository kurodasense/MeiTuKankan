package com.example.thelastworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.navigation.NavigationView;
import com.nostra13.universalimageloader.core.ImageLoader;


public class SpaceImageDetailActivity extends AppCompatActivity {

    //默认图片
    public static final String IMAGE_URL = "http://134.175.231.43/ali/Arcade_Ahri_Splash.jpg";


    public boolean isCollect;

    //下载服务
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    //-----
    private DrawerLayout mDrawerLayout;

    private ListView lsv_image_loader;
    private String imageUrl;

    //加载数据库
    private CollectionDb collectionDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_image_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.image_detail_toolbar);
        setSupportActionBar(toolbar);
        //加载数据库
        collectionDb = new CollectionDb(this, "collect.db", null, 1);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.image_detail_drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.image_detail_nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        imageUrl = intent.getStringExtra(IMAGE_URL);
        //PhotoView加载图片
        PhotoView indexImageView = (PhotoView) findViewById(R.id.index_image_view);
        //ImageLoader加载图片
        ImageLoader.getInstance().displayImage(imageUrl,indexImageView, ImageLoaderConfigs.getDefaultDisplayImageOptions(this));
        //Glide放图片
//        Glide.with(this).load(imageUrl).into(indexImageView);

        Intent newintent = new Intent(this, DownloadService.class);
        startService(newintent);//开启服务
        bindService(newintent, connection, BIND_AUTO_CREATE);//绑定服务
        if(ContextCompat.checkSelfPermission(SpaceImageDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SpaceImageDetailActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }

        navView.setCheckedItem(R.id.nav_index);
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
                        Intent intent3 = new Intent(MyApplication.getContext(), collection.class);
                        startActivity(intent3);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.index_image_menu, menu);
        //判断该图片是否被收藏过
        isCollect = isCollected(imageUrl);
        if(isCollect == true){
            menu.findItem(R.id.collect_picture).setIcon(R.drawable.ic_collect_full);
        }else{
            menu.findItem(R.id.collect_picture).setIcon(R.drawable.ic_collect);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.dowload_picture:
                downloadBinder.startDownload(imageUrl);
                break;
            case R.id.collect_picture:
                //点击收藏插进数据库
                if(isCollect == true){
                    //取消收藏
                    SQLiteDatabase db=collectionDb.getWritableDatabase();
                    int r=db.delete("collection","imgurl = ?",new String[]{imageUrl});
                    Log.d("delete", "onClick: "+r);
                    item.setIcon(R.drawable.ic_collect);
                    isCollect = false;
                    //---
                    Toast.makeText(this, "已取消收藏",Toast.LENGTH_SHORT).show();
                }else{
                    Uri uri=Uri.parse("content://com.example.collection.provider/collection");
                    ContentValues values=new ContentValues();
                    values.put("imgurl",imageUrl);
                    Uri newUri=getContentResolver().insert(uri,values);
                    String newID=newUri.getPathSegments().get(1);
                    Log.d("insert","  "+ newID);
                    item.setIcon(R.drawable.ic_collect_full);
                    isCollect = true;
                    //---
                    Toast.makeText(this, "已收藏",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>=0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private boolean isCollected(String url){
        SQLiteDatabase db=collectionDb.getReadableDatabase();
        Cursor cursor=db.query("collection",null,"imgurl = ?",new String[]{url},null,null,null);
        if(cursor.getCount()!=0){
            return true;
        }
        else{
            return false;
        }
    }

}
