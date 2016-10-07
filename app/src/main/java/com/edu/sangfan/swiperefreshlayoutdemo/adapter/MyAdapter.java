package com.edu.sangfan.swiperefreshlayoutdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.sangfan.swiperefreshlayoutdemo.R;

import java.util.List;

/**
 * 适配器
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    List<String> mDatas;
    private LayoutInflater mInflater;
    private onItemClickListener listener;

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(List<String> datas) {
        this.mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position));
    }

    //返回数据源的个数
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position, String city){
        mDatas.add(position, city);
        notifyItemInserted(position);
    }

    public void removeData(int position){
        mDatas.remove(position);
       notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(v, getLayoutPosition(),
                                mDatas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onClick(View v, int position, String city);
    }
}
