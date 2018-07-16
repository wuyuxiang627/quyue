package com.gm.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jxj on 2016/1/18.
 */


public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    public List<T> mdatas = new ArrayList<>();
    private int layoutId;


    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mdatas = datas;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.layoutId = layoutId;
    }


    public void add(T datas) {
        if (datas != null) {
            this.mdatas.add(datas);
            notifyDataSetChanged();
        }
    }


    public void addAll(List<T> datas) {
        if (datas != null) {
            this.mdatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    public void setNewData(List<T> datas) {
        if (datas != null) {
            this.mdatas = datas;
            notifyDataSetChanged();
        }
    }


    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }


    @Override
    public int getCount() {
        return mdatas.size();
    }

    @Override
    public T getItem(int position) {
        return mdatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position), holder.getPosition());
        return holder.getmConvertView();
    }

    public abstract void convert(ViewHolder holder, T t, int position);


}
