package com.example.daily.MyAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.daily.Others.DailyTask;
import com.example.daily.Others.onRightItemClickListener;
import com.example.daily.R;

import java.util.List;

public class LeftAdapter extends BaseAdapter {

    private onRightItemClickListener mListener=null;

    public void setOnRightItemClickListener(onRightItemClickListener listener){
        mListener=listener;
    }

    private Context context;
    private List<DailyTask> list;
    private List<DailyTask> oldlist;
    private int mRightWidth = 0;
    public LeftAdapter(Context context, List<DailyTask> list, int mRightWidth){
        this.context=context;
        this.list=list;
        oldlist=list;
        this.mRightWidth=mRightWidth;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.left_listview_item,null);
            viewHolder=new ViewHolder();
            viewHolder.item_left=convertView.findViewById(R.id.left_listview_item_leftlayout);
            viewHolder.item_right= convertView.findViewById(R.id.left_listview_item_rightlayout);
            viewHolder.title= convertView.findViewById(R.id.left_title);
            viewHolder.time= convertView.findViewById(R.id.left_time);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //动态获取left 和right的宽度
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.item_right.setLayoutParams(lp2);
        String TitleText=list.get(position).getTitle();
        String TimeText=list.get(position).getTime();
        viewHolder.title.setText(TitleText);
        viewHolder.time.setText(TimeText);
        final int pp=position;
        //左划删除
        viewHolder.item_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(mListener!=null) {
                    mListener.onRightItemClick(arg0, pp);
                }
            }
        });
        return convertView;
    }

    public static class ViewHolder{
        private LinearLayout item_left;
        private LinearLayout item_right;
        private TextView title;
        private TextView time;
    }

}
