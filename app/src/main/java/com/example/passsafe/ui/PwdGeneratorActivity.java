package com.example.passsafe.ui;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.example.passsafe.R;
import com.example.passsafe.constants.Constants;
import com.example.passsafe.storage.MySP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PwdGeneratorActivity extends BaseActivity {
    private static final String TAG = "SettingsActivity";

    @BindView(R.id.password_length)
    RelativeLayout mPasswordLength;

    @BindView(R.id.switch_alphabat)
    SwitchCompat mSwitchAlphabat;

    @BindView(R.id.switch_number)
    SwitchCompat mSwitchNumber;

    @BindView(R.id.switch_symbol)
    SwitchCompat mSwitchSymbol;

    private MySP mMySP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_generator);
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
        mMySP = new MySP(this).getmMySP();

        mSwitchAlphabat.setChecked(mMySP.load(Constants.IS_ALPHABAT_ON, false));
        mSwitchNumber.setChecked(mMySP.load(Constants.IS_NUMBER_ON, false));
        mSwitchSymbol.setChecked(mMySP.load(Constants.IS_SYMBOL_ON, false));

    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
        mSwitchAlphabat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_ALPHABAT_ON, true);
                } else {
                    mMySP.save(Constants.IS_ALPHABAT_ON, false);
                }
            }
        });

        mSwitchNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_NUMBER_ON, true);
                } else {
                    mMySP.save(Constants.IS_NUMBER_ON, false);
                }
            }
        });

        mSwitchSymbol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMySP.save(Constants.IS_SYMBOL_ON, true);
                } else {
                    mMySP.save(Constants.IS_SYMBOL_ON, false);
                }
            }
        });
    }

}
