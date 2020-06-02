package com.ap.scrumboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class EmployeeActivity extends AppCompatActivity {
    public static final String EMPLOYEE ="employee";
    private String emp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  emp = getIntent().getExtras().toString();

        setContentView(R.layout.activity_employee);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SectionsPagerAdapter pagerAdapter = new EmployeeActivity.SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        //emp = getIntent().getExtras().toString();
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
                    return new MainTodoFragment();


                case 1:
                    return new MainDoingFragment();
                case 2:
                    return new MainDoneFragment();
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

    public void onClickAdd(View view){
        Intent intent = new Intent(this,AddTaskActivity.class);
        startActivity(intent);

    }
}

