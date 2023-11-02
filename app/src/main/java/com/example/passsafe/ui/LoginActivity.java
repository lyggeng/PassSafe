package com.example.passsafe.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.passsafe.MyApplication;
import com.example.passsafe.R;
import com.example.passsafe.beans.User;
import com.example.passsafe.greendao.UserDao;
import com.example.passsafe.secures.BaseSecure;
import com.example.passsafe.storage.MySP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private long firstTime=0;

    @BindView(R.id.username)
    EditText mUsernameEt;

    @BindView(R.id.password)
    EditText mPasswordEt;

    @BindView(R.id.login_button)
    Button mLoginButton;

    @BindView(R.id.register)
    TextView  mRegisterTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
    }

    /**
     * 初始化组件
     */
    @Override
    void initWidgets() {

    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                if(BaseSecure.isUserInputLegal(username, password)
                        && BaseSecure.authenticateUser(username, password)){
                    UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
                    User user = userDao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique();
                    MySP.Id = user.getId();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    showAlertAlertDialog();
                }
            }
        });

        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 显示Dialog提醒
     */
    private void showAlertAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("用户名或密码输入错误，请重新输入！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUsernameEt.setText(null);
                mPasswordEt.setText(null);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(LoginActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime=secondTime;
                    return true;
                }else{
                    System.exit(0);
                }
                break;
            default:
        }
        return super.onKeyUp(keyCode, event);
    }
}
