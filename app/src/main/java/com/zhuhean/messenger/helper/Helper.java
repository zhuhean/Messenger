package com.zhuhean.messenger.helper;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public final class Helper {

    private Helper() {

    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages;
        try {
            messages = (Object[]) intent.getSerializableExtra("pdus");
        } catch (ClassCastException e) {
            return null;
        }

        if (messages == null) {
            return null;
        }

        int pduCount = messages.length;
        SmsMessage[] smsMessages = new SmsMessage[pduCount];

        for (int i = 0; i < pduCount; i++) {
            byte[] pdu = (byte[]) messages[i];
            smsMessages[i] = SmsMessage.createFromPdu(pdu);
        }

        return smsMessages;
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            manager.setPrimaryClip(ClipData.newPlainText(label, text));
        }
    }

    public static String find(String content, String startWith, String endWith) {
        int start = content.indexOf(startWith);
        start += startWith.length();
        int end = content.indexOf(endWith, start);
        if (start != -1 && end != -1 && start < end) {
            return content.substring(start, end);
        } else return "";
    }

    public static boolean isDefaultSmsApp(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String defaultPackage = Telephony.Sms.getDefaultSmsPackage(context);
            return defaultPackage.equals(context.getPackageName());
        } else {
            return true;
        }
    }

}
