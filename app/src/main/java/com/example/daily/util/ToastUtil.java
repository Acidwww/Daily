package com.example.daily.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
    public static Toast mtoast;


    public static void showToast(Context context,String msg){
        if(mtoast==null){
            mtoast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }
        else{
            mtoast.cancel();
            mtoast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
        }
        mtoast.show();
    }
    public static void ShowMsg(Context context,String msg,int i){
        int a;
        if(i== Gravity.BOTTOM)
            a=80;
        else
            a=0;

        if(mtoast==null){
            mtoast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
            mtoast.setGravity(i,0,a);
        }
        else{
            mtoast.cancel();
            mtoast = Toast.makeText(context,msg,Toast.LENGTH_LONG);
            mtoast.setGravity(i,0,a);
        }
        mtoast.show();
    }
}
