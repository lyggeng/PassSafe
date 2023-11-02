package com.example.passsafe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.passsafe.MyApplication;
import com.example.passsafe.R;
import com.example.passsafe.beans.User;
import com.example.passsafe.greendao.UserDao;
import com.example.passsafe.storage.MySP;

import butterknife.ButterKnife;

public class SettingActivity extends Activity implements View.OnClickListener{

    private TextView account;
    private TextView password;
    private TextView synchronous;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        Init();
    }

    private void Init() {
        account = findViewById(R.id.account);
        account.setOnClickListener(this);
        password = findViewById(R.id.pwd);
        password.setOnClickListener(this);
        synchronous = findViewById(R.id.synchronous);
        synchronous.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account:
                final AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(this);
                View view1 = View.inflate(this, R.layout.activity_setting_alertdialog1, null);
                final EditText et = view1.findViewById(R.id.changeAccount);
                Button bu = view1.findViewById(R.id.submitAccount);
                alertDialog1
                        .setTitle("修改用户名")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(view1)
                        .create();
                final AlertDialog show = alertDialog1.show();
                bu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MySP mySP = new MySP(SettingActivity.this).getmMySP();
                        String newAccount = et.getText().toString().trim();

                        UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
                        User user = userDao.queryBuilder().where(UserDao.Properties.Id.eq(MySP.Id)).unique();
                        user.setUsername(newAccount);
                        userDao.update(user);

                        Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        show.dismiss();
                    }
                });
                break;
            case R.id.pwd:
                Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.synchronous:

                break;
        }
    }
}
