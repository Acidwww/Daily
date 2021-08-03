package com.example.daily.MyFragment;

//import android.content.Context;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daily.MyActivity.EditActivity;
import com.example.daily.MyActivity.EditPlanActivity;
import com.example.daily.MyAdapter.LeftAdapter;
import com.example.daily.MyAdapter.PlanLeftAdapter;
import com.example.daily.MyView.CustomEditTextBottomPopup;
import com.example.daily.MyView.LeftListView;
import com.example.daily.MyView.NoSlidingViewPager;
import com.example.daily.Others.DailyTask;
import com.example.daily.Others.MyApplication;
import com.example.daily.Others.Plan;
import com.example.daily.Others.Today;
import com.example.daily.Others.onRightItemClickListener;
import com.example.daily.R;
import com.example.daily.util.CRUD;
import com.example.daily.util.CRUDplan;
import com.example.daily.util.MyTime;
import com.example.daily.util.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.SimpleCallback;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private LeftListView lv1,lv2,lv3;
    private NoSlidingViewPager vp;
    private FloatingActionButton fab;
    private LinearLayout lto,lall,lda,lcr;
    private View rootview;
    private PlanLeftAdapter adapter1,adapter2,adapter3;
    private Context context = MyApplication.getInstance();
    private List<Plan> allnotes=new ArrayList<>();
    private List<Plan> todonotes=new ArrayList<>();
    private List<Plan> donenotes=new ArrayList<>();
    private String today;
    private int Week;
    private Context mcontext;
    public  static final int REQUEST_CODE_EDIT =2;


    public Fragment2() {

    }


    public static Fragment2 newInstance(String param1) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyTime myTime=new MyTime();
        today=myTime.getToday();
        Week=myTime.getWeek();
        Log.e("Today:",today+"///week:"+Week);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {

            rootview = inflater.inflate(R.layout.fragment_fragment2, container, false);
        }
        vp=rootview.findViewById(R.id.view_page_3);
        fab=rootview.findViewById(R.id.fab2);
        lto=rootview.findViewById(R.id.today_to_do);
        lall=rootview.findViewById(R.id.all_plan);
        lda=rootview.findViewById(R.id.daka);
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

        adapter1 = new PlanLeftAdapter(context, todonotes,lv1.getRightViewWidth());
        adapter2 = new PlanLeftAdapter(context, allnotes,lv2.getRightViewWidth());
        adapter3 = new PlanLeftAdapter(context, donenotes,lv3.getRightViewWidth());

        refreshListView();
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plan curNote = (Plan) parent.getItemAtPosition(position);
                Intent intent = new Intent(mcontext, EditPlanActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", (int)curNote.getId());
                bundle.putInt("tag", curNote.getTag());
                bundle.putString("content", curNote.getContent());
                bundle.putString("time", curNote.getTime());
                bundle.putInt("mon",curNote.getMonday());
                bundle.putInt("tue",curNote.getTuesday());
                bundle.putInt("wed",curNote.getWednesday());
                bundle.putInt("thu",curNote.getThursday());
                bundle.putInt("fri",curNote.getFriday());
                bundle.putInt("sat",curNote.getSaturday());
                bundle.putInt("sun",curNote.getSunday());
                bundle.putString("state",curNote.getState());
                bundle.putInt("week",curNote.getWeek());
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_EDIT);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(getContext())
                        .autoOpenSoftInput(true).setPopupCallback(new SimpleCallback() { //设置显示和隐藏的回调
                    @Override
                    public void onCreated(BasePopupView v) {
                        // 弹窗内部onCreate执行完调用
                    }

                    @Override
                    public void beforeShow(BasePopupView v) {
                        super.beforeShow(v);
                        Log.e("tag", "beforeShow，在每次show之前都会执行，可以用来进行多次的数据更新。");
                    }

                    @Override
                    public void onShow(BasePopupView v) {
                        // 完全显示的时候执行
                    }

                    @Override
                    public void onDismiss(BasePopupView v) {
                        // 完全隐藏的时候执行
                        refreshListView();

                    }

                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    @Override
                    public boolean onBackPressed(BasePopupView v) {

                        ToastUtil.showToast(context,"我拦截的返回按键，按返回键XPopup不会关闭了");
                        return true; //默认返回false
                    }
                })
