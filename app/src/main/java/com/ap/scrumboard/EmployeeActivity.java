package com.ap.scrumboard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class EmployeeActivity extends AppCompatActivity {
    public static final String EMPLOYEE ="employee";
    private String emp ;
    private Fragment frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        emp = getIntent().getExtras().getString(EMPLOYEE);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar =getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter pagerAdapter = new EmployeeActivity.SectionsPagerAdapter(getSupportFragmentManager());
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
                    MainTodoFragment frag= new MainTodoFragment();
                    frag.setEmp(emp);
                    return frag;
                case 1:
                    MainDoingFragment frag2 =  new MainDoingFragment();
                    frag2.setEmp(emp);
                    return frag2;
                case 2:
                    MainDoneFragment frag3 =  new MainDoneFragment();
                    frag3.setEmp(emp);
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

