package com.example.thelastworks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class classification extends AppCompatActivity {
    private TabLayout tabLayout=null;
    private ViewPager viewPager;
    private Fragment[] fragmentArrays=new Fragment[5];
    private String[] classifyTitles=new String[5];

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_classification);
        tabLayout=(TabLayout) findViewById(R.id.tablayout);
        viewPager=(ViewPager) findViewById(R.id.tab_viewpager);
        initView();


        mDrawerLayout = (DrawerLayout) findViewById(R.id.classification_drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.classification_nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navView.setCheckedItem(R.id.nav_label);
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
                        break;
                    case R.id.nav_collect:
                        mDrawerLayout.closeDrawers();
                        Intent intent4 = new Intent(MyApplication.getContext(), collection.class);
                        startActivity(intent4);
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

    private void initView(){
        classifyTitles[0]="  蜘蛛侠  ";
        classifyTitles[1]="神秘海域4";
        classifyTitles[2]="死亡搁浅 ";
        classifyTitles[3]="  血  源  ";
        classifyTitles[4]="其他游戏 ";
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        fragmentArrays[0]=TabFragment.newInstance(0);
        fragmentArrays[1]=TabFragment.newInstance(1);
        fragmentArrays[2]=TabFragment.newInstance(2);
        fragmentArrays[3]=TabFragment.newInstance(3);
        fragmentArrays[4]=TabFragment.newInstance(4);
        PagerAdapter pagerAdapter=new ClassifyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    final class ClassifyPagerAdapter extends FragmentPagerAdapter {
        public ClassifyPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentArrays[position];
        }

        @Override
        public int getCount() {
            return fragmentArrays.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return classifyTitles[position];
        }
    }
}
