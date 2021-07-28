package com.example.daily.MyFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.daily.Others.MyApplication;
import com.example.daily.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.Calendar;

import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_EDIT;
import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_NEW;

/**
 * Created by lenovo on 2018/2/8.
 */

public class NoteDailogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TAG = "tag";
    public static final String TIME = "time";
    private String mYear ,mMonth,mDay;
    private LinearLayout tag_show;
    private EditText title;
    private EditText content;
    private TextView time,tag;
    private Button creat;
    private ImageView close;
    private int Tag;
    Context context = MyApplication.getInstance();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_note_dailog, null);
        builder.setView(view);
        close= view.findViewById(R.id.note_close);
        title= view.findViewById(R.id.note_title);
        tag_show=view.findViewById(R.id.note_tag_show);
        tag= view.findViewById(R.id.note_tag);
        time= view.findViewById(R.id.note_time);
        creat= view.findViewById(R.id.note_creat);
        content= view.findViewById(R.id.note_content);
        Calendar calendar = Calendar.getInstance();
        mYear = String.valueOf(calendar.get(Calendar.YEAR) + 1);
        mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
        mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的天

        time.setText(mMonth+"月"+mDay+"日");
        close.setOnClickListener(this);
        creat.setOnClickListener(this);
        time.setOnClickListener(this);
        tag_show.setOnClickListener(this);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.note_creat:
                if (getTargetFragment()== null){
                    return;
                }else if(title.getText().toString().equals("") &&content.getText().toString().equals("")){
                    dismiss();
                    return;
                }
                else{
                    Log.e("Content:-------","title:"+title.getText().toString()+"///"+"content:"+content.getText().toString());
                    Intent intent= new Intent();
                    intent.putExtra(TITLE, title.getText().toString());
                    intent.putExtra(TAG, Tag);
                    intent.putExtra(TIME, time.getText().toString());
                    intent.putExtra(CONTENT, content.getText().toString());
                    getTargetFragment().onActivityResult(REQUEST_CODE_NEW, Activity.RESULT_OK, intent);
                }
                break;
            case R.id.note_close:
                dismiss();
                break;
            case R.id.note_time:
//                Calendar calendar=Calendar.getInstance();
//                new DatePickerDialog( getActivity(), new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
//                        Toast.makeText(context, text, Toast.LENGTH_SHORT ).show();
//                    }
//                }
//                        ,calendar.get(Calendar.YEAR)
//                        ,calendar.get(Calendar.MONTH)
//                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
//                FragmentManager fm=getFragmentManager();
//                FragmentTransaction ft=fm.beginTransaction();
//                Fragment f1=fm.findFragmentByTag("note");
//                ft.hide(f1);
//                ft.addToBackStack("hide");
//                ft.commit();
                DatePickerFragment dfragment= new DatePickerFragment();
                dfragment.setTargetFragment(this,REQUEST_CODE_NEW);
                dfragment.show(getFragmentManager(),"time");

                break;
            case R.id.note_tag_show:
                new XPopup.Builder(getContext())
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"学习", "工作", "运动","生活","其他"},
                                new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        switch(position){
                                            case 0:
                                                tag.setText("学习");
                                                break;
                                            case 1:
                                                tag.setText("工作");
                                                break;
                                            case 2:
                                                tag.setText("运动");
                                                break;
                                            case 3:
                                                tag.setText("生活");
                                                break;
                                            case 4:
                                                tag.setText("其他");
                                                break;
                                        }
                                        Tag=position;
                                    }
                                })
                        .show();
                break;

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NEW) {
            mYear = data.getStringExtra("year");
            mMonth = data.getStringExtra("month");
            mDay = data.getStringExtra("day");
            time.setText(mMonth+"月"+mDay+"日");
        }

      }

}