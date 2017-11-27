package com.zhuhean.messenger.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zhuhean.messenger.App;
import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.messenger.model.TextMessageDao;
import com.zhuhean.library.base.RecyclerViewFragment;

import java.util.List;

/**
 * @author zhuhean
 * @date 24/11/2017.
 */

public class MessageFragment extends RecyclerViewFragment {

    private int messageType;
    private MessageAdapter adapter;

    public static MessageFragment newInstance(int messageType) {
        Bundle args = new Bundle();
        args.putInt("type", messageType);
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageType = getArguments().getInt("type");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLinearLayoutManager();
        addDividerDecoration();
        setAdapter(adapter = new MessageAdapter());
        loadMessages();
    }

    private void loadMessages() {
        TextMessageDao dao = App.daoSession().getTextMessageDao();
        List<TextMessage> messages = dao.queryBuilder()
                .where(TextMessageDao.Properties.Type.eq(messageType))
                .orderDesc(TextMessageDao.Properties.Timestamp)
                .build().list();
        adapter.addAll(messages);
    }

}
