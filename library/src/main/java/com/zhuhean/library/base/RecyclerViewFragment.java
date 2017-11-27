package com.zhuhean.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhuhean.library.R;

/**
 * @author zhuhean
 * @date 24/11/2017.
 */

public class RecyclerViewFragment extends BaseFragment {

    RecyclerView recyclerView;

    @Override
    public int getContentView() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    public void setLinearLayoutManager() {
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    public void addDividerDecoration() {
        addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

}
