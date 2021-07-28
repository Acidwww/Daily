package com.example.daily.MyActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.daily.MyFragment.NoteDailogFragment;
import com.example.daily.Others.MyApplication;
import com.example.daily.R;
import com.example.daily.util.CRUD;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etitle,econtent;
    private ImageView eback;
    private LinearLayout Ltag,Ldelete,Lmore;
    private TextView etag,etime;
    private String content,time,title;
    private int tag,id;
    Context context = MyApplication.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Ldelete=findViewById(R.id.edit_delete);
        Lmore=findViewById(R.id.edit_more);
        eback=findViewById(R.id.edit_back);
        Ltag=findViewById(R.id.edit_tag_touch);
        etitle = findViewById(R.id.edit_title);
        etime = findViewById(R.id.edit_time);
        econtent = findViewById(R.id.edit_content);
        etag = findViewById(R.id.edit_tag);
        etime.setOnClickListener(this);
        Ltag.setOnClickListener(this);
        eback.setOnClickListener(this);
        Ldelete.setOnClickListener(this);
        Lmore.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();

        title = bundle.getString("title");
        content = bundle.getString("content");
        time = bundle.getString("time");
        tag = bundle.getInt("tag");
        id=bundle.getInt("id");
        switch(tag){
            case 0:
                etag.setText("学习");
                break;
            case 1:
                etag.setText("工作");
                break;
            case 2:
                etag.setText("运动");
                break;
            case 3:
                etag.setText("生活");
                break;
            case 4:
                etag.setText("其他");
                break;
        }
        etitle.setText(title);
        econtent.setText(content);
        etime.setText(time);
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

    public void saveReturn(){
        Intent intent = new Intent();
        intent.putExtra(NoteDailogFragment.TITLE, etitle.getText().toString());
        intent.putExtra(NoteDailogFragment.CONTENT, econtent.getText().toString());
        intent.putExtra(NoteDailogFragment.TAG, tag);
        intent.putExtra("id", id);
        intent.putExtra(NoteDailogFragment.TIME,etime.getText().toString());//dateToStr()
        setResult(RESULT_OK, intent);
        finish();
    }

    public String dateToStr(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_time:
                Calendar calendar= Calendar.getInstance();
                new DatePickerDialog( EditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etime.setText((month + 1) + "月" + dayOfMonth + "日");
//                        String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
//                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT ).show();
                    }
                }
                        ,calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.edit_tag_touch:
                new XPopup.Builder(EditActivity.this)
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"学习", "工作", "运动","生活","其他"},
                                new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        switch(position){
                                            case 0:
                                                etag.setText("学习");
                                                break;
                                            case 1:
                                                etag.setText("工作");
                                                break;
                                            case 2:
                                                etag.setText("运动");
                                                break;
                                            case 3:
                                                etag.setText("生活");
                                                break;
                                            case 4:
                                                etag.setText("其他");
                                                break;
                                        }
                                            tag=position;
                                    }
                                })
                        .show();
                break;
            case R.id.edit_back:
                saveReturn();
                break;
            case R.id.edit_delete:
                new XPopup.Builder(EditActivity.this).asConfirm("确定删除吗？", "",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                Intent intent = new Intent();
                                intent.putExtra("id", id);
                                setResult(2, intent);
                                finish();
                            }
                        })
                        .show();
                break;
            case R.id.edit_more:
                new XPopup.Builder(EditActivity.this)
                        .atView(v)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
                        .asAttachList(new String[]{"学习", "工作", "运动","生活","其他"},
                                new int[]{R.mipmap.study, R.mipmap.work,R.mipmap.sport,R.mipmap.life,R.mipmap.other},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {

                                        }
                                    }
                                )
                        .show();
                break;
        }
    }
}