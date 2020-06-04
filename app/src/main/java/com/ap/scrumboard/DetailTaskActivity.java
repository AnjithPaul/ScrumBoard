package com.ap.scrumboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class DetailTaskActivity extends AppCompatActivity {
    public static final String MAINTASK ="main";
    private String main ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_task);

        main = getIntent().getExtras().getString(MAINTASK);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(main);
        ActionBar actionbar =getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        DetailTaskActivity.SectionsPagerAdapter pagerAdapter = new DetailTaskActivity.SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    DetailTodoFragment frag= new DetailTodoFragment();
                    frag.setMain(main);
                    return frag;
                case 1:
                    DetailDoingFragment frag2 =  new DetailDoingFragment();
                    frag2.setMain(main);
                    return frag2;
                case 2:
                    DetailDoneFragment frag3 =  new DetailDoneFragment();
                    frag3.setMain(main);
                    return frag3;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.todo_tab);
                case 1:
                    return getResources().getText(R.string.doing_tab);
                case 2:
                    return getResources().getText(R.string.done_tab);

            }
            return null;
        }
    }
}