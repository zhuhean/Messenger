package com.zhuhean.library.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuhean
 * @date 19/11/2017.
 */
public abstract class QuickRecyclerAdapter<E, VH extends QuickViewHolder> extends RecyclerView.Adapter<VH> {

    private int layoutRes;
    private List<E> data;

    private LayoutInflater inflater;

    public QuickRecyclerAdapter(@LayoutRes int layoutRes) {
        this(layoutRes, new ArrayList<E>());
    }

    public QuickRecyclerAdapter(@LayoutRes int layoutRes, List<E> data) {
        this.layoutRes = layoutRes;
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(layoutRes, parent, false);
        return createViewHolder(itemView);
    }

    @SuppressWarnings("unchecked")
    protected VH createViewHolder(View view) {
        return (VH) new QuickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindViewHolder(holder, data.get(position));
    }

    protected abstract void bindViewHolder(VH holder, E item);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void update(List<E> items) {
        data = items;
        notifyDataSetChanged();
    }

    public void add(E item) {
        int position = data.size();
        data.add(item);
        notifyItemInserted(position);
    }

    public void addAll(E... items) {
        addAll(Arrays.asList(items));
    }

    public void addAll(List<E> items) {
        data.addAll(items);
        notifyItemRangeInserted(data.size(), items.size());
    }

    public void remove(E item) {
        remove(data.indexOf(item));
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void swapData(int i, int j) {
        Collections.swap(data, i, j);
        notifyItemMoved(i, j);
    }

}
