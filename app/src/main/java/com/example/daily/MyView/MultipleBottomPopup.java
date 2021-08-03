package com.example.daily.MyView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.daily.R;
import com.example.daily.util.ToastUtil;
import com.lxj.xpopup.core.BottomPopupView;

public class MultipleBottomPopup extends BottomPopupView implements View.OnClickListener {

    private LinearLayout mon,tue,wed,thu,fri,sat,sun;
    private TextView cancel,comfirm;



    private int[] week={0,0,0,0,0,0,0,0};

    public MultipleBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.costom_multiple_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onShow() {


        cancel.setOnClickListener(this);
        comfirm.setOnClickListener(this);
        mon .setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thu.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);
        super.onShow();
    }

    @Override
    protected void beforeShow() {
        super.beforeShow();
        cancel=findViewById(R.id.cancel_week);
        comfirm=findViewById(R.id.comfirm_week);
        mon=findViewById(R.id.mon);
        tue=findViewById(R.id.tue);
        wed=findViewById(R.id.wed);
        thu=findViewById(R.id.thu);
        fri=findViewById(R.id.fri);
        sat=findViewById(R.id.sat);
        sun=findViewById(R.id.sun);
        if(week[7]!=0){
            mon.setSelected(week[0]!=0);
            tue.setSelected(week[1]!=0);
            wed.setSelected(week[2]!=0);
            thu.setSelected(week[3]!=0);
            fri.setSelected(week[4]!=0);
            sat.setSelected(week[5]!=0);
            sun.setSelected(week[6]!=0);
        }
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }

    public int[] getWeek(){
       return week;
    }
    public void setWeek(int[] week) {
        this.week = week;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_week:
                dismiss();
                break;
            case R.id.comfirm_week:
                for(int i=0;i<7;i++){
                    if(week[i]!=0){
                        week[7]=1;
                        dismiss();
                        return;
                    }
            }
                ToastUtil.showToast(getContext(),"至少选择一天！");
                break;
            case R.id.mon:
                if(week[0]==0){
                    mon.setSelected(true);
                    week[0]=1;
                }else if(week[0]==1){
                mon.setSelected(false);
                week[0]=0;
            }

                break;
            case R.id.tue:
                if(week[1]==0){
                    tue.setSelected(true);
                    week[1]=1;
                }else if(week[1]==1){
                    tue.setSelected(false);
                    week[1]=0;
                }
                break;
            case R.id.wed:
                if(week[2]==0){
                    wed.setSelected(true);
                    week[2]=1;
                }else if(week[2]==1){
                    wed.setSelected(false);
                    week[2]=0;
                }
                break;
            case R.id.thu:
                if(week[3]==0){
                    thu.setSelected(true);
                    week[3]=1;
                }else if(week[3]==1){
                    thu.setSelected(false);
                    week[3]=0;
                }
                break;
            case R.id.fri:
                if(week[4]==0){
                    fri.setSelected(true);
                    week[4]=1;
                }else if(week[4]==1){
                    fri.setSelected(false);
                    week[4]=0;
                }
                break;
            case R.id.sat:
                if(week[5]==0){
                    sat.setSelected(true);
                    week[5]=1;
                }else if(week[5]==1){
                    sat.setSelected(false);
                    week[5]=0;
                }
                break;
            case R.id.sun:
                if(week[6]==0){
                    sun.setSelected(true);
                    week[6]=1;
                }else if(week[6]==1){
                    sun.setSelected(false);
                    week[6]=0;
                }
                break;

        }
    }

}