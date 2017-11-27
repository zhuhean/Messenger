package com.zhuhean.messenger.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

import com.zhuhean.messenger.App;
import com.zhuhean.messenger.helper.Helper;
import com.zhuhean.messenger.helper.NotificationHelper;
import com.zhuhean.messenger.model.TextMessage;
import com.zhuhean.messenger.model.TextMessageDao;
import com.zhuhean.library.widget.Toasty;

/**
 * @author zhuhean
 * @date 11/11/2017.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String TAG = SmsReceiver.class.getSimpleName();
    private static final String SMS_DELIVER = "android.provider.Telephony.SMS_DELIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (SMS_DELIVER.equals(action)) {
            SmsMessage[] messages = Helper.getMessagesFromIntent(intent);
            if (messages == null) {
                return;
            }
            TextMessage textMessage = new TextMessage(messages);
            if (textMessage.hasSmsCode()) {
                String code = textMessage.getSmsCode();
                if (!TextUtils.isEmpty(code)) {
                    Helper.copyToClipboard(context, "验证码", code);
                    Toasty.success(context, "验证码【" + code + "】已复制到剪贴板").show();
                }
            }
            if (!textMessage.isSpam()) {
                NotificationHelper.showNotification(context, textMessage.getFrom(), textMessage.getContent());
            }
            saveToDataBase(textMessage);
        }
    }

    private void saveToDataBase(TextMessage textMessage) {
        TextMessageDao dao = App.daoSession().getTextMessageDao();
        dao.insert(textMessage);
    }

    public static void d(String message) {
        Log.d(TAG, message);
    }

}
