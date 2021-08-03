package com.example.daily.MyActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.daily.MyFragment.Fragment1;
import com.example.daily.MyFragment.Fragment2;
import com.example.daily.MyAdapter.MyViewFragmentAdapter;
import com.example.daily.MyFragment.NoteDailogFragment;
import com.example.daily.Others.DailyTask;
import com.example.daily.Others.MyApplication;
import com.example.daily.Others.Plan;
import com.example.daily.Others.Today;
import com.example.daily.R;
import com.example.daily.util.CRUD;
import com.example.daily.util.CRUDplan;
import com.example.daily.util.CRUDtoday;
import com.example.daily.util.MyTime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_NEW;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager2 viewpage;
    private LinearLayout lmsg,lsig,lexp;
    private ImageView imsg,isig,icr;
    private FloatingActionButton fab;
    private Toolbar myToolbar;
    private Context context = MyApplication.getInstance();
    private List<DailyTask> notes=new ArrayList<>();
    private List<Plan> plans=new ArrayList<>();

    private String today;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckToday();
        initview();
        inittable();
//        fab=findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(icr==imsg){
//                    nfragment= new NoteDailogFragment();
//                    nfragment.setTargetFragment(getSupportFragmentManager().findFragmentByTag(""));
//                    nfragment.show(getSupportFragmentManager(),"note");
//                }
//            }
//        });
    }

    private void CheckToday() {
        CRUDtoday op = new CRUDtoday(context);
        op.open();
        MyTime myTime=new MyTime();
        today=myTime.getToday();

        Today note = new Today(today);

        if (op.getCount()!=1) {
            Log.e("1256","1684894984984984998***");
            op.addNote(note);
            op.close();
        }else if(op.getNote(1).getToday().compareTo(today)<0){
            Log.e("1244444","1684894984984984998***");
            note.setId(1);
            op.updateNote(note);
            op.close();
            CRUD op1=new CRUD(context);
            op1.open();
            notes=op1.getStateNotes("待完成");
            Log.e("Size:",notes.size()+"***");
            for(DailyTask e:notes){
                if(e.getTime().compareTo(today)<0){
                    e.setState("未完成");
                    op1.updateNote(e);
                }
            }
            op1.close();

            CRUDplan op2=new CRUDplan(context);
            op2.open();
            plans=op2.getStateNotes("已完成");
            for(Plan p:plans){
                p.setState("待完成");
                op2.updateNote(p);
            }
            op2.close();

        }

    }

    private void inittable() {
        myToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
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