//                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(new CustomEditTextBottomPopup(getContext()))
                        .show();
            }
        });
        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshListView();
    }

    public void refreshListView(){
        CRUDplan op = new CRUDplan(context);
        op.open();
        if (allnotes.size() > 0) allnotes.clear();
        allnotes.addAll(op.getAllNotes());
        if (todonotes.size() > 0) {
            TextView emptytext=rootview.findViewById(R.id.empty_text_plan);
            emptytext.setText("");
            todonotes.clear();
        }
        for(Plan e:allnotes){
            switch (Week){
                case 0:
                    if(e.getSunday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 1:
                    if(e.getMonday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 2:
                    if(e.getTuesday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 3:
                    if(e.getWednesday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 4:
                    if(e.getThursday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 5:
                    if(e.getFriday()==1){
                        todonotes.add(e);
                    }
                    break;
                case 6:
                    if(e.getSaturday()==1){
                        todonotes.add(e);
                    }
                    break;

            }


        }
        if (todonotes.size() > 0) {
            TextView emptytext=rootview.findViewById(R.id.empty_text_plan);
            emptytext.setText("");
        }


        op.close();
        adapter1.notifyDataSetChanged();
        adapter2.notifyDataSetChanged();
        adapter3.notifyDataSetChanged();
        adapter1.setOnRightItemClickListener(new onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                removeNote(position);
            }
        });
        adapter1.setOnCheckClickListener(new PlanLeftAdapter.OnCheckClickListener() {
            @Override
            public void onCheckClick(View v,int position) {
                Plan crNote;
                CRUDplan op = new CRUDplan(context);
                op.open();
                crNote=todonotes.get(position);
                if(crNote.getState().equals("待完成")){
                    crNote.setState("已完成");
                    op.updateNote(crNote);
                    op.close();
                    ImageView iv=v.findViewById(R.id.check);
                    iv.setImageResource(R.drawable.check);
                    ToastUtil.showToast(context,"打卡成功");
                }else if(crNote.getState().equals("已完成")){
                    crNote.setState("待完成");
                    op.updateNote(crNote);
                    op.close();
                    ImageView iv=v.findViewById(R.id.check);
                    iv.setImageResource(R.drawable.uncheck);
                    ToastUtil.showToast(context,"取消成功");

                }

            }
        });

    }

    private void changeTable(int position) {
        switch (position){
            case R.id.today_to_do:
                vp.setCurrentItem(0);
                //case 0:
                lcr.setSelected(false);
                lto.setSelected(true);
                lcr=lto;
                refreshListView();
                break;
            case R.id.all_plan:
                vp.setCurrentItem(1);
                //case 1:
                lcr.setSelected(false);
                lall.setSelected(true);
                lcr=lall;
                refreshListView();
                break;
            case R.id.daka:
                vp.setCurrentItem(2);
                //case 1:
                lcr.setSelected(false);
                lda.setSelected(true);
                lcr=lda;
                refreshListView();
                break;

        }
    }

    private void removeNote(int position) {
        ToastUtil.showToast(context,"已删除");
        CRUDplan op = new CRUDplan(context);
        op.open();
        op.removeNote(op.getAllNotes().get(position).getId());
        op.close();
        refreshListView();
    }

    class myListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.today_to_do || v.getId() == R.id.all_plan || v.getId() == R.id.daka) {
                changeTable(v.getId());
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Fragment2.myListner mylistner=new Fragment2.myListner();
        lto.setOnClickListener(mylistner);
        lall.setOnClickListener(mylistner);
        lda.setOnClickListener(mylistner);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mcontext = activity;
    }
}
