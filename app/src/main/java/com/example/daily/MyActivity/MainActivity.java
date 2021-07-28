package com.example.daily.MyActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.daily.MyFragment.Fragment1;
import com.example.daily.MyFragment.Fragment2;
import com.example.daily.MyFragment.Fragment3;
import com.example.daily.MyAdapter.MyViewFragmentAdapter;
import com.example.daily.R;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager2 viewpage;
    private LinearLayout lmsg,lsig,lexp;
    private ImageView imsg,isig,iexp,icr;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        inittable();
    }
    private void inittable() {
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lmsg=findViewById(R.id.msg);
        lsig=findViewById(R.id.signal);
        imsg=findViewById(R.id.imsg);
        isig=findViewById(R.id.isig);
        lmsg.setOnClickListener(this);
        lsig.setOnClickListener(this);
        imsg.setSelected(true);
        icr=imsg;
    }

    private void initview() {
        viewpage = findViewById(R.id.view_page);
        List<Fragment> fragmentList=new ArrayList<>();
        fragmentList.add(Fragment1.newInstance("Message"));
        fragmentList.add(Fragment2.newInstance("Signal"));
        MyViewFragmentAdapter myadapter = new MyViewFragmentAdapter(getSupportFragmentManager(),getLifecycle(),fragmentList);
        viewpage.setAdapter(myadapter);
        viewpage.setUserInputEnabled(false);//设置不可滑动
        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeTable(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }

        });
    }
    private void changeTable(int position) {
        switch (position){
            case R.id.msg:
                viewpage.setCurrentItem(0);
            //case 0:
                icr.setSelected(false);
                imsg.setSelected(true);
                icr=imsg;
                break;
            case R.id.signal:
                viewpage.setCurrentItem(1);
            //case 1:
                icr.setSelected(false);
                isig.setSelected(true);
                icr=isig;
                break;

        }
    }


    @Override
    public void onClick(View v) {
        changeTable(v.getId());
    }

}
