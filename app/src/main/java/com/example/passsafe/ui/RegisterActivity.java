package com.example.passsafe.ui;

import android.app.AlertDialog;
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
import com.example.passsafe.constants.Constants;
import com.example.passsafe.greendao.UserDao;
import com.example.passsafe.secures.BCrypt;
import com.example.passsafe.storage.MySP;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    private long firstTime=0;

    @BindView(R.id.username_et)
    EditText mUsernameEt;

    @BindView(R.id.password1_et)
    EditText mPassword1Et;

    @BindView(R.id.password2_et)
    EditText mPassword2Et;

    @BindView(R.id.login)
    TextView mLoginTv;

    @BindView(R.id.register_button)
    Button mrRgisterButton;

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initWidgets();

        initActions();
    }

    @Override
    void initWidgets() {
        mMySP = new MySP(this).getmMySP();

        mMySP.save(Constants.IS_ALPHABAT_ON, true);
        mMySP.save(Constants.IS_NUMBER_ON, true);
        mMySP.save(Constants.IS_SYMBOL_ON, true);
        mMySP.save(Constants.KEY_LENGTH, 8);
    }

    @Override
    void initActions() {
        mLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    @OnClick(R.id.register_button)
    public void onViewClicked() {
        String username = mUsernameEt.getText().toString().trim();
        String password1 = mPassword1Et.getText().toString().trim();
        String password2 = mPassword2Et.getText().toString().trim();

        if(username.length() > 0
                && password1.equals(password2)
                && password1.length() >= 8){
            UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
            User user = new User();
            user.setUsername(username);
            user.setPassword(BCrypt.hashpw(password1, BCrypt.gensalt()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Long Id = Long.valueOf(sdf.format(System.currentTimeMillis()));
            user.setId(Id);
            userDao.insert(user);
            MySP.Id = Id;

            mMySP.save(Constants.IS_HAS_USER, true);

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("注意");
            builder.setMessage("您的输入不符合要求，请仔细检查！");
            builder.setPositiveButton("确定", (dialog, which) -> {
                mUsernameEt.setText(null);
                mPassword1Et.setText(null);
                mPassword2Et.setText(null);
            });
            builder.setNegativeButton("取消", null);
            builder.create().show();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(RegisterActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
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
