package com.example.daily.MyActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.daily.MyFragment.Fragment1;
import com.example.daily.MyFragment.Fragment2;
import com.example.daily.MyAdapter.MyViewFragmentAdapter;
import com.example.daily.Others.DailyTask;
import com.example.daily.Others.MyApplication;
import com.example.daily.Others.MyService;
import com.example.daily.Others.Plan;
import com.example.daily.Others.Today;
import com.example.daily.R;
import com.example.daily.util.CRUD;
import com.example.daily.util.CRUDplan;
import com.example.daily.util.CRUDtoday;
import com.example.daily.util.MyTime;
import com.example.daily.util.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


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
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;




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
        }else if(op.getNote(1).getToday().equals(today)==false){
            Log.e("1244444","1684894984984984998***");
            note.setId(1);
            op.updateNote(note);
            op.close();
            CRUD op1=new CRUD(context);
            op1.open();
            notes=op1.getStateNotes("?????????");
            Log.e("Size:",notes.size()+"***");
            for(DailyTask e:notes){
                String [] p1 = e.getTime().split("[???]");
                String [] p2 = today.split("[???]");
                String [] p3 = p1[1].split("[???]");
                String [] p4 = p2[1].split("[???]");
                if(p1[0].compareTo(p2[0])<0){
                    e.setState("?????????");
                    op1.updateNote(e);
                }else if(p3[0].compareTo(p4[0])<0){
                    e.setState("?????????");
                    op1.updateNote(e);
                }else if(Integer.parseInt(p3[1].split("[???]")[0])<Integer.parseInt(p4[1].split("[???]")[0])){
                    e.setState("?????????");
                    op1.updateNote(e);
                }

            }
            op1.close();

            CRUDplan op2=new CRUDplan(context);
            op2.open();
            plans=op2.getStateNotes("?????????");
            for(Plan p:plans){
                p.setState("?????????");
                op2.updateNote(p);
            }
            op2.close();

        }

    }

    private void inittable() {

        final Switch aSwitch = findViewById(R.id.s_v);

        aSwitch.setChecked(false);

        aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.s_false);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //????????????????????????

                if (b) {

                    aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.s_true);

//                    Intent intent = new Intent(context,AutoReceiver.class);
//                    intent.setAction("VIDEO_TIMER");
//                    // PendingIntent??????????????????????????????????????????
//
//                    alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
//                    alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
//                    Log.e("Tag", "onClick: ????????????????????????" );
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTimeInMillis(System.currentTimeMillis());
//                    calendar.set(Calendar.HOUR_OF_DAY, 10);
//                    calendar.set(Calendar.MINUTE, 11);
//
//                    // With setInexactRepeating(), you have to use one of the AlarmManager interval
//                    // constants--in this case, AlarmManager.INTERVAL_DAY.
////                    alarmMgr.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
////                            AlarmManager.INTERVAL_DAY, alarmIntent);
//
////                    alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
////                            SystemClock.elapsedRealtime(), 10* 1000, alarmIntent);
//
//                    alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                            1000 * 10, alarmIntent);



                    Intent intent=new Intent(MainActivity.this, MyService.class);
                    startService(intent);




                    ToastUtil.showToast(context,"?????????????????????");



                }else {

                    aSwitch.setSwitchTextAppearance(MainActivity.this,R.style.s_false);
//                    if(alarmIntent==null){
//
//                        // ????????????intent?????????filterEquals(intent)????????????????????????
//                        // ??????????????????
//                        Intent intent = new Intent(MainActivity.this, AutoReceiver.class);
//                        intent.setAction("VIDEO_TIMER");
//                        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                        AlarmManager manager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        manager1.cancel(alarmIntent);
//                    }else {
//                        AlarmManager manager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                        manager1.cancel(alarmIntent);
//                    }


                    ToastUtil.showToast(context,"?????????????????????");

                }

            }

        });
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
        viewpage.setUserInputEnabled(false);//??????????????????
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
