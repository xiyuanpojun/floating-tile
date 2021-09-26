package com.lingc.nfloatingtile;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import com.lingc.nfloatingtile.manager.RuleManager;
import com.lingc.nfloatingtile.model.RuleModel;
import com.lingc.nfloatingtile.widget.FloatingTile;
import com.lingc.nfloatingtile.widget.TileObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by LingC on 2019/8/4 21:46
 */
public class NotificationService extends NotificationListenerService {
    private String content;

    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if (!sbn.isClearable() || sbn.getPackageName().equals(getPackageName())
                || sbn.getPackageName().equals("android")) {
            return;
        }
        PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        if (!powerManager.isScreenOn()) {
            return;
        }
        cancelAllNotifications();

        Bundle extras = sbn.getNotification().extras;
//        int id = sbn.getId();
        final Bitmap icon = extras.getParcelable(android.app.Notification.EXTRA_LARGE_ICON);
        final String title = extras.getString(android.app.Notification.EXTRA_TITLE);
        content = extras.getString(android.app.Notification.EXTRA_TEXT);

        if (content == null) {
            content = "";
        }
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
            return;
        }
        if (content.contains("下载") || content.contains("%")) {
            return;
        }
        if (checkRule(title, content)) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                FloatingTile floatingTile = new FloatingTile();
                floatingTile.setContent(icon, title, content, sbn.getPackageName(), sbn.getNotification().contentIntent);
                floatingTile.setLastTile(TileObject.lastFloatingTile);
                floatingTile.showWindow(NotificationService.this);
            }
        }).start();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    private boolean checkRule(String title, String content) {
        RuleManager ruleManager = RuleManager.getInstance();
        List<RuleModel> ruleModelList = ruleManager.load(this);
        //只要一个匹配 就说明不显示该内容
        //标题提取数字，内容取关键词
        for (RuleModel ruleModel : ruleModelList) {
            List<String> keyword = ruleModel.getKeyword();
            RuleModel.Price price = ruleModel.getPrice();
            boolean f1 = false, f2 = false;
            for (String k : keyword) {
                if (content.contains(k)) {
                    f1 = true;
                    break;
                }
            }
            String number = getNumber(title);
            if (price == null) {
                f2 = true;
            } else {
                if (number != null && price.getLow() != null && price.getHigh() != null) {
                    if (price.getLow() >= Long.parseLong(number) && price.getHigh() <= Long.parseLong(number)) {
                        f2 = true;
                    }
                }
            }
            if (f1 && f2) {
                return true;
            }
        }
        return false;
    }

    private String getNumber(String title) {
        String reg = "\\D+(\\d+)\\D+";
        Pattern p2 = Pattern.compile(reg);
        Matcher m2 = p2.matcher(title);
        if (m2.find()) {
            return m2.group(1);
        }
        return null;
    }
}
