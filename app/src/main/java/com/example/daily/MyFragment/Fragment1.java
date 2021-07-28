package com.example.daily.MyFragment;

//import android.content.Context;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.example.daily.MyAdapter.LeftAdapter;
import com.example.daily.MyView.LeftListView;
import com.example.daily.Others.onRightItemClickListener;
import com.example.daily.util.CRUD;
import com.example.daily.Others.DailyTask;
import com.example.daily.MyActivity.EditActivity;
import com.example.daily.Others.MyApplication;
import com.example.daily.R;
import com.example.daily.util.ToastUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Fragment1 extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    public  static final int REQUEST_CODE_NEW =1;
    public  static final int REQUEST_CODE_EDIT =2;
    private NoteDailogFragment nfragment;
    private String mParam1;
//    private ListView lv;
    private LeftListView lv;
    private FloatingActionButton fab;

    View rootview;
    private LeftAdapter adapter;
    List<DailyTask> notes=new ArrayList<>();
    Context context = MyApplication.getInstance();
    Context mcontext;
    int year,month,day;
    DatePicker datePicker;





    public Fragment1() {

    }


    public static Fragment1 newInstance(String param1) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview == null) {

            rootview = inflater.inflate(R.layout.fragment_blank, container, false);
        }

        //name= rootview.findViewById(R.id.text_1);
        //password= rootview.findViewById(R.id.text_12);

        fab=rootview.findViewById(R.id.fab);
        lv = rootview.findViewById(R.id.lv);

        adapter = new LeftAdapter(mcontext, notes,lv.getRightViewWidth());
        refreshListView();
        lv.setAdapter(adapter);
        adapter.setOnRightItemClickListener(new onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, int position) {
                String result=notes.get(position).getTitle()+"\n"+position;
                ToastUtil.showToast(context,result);
                CRUD op = new CRUD(context);
                op.open();
                op.removeNote(op.getAllNotes().get(position).getId());
                op.close();
                refreshListView();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DailyTask curNote = (DailyTask) parent.getItemAtPosition(position);

                Intent intent = new Intent(mcontext, EditActivity.class);
//                CRUD op = new CRUD(context);
//                op.open();
//                DailyTask note = op.getNote(position);
//                op.close();
                Bundle bundle = new Bundle();
                bundle.putInt("id", (int)curNote.getId());
                bundle.putString("title", curNote.getTitle());
                bundle.putString("time", curNote.getTime());
                bundle.putInt("tag", curNote.getTag());
                bundle.putString("content", curNote.getContent());
//                bundle.putInt("id", (int)note.getId());
//                bundle.putString("title", note.getTitle());
//                bundle.putString("time", note.getTime());
//                bundle.putInt("tag", note.getTag());
//                bundle.putString("content", note.getContent());
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE_EDIT);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nfragment= new NoteDailogFragment();
                nfragment.setTargetFragment(Fragment1.this,REQUEST_CODE_NEW);
                nfragment.show(getFragmentManager(),"note");

            }
        });
        initview();
        return rootview;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK) {
            if (requestCode == REQUEST_CODE_NEW) {
                String content = data.getStringExtra(NoteDailogFragment.CONTENT);
                String title = data.getStringExtra(NoteDailogFragment.TITLE);
                String time = data.getStringExtra(NoteDailogFragment.TIME);
                int tag = data.getIntExtra(NoteDailogFragment.TAG, 0);
                DailyTask note = new DailyTask(content, title, time, tag);
                CRUD op = new CRUD(context);
                op.open();
                op.addNote(note);
                op.close();
                refreshListView();
                nfragment.dismiss();
            } else if (requestCode == REQUEST_CODE_EDIT) {

                String content = data.getStringExtra(NoteDailogFragment.CONTENT);
                String title = data.getStringExtra(NoteDailogFragment.TITLE);
                String time = data.getStringExtra(NoteDailogFragment.TIME);
                int tag = data.getIntExtra(NoteDailogFragment.TAG, 0);
                long id = (long) data.getIntExtra("id", 0);
                DailyTask note = new DailyTask(content, title, time, tag);
                Log.e("AfterEdit:************", id + "");
                note.setId(id);
                CRUD op = new CRUD(mcontext);
                op.open();
                op.updateNote(note);
                op.close();
                refreshListView();
            }
        }else if(resultCode==2){
            long id=(long)data.getIntExtra("id",0);
            CRUD op = new CRUD(context);
            op.open();
            op.removeNote(id);
            op.close();
            refreshListView();
        }
    }



    private void initview() {
        //TextView tv = rootview.findViewById(R.id.text_1);
        //tv.setText(mParam1);
    }
    public void refreshListView(){
        CRUD op = new CRUD(mcontext);
        op.open();
        // set adapter
        if (notes.size() > 0) notes.clear();
        notes.addAll(op.getAllNotes());
        op.close();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mcontext = activity;
    }

}
