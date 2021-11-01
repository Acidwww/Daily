package com.example.daily.MyActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.daily.MyView.MultipleBottomPopup;
import com.example.daily.Others.MyApplication;
import com.example.daily.Others.Plan;
import com.example.daily.R;
import com.example.daily.util.CRUDplan;
import com.example.daily.util.ToastUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;

import java.util.Date;

public class EditPlanActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_content;
    private LinearLayout repeat,timeedit,edit_tag;
    private TextView tv_time,plan_tag;
    private ImageView back,check;
    private String content,time,state;
    private int tag,id,mon,tue,wed,thu,fri,sat,sun,week;
    Context context = MyApplication.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_plan_layout);
        et_content=findViewById(R.id.edit_plan_content);
        repeat=findViewById(R.id.edit_plan_repeat);
        timeedit=findViewById(R.id.edit_plan_changetime);
        tv_time=findViewById(R.id.edit_plan_time);
        back=findViewById(R.id.edit_plan_back);
        plan_tag=findViewById(R.id.edit_plan_tag);
        edit_tag=findViewById(R.id.edit_plan_tag_touch);
        check=findViewById(R.id.edit_plan_check);
        check.setOnClickListener(this);
        et_content.setOnClickListener(this);
        repeat.setOnClickListener(this);
        timeedit.setOnClickListener(this);
        back.setOnClickListener(this);
        edit_tag.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        id=bundle.getInt("id");
        content = bundle.getString("content");
        time = bundle.getString("time");
        tag = bundle.getInt("tag");
        mon = bundle.getInt("mon");
        tue = bundle.getInt("tue");
        wed = bundle.getInt("wed");
        thu = bundle.getInt("thu");
        fri = bundle.getInt("fri");
        sat = bundle.getInt("sat");
        sun = bundle.getInt("sun");
        state = bundle.getString("state");
        week = bundle.getInt("week");
        if(state.equals("待完成")){
            check.setImageResource(R.drawable.uncheck);
        }else if(state.equals("已完成")){
            check.setImageResource(R.drawable.check);
        }
        switch(tag){
            case 0:
                plan_tag.setText("学习");
                break;
            case 1:
                plan_tag.setText("工作");
                break;
            case 2:
                plan_tag.setText("运动");
                break;
            case 3:
                plan_tag.setText("生活");
                break;
            case 4:
                plan_tag.setText("其他");
                break;
        }
        et_content.setText(content);
        tv_time.setText(time);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_plan_check:
                if(state.equals("待完成")){
                    state="已完成";
                    check.setImageResource(R.drawable.check);
                    ToastUtil.showToast(context,"打卡成功");
                }else if(state.equals("已完成")){
                    state="待完成";
                    check.setImageResource(R.drawable.uncheck);
                    ToastUtil.showToast(context,"取消成功");

                }
                break;
            case R.id.edit_plan_repeat:
                final MultipleBottomPopup multipleBottomPopup=new MultipleBottomPopup(EditPlanActivity.this);
                new XPopup.Builder(getApplicationContext())
                        .autoOpenSoftInput(true).setPopupCallback(new SimpleCallback() { //设置显示和隐藏的回调
                    @Override
                    public void onCreated(BasePopupView v) {
                        // 弹窗内部onCreate执行完调用

                    }

                    @Override
                    public void beforeShow(BasePopupView v) {
                        super.beforeShow(v);
                        multipleBottomPopup.setWeek(new int[]{mon,tue,wed,thu,fri,sat,sun,week});
                        Log.e("tag", "beforeShow，在每次show之前都会执行，可以用来进行多次的数据更新。");
                    }

                    @Override
                    public void onShow(BasePopupView v) {
                        // 完全显示的时候执行
                    }

                    @Override
                    public void onDismiss(BasePopupView v) {
                        // 完全隐藏的时候执行getComment()
                        int[] week1=multipleBottomPopup.getWeek();
                        if(week1[7]==1){
                            week=1;
                            mon=week1[0];
                            tue=week1[1];
                            wed=week1[2];
                            thu=week1[3];
                            fri=week1[4];
                            sat=week1[5];
                            sun=week1[6];
                        }
                    }

                    //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
                    @Override
                    public boolean onBackPressed(BasePopupView v) {

                        ToastUtil.showToast(context,"我拦截的返回按键，按返回键XPopup不会关闭了");
                        return true; //默认返回false
                    }
                })
//                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asCustom(multipleBottomPopup)
                        .show();
                break;
            case R.id.edit_plan_changetime:
                TimePickerView pvTime = new TimePickerBuilder(EditPlanActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String [] parts = date.toString().split("[ ]")[3].split("[:]");
                        String time1=parts[0]+"时"+parts[1]+"分";
                        tv_time.setText(time1);
                        time=time1;
                        Log.e("pvTime",time );
                    }
                })
                        .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                            @Override
                            public void onTimeSelectChanged(Date date) {
                                Log.i("pvTime", "onTimeSelectChanged");
                            }
                        })
                        .setType(new boolean[]{false, false, false, true, true, false})
                        .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                        .addOnCancelClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("pvTime", "onCancelClickListener");
                            }
                        })
                        .setItemVisibleCount(4) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                        .setLineSpacingMultiplier(2.5f)
                        .isAlphaGradient(true)
                        .build();

                Dialog mDialog = pvTime.getDialog();
                if (mDialog != null) {

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            Gravity.BOTTOM);

                    params.leftMargin = 0;
                    params.rightMargin = 0;
                    pvTime.getDialogContainerLayout().setLayoutParams(params);

                    Window dialogWindow = mDialog.getWindow();
                    if (dialogWindow != null) {
                        dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                        dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                        dialogWindow.setDimAmount(0.3f);
                    }
                }
                pvTime.show(v);
                break;
            case R.id.edit_plan_tag_touch:
                new XPopup.Builder(EditPlanActivity.this)
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"学习", "工作", "运动","生活","其他"},
                                new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        switch(position){
                                            case 0:
                                                plan_tag.setText("学习");
                                                break;
                                            case 1:
                                                plan_tag.setText("工作");
                                                break;
                                            case 2:
                                                plan_tag.setText("运动");
                                                break;
                                            case 3:
                                                plan_tag.setText("生活");
                                                break;
                                            case 4:
                                                plan_tag.setText("其他");
                                                break;
                                        }
                                        tag=position;
                                    }
                                })
                        .show();
                break;
            case R.id.edit_plan_back:
                saveReturn();
                break;

        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK){
            saveReturn();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveReturn() {
        content=et_content.getText().toString();
        Plan e=new Plan(tag,content,time,mon,tue,wed,thu,fri,sat,sun,state,week);
        e.setId(id);
        CRUDplan op=new CRUDplan(context);
        op.open();
        op.updateNote(e);
        op.close();
        finish();
    }
}
