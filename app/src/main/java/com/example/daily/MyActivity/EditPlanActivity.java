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
        if(state.equals("?????????")){
            check.setImageResource(R.drawable.uncheck);
        }else if(state.equals("?????????")){
            check.setImageResource(R.drawable.check);
        }
        switch(tag){
            case 0:
                plan_tag.setText("??????");
                break;
            case 1:
                plan_tag.setText("??????");
                break;
            case 2:
                plan_tag.setText("??????");
                break;
            case 3:
                plan_tag.setText("??????");
                break;
            case 4:
                plan_tag.setText("??????");
                break;
        }
        et_content.setText(content);
        tv_time.setText(time);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_plan_check:
                if(state.equals("?????????")){
                    state="?????????";
                    check.setImageResource(R.drawable.check);
                    ToastUtil.showToast(context,"????????????");
                }else if(state.equals("?????????")){
                    state="?????????";
                    check.setImageResource(R.drawable.uncheck);
                    ToastUtil.showToast(context,"????????????");

                }
                break;
            case R.id.edit_plan_repeat:
                final MultipleBottomPopup multipleBottomPopup=new MultipleBottomPopup(EditPlanActivity.this);
                new XPopup.Builder(getApplicationContext())
                        .autoOpenSoftInput(true).setPopupCallback(new SimpleCallback() { //??????????????????????????????
                    @Override
                    public void onCreated(BasePopupView v) {
                        // ????????????onCreate???????????????

                    }

                    @Override
                    public void beforeShow(BasePopupView v) {
                        super.beforeShow(v);
                        multipleBottomPopup.setWeek(new int[]{mon,tue,wed,thu,fri,sat,sun,week});
                        Log.e("tag", "beforeShow????????????show???????????????????????????????????????????????????????????????");
                    }

                    @Override
                    public void onShow(BasePopupView v) {
                        // ???????????????????????????
                    }

                    @Override
                    public void onDismiss(BasePopupView v) {
                        // ???????????????????????????getComment()
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

                    //???????????????????????????????????????????????????????????????????????????true??????
                    @Override
                    public boolean onBackPressed(BasePopupView v) {

                        ToastUtil.showToast(context,"???????????????????????????????????????XPopup???????????????");
                        return true; //????????????false
                    }
                })
//                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                        .asCustom(multipleBottomPopup)
                        .show();
                break;
            case R.id.edit_plan_changetime:
                TimePickerView pvTime = new TimePickerBuilder(EditPlanActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String [] parts = date.toString().split("[ ]")[3].split("[:]");
                        String time1=parts[0]+"???"+parts[1]+"???";
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
                        .isDialog(true) //????????????false ??????????????????DecorView ????????????????????????
                        .addOnCancelClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("pvTime", "onCancelClickListener");
                            }
                        })
                        .setItemVisibleCount(4) //?????????????????????????????????1???????????????6???????????????????????????7???
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
                        dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//??????????????????
                        dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
                        dialogWindow.setDimAmount(0.3f);
                    }
                }
                pvTime.show(v);
                break;
            case R.id.edit_plan_tag_touch:
                new XPopup.Builder(EditPlanActivity.this)
                        .atView(v)  // ?????????????????????View???????????????????????????????????????????????????
                        .asAttachList(new String[]{"??????", "??????", "??????","??????","??????"},
                                new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        switch(position){
                                            case 0:
                                                plan_tag.setText("??????");
                                                break;
                                            case 1:
                                                plan_tag.setText("??????");
                                                break;
                                            case 2:
                                                plan_tag.setText("??????");
                                                break;
                                            case 3:
                                                plan_tag.setText("??????");
                                                break;
                                            case 4:
                                                plan_tag.setText("??????");
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
