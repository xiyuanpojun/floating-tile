package com.lingc.nfloatingtile.manager;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lingc.nfloatingtile.model.RuleModel;
import com.lingc.nfloatingtile.util.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class RuleManager {
    private final static String LOCAL_ROLE_KEY = "local_role_key";
    private final static RuleManager M_INSTALL = new RuleManager();
    private final static List<RuleModel> RULE_MODEL_LIST = new ArrayList<>();

    private RuleManager() {
    }

    public static RuleManager getInstance() {
        return M_INSTALL;
    }

    private synchronized void save(List<RuleModel> ruleModelList) {
        RuleManager.RULE_MODEL_LIST.clear();
        if (ruleModelList != null && !ruleModelList.isEmpty()) {
            RuleManager.RULE_MODEL_LIST.addAll(ruleModelList);
        }
    }

    public synchronized void save(Context context, List<RuleModel> ruleModelList) {
        save(ruleModelList);
        String rule = JSON.toJSONString(RuleManager.RULE_MODEL_LIST);
        SharedPreferencesUtils.putString(context, LOCAL_ROLE_KEY, rule);
    }

    public synchronized List<RuleModel> load(Context context) {
        if (RuleManager.RULE_MODEL_LIST.isEmpty()) {
            String rule = SharedPreferencesUtils.getString(context, LOCAL_ROLE_KEY, null);
            if (rule != null) {
                List<RuleModel> models = JSON.parseArray(rule, RuleModel.class);
                save(models);
            }
        }
        return RuleManager.RULE_MODEL_LIST;
    }
}
