package com.zhuhean.messenger.model;

import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.zhuhean.messenger.helper.DateHelper;
import com.zhuhean.messenger.helper.Helper;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class TextMessage {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_SMS_CODE = 10;
    public static final int TYPE_SPAM = 20;

    @Id
    private String id;

    private long timestamp;
    private String from;
    private String content;
    private int type = TYPE_DEFAULT;

    public TextMessage(SmsMessage[] messages) {
        if (messages == null || messages.length < 1) return;
        SmsMessage first = messages[0];
        StringBuilder sb = new StringBuilder();
        for (SmsMessage smsMessage : messages) {
            sb.append(smsMessage.getMessageBody());
        }
        content = sb.toString();
        from = first.getOriginatingAddress();
        timestamp = first.getTimestampMillis();
        id = UUID.randomUUID().toString();
        resolveType();
    }

    public void resolveType() {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        if (content.contains("退订")) {
            type = TYPE_SPAM;
            return;
        }
        if (content.contains("验证码")) {
            type = TYPE_SMS_CODE;
        }
    }

    @Generated(hash = 1031664629)
    public TextMessage(String id, long timestamp, String from, String content,
                       int type) {
        this.id = id;
        this.timestamp = timestamp;
        this.from = from;
        this.content = content;
        this.type = type;
    }

    public TextMessage() {
        this.id = UUID.randomUUID().toString();
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getTime() {
        return DateHelper.asTime(timestamp);
    }

    public void setContent(String content) {
        this.content = content;
        resolveType();
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSpam() {
        return type == TYPE_SPAM;
    }

    public boolean hasSmsCode() {
        return type == TYPE_SMS_CODE;
    }

    public String getSmsCode() {
        Pattern pattern = Pattern.compile("\\d{4,6}");
        Matcher matcher = pattern.matcher(content);
        boolean found = matcher.find();
        if (found) return matcher.group();
        else return "";
    }

    public String getSource() {
        String result = Helper.find(content, "【", "】");
        return TextUtils.isEmpty(result) ? from : result;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
