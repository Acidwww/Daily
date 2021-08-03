package com.example.daily.MyFragment;

//import android.content.Context;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daily.MyAdapter.LeftAdapter;
import com.example.daily.MyView.LeftListView;
import com.example.daily.MyView.NoSlidingViewPager;
import com.example.daily.Others.onRightItemClickListener;
import com.example.daily.util.CRUD;
import com.example.daily.Others.DailyTask;
import com.example.daily.MyActivity.EditActivity;
import com.example.daily.Others.MyApplication;
import com.example.daily.R;
import com.example.daily.util.MyTime;
import com.example.daily.util.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Fragment1 extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    public  static final int REQUEST_CODE_NEW =1;
    public  static final int REQUEST_CODE_EDIT =2;
    private NoteDailogFragment nfragment;
    private String mParam1;
//    private ListView lv;
    private LeftListView lv1,lv2,lv3;
    private NoSlidingViewPager vp;
    private FloatingActionButton fab;
    private LinearLayout lto,ldone,lundone,lcr;
    private View rootview;
    private LeftAdapter adapter1,adapter2,adapter3;
    private List<DailyTask> allnotes=new ArrayList<>();
    private List<DailyTask> todonotes=new ArrayList<>();
    private List<DailyTask> donenotes=new ArrayList<>();
    private List<DailyTask> undonenotes=new ArrayList<>();
    private Context context = MyApplication.getInstance();
    private Context mcontext;
    private String today;






    public Fragment1() {

    }


    public static Fragment1 newInstance(String param1) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        MyTime myTime=new MyTime();
        today=myTime.getToday();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {

            rootview = inflater.inflate(R.layout.fragment_blank, container, false);
        }


        //name= rootview.findViewById(R.id.text_1);
        //password= rootview.findViewById(R.id.text_12);


        vp=rootview.findViewById(R.id.view_page_2);
        fab=rootview.findViewById(R.id.fab);
        lto=rootview.findViewById(R.id.to_do);
        ldone=rootview.findViewById(R.id.done);
        lundone=rootview.findViewById(R.id.undone);
        lto.setSelected(true);
        lcr=lto;
        final List<View> viewList = new ArrayList<View>();

        //定义两个视图，两个视图都加载同一个布局文件list_view.ml
        View view1 = getLayoutInflater().inflate(R.layout.leftlistview1,null);
        View view2 = getLayoutInflater().inflate(R.layout.leftlistview1,null);
        View view3 = getLayoutInflater().inflate(R.layout.leftlistview1,null);
        //将两个视图添加到视图集合viewList中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //这个方法是返回总共有几个滑动的页面（）
                return viewList.size();
            }


            @Override
            public boolean isViewFromObject(View view, Object object) {
                //该方法判断是否由该对象生成界面。
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
                vp.addView(viewList.get(position));
                return viewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //这个方法从viewPager中移动当前的view。（划过的时候）
                vp.removeView(viewList.get(position));
            }
        });
        lv1=view1.findViewById(R.id.lv1);
        lv2=view2.findViewById(R.id.lv1);
        lv3=view3.findViewById(R.id.lv1);

        adapter1 = new LeftAdapter(mcontext, todonotes,lv1.getRightViewWidth());
        adapter2 = new LeftAdapter(mcontext, donenotes,lv2.getRightViewWidth());
        adapter3 = new LeftAdapter(mcontext, undonenotes,lv3.getRightViewWidth());
        refreshListView();
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);

