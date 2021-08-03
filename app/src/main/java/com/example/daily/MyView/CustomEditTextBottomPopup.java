package com.example.daily.MyView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import com.example.daily.Others.MyApplication;
import com.example.daily.Others.Plan;
import com.example.daily.R;
import com.example.daily.util.CRUDplan;
import com.example.daily.util.MyTime;
import com.example.daily.util.ToastUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;


import java.util.Date;

public class CustomEditTextBottomPopup extends BottomPopupView {
    private ImageView comfirm,time,week,tag;
    private EditText et;
    Plan myPlan=new Plan();
    Context context = MyApplication.getInstance();
    private int Tag=0;

    public CustomEditTextBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_edittext_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        MyTime myTime=new MyTime();
        myPlan.setTime(myTime.getTime());
    }

    @Override
    protected void onShow() {
        super.onShow();

        MyListener myListener=new MyListener();
        et=findViewById(R.id.et_content);
        comfirm=findViewById(R.id.comfirm);
        time=findViewById(R.id.plan_time);
        week=findViewById(R.id.plan_weekday);
        tag=findViewById(R.id.plan_tag);
        comfirm.setOnClickListener(myListener);
        time.setOnClickListener(myListener);
        week.setOnClickListener(myListener);
        tag.setOnClickListener(myListener);
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();

    }

    public String getComment() {
        EditText et = findViewById(R.id.et_content);
        return et.getText().toString();
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.comfirm:
                    if(et.getText().toString()=="请输入计划"){
                        ToastUtil.showToast(context,"内容不能为空！");
                    }else if(myPlan.getWeek()==0){
                        ToastUtil.showToast(context,"没有设置循环日期！");
                    }else {
                        myPlan.setContent(et.getText().toString());
                        myPlan.setTag(Tag);
                        myPlan.setState("待完成");
                        CRUDplan cd=new CRUDplan(context);
                        cd.open();
                        cd.addNote(myPlan);
                        cd.close();
                        dismiss();
                    }

                    break;
                case R.id.plan_time:
                    TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            String [] parts = date.toString().split("[ ]")[3].split("[:]");
                            String time=parts[0]+"时"+parts[1]+"分";
                            myPlan.setTime(time);
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
                case R.id.plan_weekday:
                        final MultipleBottomPopup multipleBottomPopup=new MultipleBottomPopup(getContext());
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
                            // 完全隐藏的时候执行getComment()
                            int[] week=multipleBottomPopup.getWeek();
                            if(week[7]==1){
                                myPlan.setWeek(1);
                                myPlan.setMonday(week[0]);
                                myPlan.setTuesday(week[1]);
                                myPlan.setWednesday(week[2]);
                                myPlan.setThursday(week[3]);
                                myPlan.setFriday(week[4]);
                                myPlan.setSaturday(week[5]);
                                myPlan.setSunday(week[6]);
                            }
                        }

                        //如果你自己想拦截返回按键事件，则重写这个方法，返回true即可
//                        @Override
//                        public boolean onBackPressed(BasePopupView v) {
//
//                            ToastUtil.showToast(context,"我拦截的返回按键，按返回键XPopup不会关闭了");
//                            return true; //默认返回false
//                        }
                    })
//                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                            .asCustom(multipleBottomPopup)
                            .show();
                    break;
                case R.id.plan_tag:
                    new XPopup.Builder(getContext())
                            .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                            .asAttachList(new String[]{"学习", "工作", "运动","生活","其他"},
                                    new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            switch(position){
                                                case 0:
                                                    tag.setImageResource(R.mipmap.study);
                                                    break;
                                                case 1:
                                                    tag.setImageResource(R.mipmap.work);
                                                    break;
                                                case 2:
                                                    tag.setImageResource(R.mipmap.sport);
                                                    break;
                                                case 3:
                                                    tag.setImageResource(R.mipmap.life);
                                                    break;
                                                case 4:
                                                    tag.setImageResource(R.mipmap.other);
                                                    break;
                                            }
                                            Tag=position;
                                        }
                                    })
                            .show();
                    break;
            }
        }
    }
//    public void showMultiChioceDialog(View v) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("于谦:");
//        builder.setIcon(R.mipmap.ic_launcher);
//        final String[] items = new String[]{"抽烟", "喝酒", "烫头"};/*设置多选的内容*/
//        final boolean[] checkedItems = new boolean[]{false, true, false};/*设置多选默认状态*/
//        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {/*设置多选的点击事件*/
//            @Override
//            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
//                checkedItems[which] = isChecked;
//                ToastUtil.showToast(context, items[which] + " 状态: " + isChecked);
//            }
//        });
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ToastUtil.showToast(context, "OK");
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ToastUtil.showToast(context, "cancel");
//            }
//        });
//        builder.setCancelable(false);
//        builder.show();
//    }
}