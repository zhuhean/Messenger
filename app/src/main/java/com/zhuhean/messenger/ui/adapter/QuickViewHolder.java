package com.zhuhean.messenger.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author zhuhean
 * @date 19/11/2017.
 */

public class QuickViewHolder extends RecyclerView.ViewHolder {

    protected final View itemView;
    public final ViewHelper helper;

    public QuickViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        helper = new ViewHelper(itemView);
    }

}
