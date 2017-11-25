package com.zhuhean.messenger.ui.adapter;

import com.zhuhean.messenger.R;
import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.messenger.ui.widget.AvatarTextView;

/**
 * @author zhuhean
 * @date 12/11/2017.
 */
public class MessageAdapter extends QuickRecyclerAdapter<TextMessage, QuickViewHolder> {

    public MessageAdapter() {
        super(R.layout.item_message);
    }

    @Override
    protected void bindViewHolder(QuickViewHolder holder, TextMessage item) {
        AvatarTextView avatar = holder.helper.getView(R.id.avatar);
        avatar.setTitle(item.getSource());
        holder.helper.setText(R.id.title, item.getFrom())
                .setText(R.id.content, item.getContent())
                .setText(R.id.time, item.getTime());
    }
}
