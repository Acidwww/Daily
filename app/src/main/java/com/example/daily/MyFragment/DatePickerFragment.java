package com.example.daily.MyFragment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_EDIT;
import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_NEW;

// 继承   接口
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    //设置对话框方法
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();   //调用getInstance
        int year = c.get(Calendar.YEAR);    // 得到年
        int month = c.get(Calendar.MONTH);   //月
        int day = c.get(Calendar.DAY_OF_MONTH);   //日
        return new DatePickerDialog(getContext(),this,year,month,day);
    }
    // 设置onDateSet方法  年，月，日
    @Override
    public void onDateSet(DatePicker datePicker, int year , int month, int day){
        Intent intent= new Intent();
        intent.putExtra("year", year+"");
        intent.putExtra("month", month+1+"");
        intent.putExtra("day", day+"");

        getTargetFragment().onActivityResult(REQUEST_CODE_NEW, Activity.RESULT_OK, intent);
    }
}
