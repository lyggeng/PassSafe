package com.example.passsafe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.passsafe.MyApplication;
import com.example.passsafe.R;
import com.example.passsafe.adapters.KeyListAdapter;
import com.example.passsafe.beans.Item;
import com.example.passsafe.constants.Constants;
import com.example.passsafe.greendao.ItemDao;
import com.example.passsafe.storage.MySP;
import com.example.passsafe.utils.LoadUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private long firstTime=0;

    @BindView(R.id.category_self)
    LinearLayout mCategorySelf;

    @BindView(R.id.category_comm)
    LinearLayout mCategoryComm;

    @BindView(R.id.category_net)
    LinearLayout mCategoryNet;

    @BindView(R.id.category_work)
    LinearLayout mCategoryWork;

    @BindView(R.id.category_other)
    LinearLayout mCategoryOther;

    @BindView(R.id.category_layout)
    LinearLayout mCategoryLayout;

    @BindView(R.id.items_list)
    RecyclerView mItemsList;

    private MySP mMySP;

    private Item[] mKeyItems;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView ToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化组件
        initWidgets();

        //初始化动作
        initActions();
        NavActions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.menu_settings){
            Intent intent = new Intent(MainActivity.this, PwdGeneratorActivity.class);
            startActivity(intent);
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initWidgets();

        ItemDao itemDao = MyApplication.getInstance().getDaoSession().getItemDao();
        List<Item> itemList = itemDao.queryBuilder().where(ItemDao.Properties.UserId.eq(MySP.Id)).list();
    }

    /**
     * 初始化组件
     */
    @Override
    void initWidgets() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav);
        ToolBar = findViewById(R.id.ToolBar);

        mMySP = new MySP(this).getmMySP();
        mMySP.save("is_first_load", false);

        mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.SELF_ID);
        mItemsList.setLayoutManager(new LinearLayoutManager(this));
        mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.SELF_ID));
    }

    /**
     * 初始化动作
     */
    @Override
    void initActions() {
    }

    @OnClick({R.id.ToolBar,R.id.category_self, R.id.category_comm, R.id.category_net,
            R.id.category_work, R.id.category_other, R.id.add_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ToolBar:
                if (drawerLayout.isDrawerOpen(navigationView)){
                    drawerLayout.closeDrawer(navigationView);
                }else{
                    drawerLayout.openDrawer(navigationView);
                }
                break;
            case R.id.category_self:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.SELF_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.SELF_ID));
                break;
            case R.id.category_comm:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.COMM_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.COMM_ID));
                break;
            case R.id.category_net:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.NET_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.NET_ID));
                break;
            case R.id.category_work:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.WORK_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.WORK_ID));
                break;
            case R.id.category_other:
                mKeyItems = LoadUtils.loadSelfKeyItemsByCategoryId(MySP.Id, Constants.OTHER_ID);
                mItemsList.setAdapter(new KeyListAdapter(this, mKeyItems, Constants.OTHER_ID));
                break;
            case R.id.add_button:
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.DETAIL_ACTIVITY_MODE, Constants.DETAIL_ACTIVITY_ADD);
                intent.putExtra(Constants.DETAIL_ACTIVITY_MODE_DATA, bundle);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void NavActions() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                //密码生成器设置
                case R.id.pwd_config:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, PwdGeneratorActivity.class);
                    startActivity(intent);
                    break;
                //设置
                case R.id.config:
                    Intent intent2 = new Intent();
                    intent2.setClass(MainActivity.this, SettingActivity.class);
                    startActivity(intent2);
                    break;
                //退出登录
                case R.id.sign_out:
                    Intent intent3 = new Intent();
                    intent3.setClass(MainActivity.this, LoginActivity.class);
                    startActivity(intent3);
                    finish();
                    break;
                default:
            }
            return true;
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstTime>2000){
                    Toast.makeText(MainActivity.this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
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
