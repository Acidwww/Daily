package com.example.daily.MyAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daily.Others.DailyTask;
import com.example.daily.Others.Plan;
import com.example.daily.Others.Today;
import com.example.daily.Others.onRightItemClickListener;
import com.example.daily.R;

import java.util.List;

public class PlanLeftAdapter extends BaseAdapter {
    private onRightItemClickListener mListener=null;
    private PlanLeftAdapter.OnCheckClickListener mcheckListener=null;
    private Context context;
    private List<Plan> list;
    private List<Plan> oldlist;
    private int mRightWidth = 0;

    public void setOnRightItemClickListener(onRightItemClickListener listener){
        mListener=listener;
    }
    public void setOnCheckClickListener(PlanLeftAdapter.OnCheckClickListener listener){
        mcheckListener=listener;
    }

    public PlanLeftAdapter(Context context, List<Plan> list, int mRightWidth){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PlanLeftAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.left_listview_item,null);
            viewHolder=new PlanLeftAdapter.ViewHolder();
            viewHolder.item_left=convertView.findViewById(R.id.left_listview_item_leftlayout);
            viewHolder.item_right= convertView.findViewById(R.id.left_listview_item_rightlayout);
            viewHolder.title= convertView.findViewById(R.id.left_title);
            viewHolder.time= convertView.findViewById(R.id.left_time);
            viewHolder.check=convertView.findViewById(R.id.check);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (PlanLeftAdapter.ViewHolder) convertView.getTag();
        }

        //动态获取left 和right的宽度
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(mRightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.item_right.setLayoutParams(lp2);
        String TitleText=list.get(position).getContent();
        String TimeText=list.get(position).getTime();
        viewHolder.title.setText(TitleText);
        viewHolder.time.setText(TimeText);
        if(list.get(position).getState().equals("待完成")){
            Bitmap bitmap= BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/uncheck.png"));
            viewHolder.check.setImageBitmap(bitmap);
        }else if(list.get(position).getState().equals("已完成")){
            Bitmap bitmap=BitmapFactory.decodeStream(getClass().getResourceAsStream("/res/drawable/check.png"));
            viewHolder.check.setImageBitmap(bitmap);
        }

        final int pp=position;
        viewHolder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mcheckListener!=null){
                    mcheckListener.onCheckClick(v,pp);
                }
            }
        });

        //左划删除
        viewHolder.item_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mListener!=null) {
                    mListener.onRightItemClick(v, pp);
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
        private ImageView check;
    }

    public interface OnCheckClickListener{
        void onCheckClick(View v,int position);
    }
}