//        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //changeTable(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        adapter1.setOnRightItemClickListener(new onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                removeNote(position);
            }
        });
        adapter1.setOnCheckClickListener(new LeftAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(View v,int position) {
                DailyTask crNote;
                CRUD op = new CRUD(mcontext);
                op.open();
                crNote=todonotes.get(position);
                if(crNote.getState().equals("待完成")){
                    crNote.setState("已完成");
                    op.updateNote(crNote);
                    op.close();
                    ImageView iv=v.findViewById(R.id.check);
                    iv.setImageResource(R.drawable.check);
                    ToastUtil.showToast(mcontext,"计划完成");
                }else if(crNote.getState().equals("已完成")){
                    crNote.setState("待完成");
                    op.updateNote(crNote);
                    op.close();
                    ImageView iv=v.findViewById(R.id.check);
                    iv.setImageResource(R.drawable.uncheck);
                    ToastUtil.showToast(mcontext,"取消成功");

                }

            }
        });

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailyTask curNote = (DailyTask) parent.getItemAtPosition(position);
                Intent intent = new Intent(mcontext, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", (int)curNote.getId());
                bundle.putString("title", curNote.getTitle());
                bundle.putString("time", curNote.getTime());
                bundle.putInt("tag", curNote.getTag());
                bundle.putString("content", curNote.getContent());
                bundle.putString("state", curNote.getState());
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_EDIT);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nfragment= new NoteDailogFragment();
                nfragment.setTargetFragment(Fragment1.this,REQUEST_CODE_NEW);
                nfragment.show(getFragmentManager(),"note");

            }
        });
        return rootview;
    }

    private void changeTable(int position) {
        switch (position){
            case R.id.to_do:
                vp.setCurrentItem(0);
                //case 0:
                lcr.setSelected(false);
                lto.setSelected(true);
                lcr=lto;
                refreshListView();
                break;
            case R.id.done:
                vp.setCurrentItem(1);
                //case 1:
                lcr.setSelected(false);
                ldone.setSelected(true);
                lcr=ldone;
                refreshListView();
                break;
            case R.id.undone:
                vp.setCurrentItem(2);
                //case 1:
                lcr.setSelected(false);
                lundone.setSelected(true);
                lcr=lundone;
                refreshListView();
                break;

        }
    }


    private void removeNote(int position) {
        String result=todonotes.get(position).getTitle()+"\n"+position;
        ToastUtil.showToast(context,result);
        CRUD op = new CRUD(context);
        op.open();
        op.removeNote(op.getAllNotes().get(position).getId());
        op.close();
        refreshListView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if (requestCode == REQUEST_CODE_NEW) {
                String content = data.getStringExtra(NoteDailogFragment.CONTENT);
                String title = data.getStringExtra(NoteDailogFragment.TITLE);
                String time = data.getStringExtra(NoteDailogFragment.TIME);
                int tag = data.getIntExtra(NoteDailogFragment.TAG, 0);
                String state = "待完成";
                DailyTask note = new DailyTask(content, title, time, tag, state);
                CRUD op = new CRUD(context);
                op.open();
                op.addNote(note);
                op.close();
                refreshListView();
                nfragment.dismiss();
            } else if (requestCode == REQUEST_CODE_EDIT) {

                String content = data.getStringExtra(NoteDailogFragment.CONTENT);
                String title = data.getStringExtra(NoteDailogFragment.TITLE);
                String time = data.getStringExtra(NoteDailogFragment.TIME);
                int tag = data.getIntExtra(NoteDailogFragment.TAG, 0);
                String state = data.getStringExtra(NoteDailogFragment.STATE);
                long id = (long) data.getIntExtra("id", 0);
                DailyTask note = new DailyTask(content, title, time, tag, state);
                Log.e("AfterEdit:************", id + "");
                note.setId(id);
                CRUD op = new CRUD(mcontext);
                op.open();
                op.updateNote(note);
                op.close();
                refreshListView();
            }
        }else if(resultCode==2){
            long id=(long)data.getIntExtra("id",0);
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(id);
            op.close();
            refreshListView();
        }
    }




    public void refreshListView(){
        CRUD op = new CRUD(mcontext);
        op.open();
        if (allnotes.size() > 0) allnotes.clear();
        allnotes.addAll(op.getAllNotes());
        if (todonotes.size() > 0) {
            TextView emptytext=rootview.findViewById(R.id.empty_text);
            emptytext.setText("");
            todonotes.clear();
        }
        for(DailyTask e:allnotes){
            if(e.getTime().compareTo(today)>=0){
                todonotes.add(e);
            }
        }
        if (todonotes.size() > 0) {
            TextView emptytext=rootview.findViewById(R.id.empty_text);
            emptytext.setText("");
        }
        if (donenotes.size() > 0) donenotes.clear();
        donenotes.addAll(op.getStateNotes("已完成"));
        if (undonenotes.size() > 0) undonenotes.clear();
        undonenotes.addAll(op.getStateNotes("未完成"));
        op.close();
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mcontext = activity;
    }

    class myListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.to_do || v.getId() == R.id.done || v.getId() == R.id.undone) {
                changeTable(v.getId());
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myListner mylistner=new myListner();
        lto.setOnClickListener(mylistner);
        ldone.setOnClickListener(mylistner);
        lundone.setOnClickListener(mylistner);
    }
}
