package com.lingc.nfloatingtile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lingc.nfloatingtile.manager.RuleManager;
import com.lingc.nfloatingtile.model.RuleModel;

import java.util.ArrayList;
import java.util.List;

public class SetRuleActivity extends AppCompatActivity {
    private EditText ruleText;
    private Button editSave;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rule);
        if (Build.VERSION.SDK_INT >= 23) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#e5e5e5"));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        findView();

        initView();
    }


    private void findView() {
        ruleText = findViewById(R.id.edit_content);
        editSave = findViewById(R.id.edit_save);
        back = findViewById(R.id.back);
    }

    private void initView() {
        RuleManager ruleManager = RuleManager.getInstance();
        List<RuleModel> ruleModelList = ruleManager.load(this);
        String rule;
        if (!ruleModelList.isEmpty()) {
            rule = JSON.toJSONString(ruleModelList);
        } else {
            rule = "[{\"keyword\":[\"Message\",\"关键词二\"]},{\"keyword\":[\"关键词三\",\"关键词四\"],\"price\":{\"low\":50,\"high\":100}}]";
        }
        ruleText.setText(rule);

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rule = ruleText.getText().toString();
                List<RuleModel> modelList = new ArrayList<>();
                try {
                    modelList = JSON.parseArray(rule, RuleModel.class);
                    Toast.makeText(SetRuleActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(SetRuleActivity.this, "规则数据有误 未设置规则", Toast.LENGTH_SHORT).show();
                }
                RuleManager.getInstance().save(SetRuleActivity.this, modelList);
                startActivity(new Intent(SetRuleActivity.this, MainActivity.class));
                SetRuleActivity.this.finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetRuleActivity.this, MainActivity.class));
                SetRuleActivity.this.finish();
            }
        });

    }
}
