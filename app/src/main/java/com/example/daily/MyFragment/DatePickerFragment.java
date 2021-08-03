package com.example.daily.MyFragment;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.daily.MyActivity.MainActivity;
import com.example.daily.Others.MyApplication;
import com.example.daily.util.ToastUtil;

import java.util.Calendar;

import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_EDIT;
import static com.example.daily.MyFragment.Fragment1.REQUEST_CODE_NEW;

// 继承   接口
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    Calendar calendar = Calendar.getInstance();
    String mYear = String.valueOf(calendar.get(Calendar.YEAR));
    String mMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);        //获取日期的月
    String mDay = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));      //获取日期的天
    String Today=mYear+"年"+mMonth+"月"+mDay+"日";
    private Context context = MyApplication.getInstance();

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
        int Mymonth=month+1;
        String today=year+"年"+Mymonth+"月"+day+"日";
        if(today.compareTo(Today)<0){
            ToastUtil.showToast(context,"日期不能早于今天！");
        }else{
            Intent intent= new Intent();
            intent.putExtra("year", year+"");
            intent.putExtra("month", month+1+"");
            intent.putExtra("day", day+"");

            getTargetFragment().onActivityResult(REQUEST_CODE_NEW, Activity.RESULT_OK, intent);
        }

    }
}
