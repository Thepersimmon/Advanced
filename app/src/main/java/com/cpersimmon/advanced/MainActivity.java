package com.cpersimmon.advanced;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.maiml.library.BaseItemLayout;

import java.util.ArrayList;
import java.util.List;

import static com.cpersimmon.advanced.Utils.toWork.isExist;
import static com.cpersimmon.advanced.Utils.toWork.说明;
import static com.cpersimmon.advanced.Utils.toWork.通用Switch点击操作;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Switch aSwitch_101;
    Switch aSwitch_201;
    Switch aSwitch_301;
    Switch aSwitch_305;
    Switch aSwitch_401;
    Switch aSwitch_402;
    Switch aSwitch_403;
    Switch aSwitch_404;
    TextView name_305;
    TextView name_305_1;
    BaseItemLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        说明(this);
        layout= findViewById(R.id.layout);
        List<String> valueList = new ArrayList<>();
        valueList.add("授权管理：去除权限");
        valueList.add("谷歌冻结");
        List<Integer> resIdList = new ArrayList<>();
        resIdList.add(R.mipmap.ic_launcher);
        resIdList.add(R.mipmap.ic_launcher);
        layout.setValueList(valueList) // 文字 list
                .setResIdList(resIdList) // icon list
                .setArrowResId(R.drawable.right_arrow_black)// 右边的箭头
                .setArrowIsShow(true) //是否显示右边的箭头
                .setItemMarginTop(18)  //设置 item的边距（全部）
                //.setItemMarginTop(10,10) // 设置 某一个item 的边距
                .setIconHeight(30)    // icon 的高度
                .setIconWidth(30)      // icon 的宽度
                .create();
        layout.setOnBaseItemClick(new BaseItemLayout.OnBaseItemClick() {
            @Override
            public void onItemClick(int position) {
                switch (position){
                    case 0:
                        Intent intent = new Intent   (MainActivity.this,AuthManager.class);
                        startActivity(intent);
                        break;
                }
            }
        });



        //分离符号
        aSwitch_101=Switch初始化(R.id.switch_101,"101");
        aSwitch_201=Switch初始化(R.id.switch_201,"201");
        aSwitch_301=Switch初始化(R.id.switch_301,"301");
        aSwitch_305=Switch初始化(R.id.switch_305,"305");
        aSwitch_401=Switch初始化(R.id.switch_401,"401");
        aSwitch_402=Switch初始化(R.id.switch_402,"402");
        aSwitch_403=Switch初始化(R.id.switch_403,"403");
        aSwitch_404=Switch初始化(R.id.switch_404,"404");
        name_305=findViewById(R.id.name_305);
        name_305_1=findViewById(R.id.name_305_1);
        name_305.setOnClickListener(this);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private Switch Switch初始化(int id, String name){
        Switch aSwitch = findViewById(id);
        aSwitch.setChecked(!isExist(name));
        aSwitch.setOnClickListener(this);
        return aSwitch;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.switch_101:
                通用Switch点击操作(this,aSwitch_101,"101");
                break;
            case R.id.switch_201:
                通用Switch点击操作(this,aSwitch_201,"201");
                break;
            case R.id.switch_301:
                通用Switch点击操作(this,aSwitch_301,"301");
                break;
            case R.id.switch_305:
                通用Switch点击操作(this,aSwitch_305,"305");
                break;
            case R.id.switch_401:
                通用Switch点击操作(this,aSwitch_401,"401");
                break;
            case R.id.switch_402:
                通用Switch点击操作(this,aSwitch_402,"402");
                break;
            case R.id.switch_403:
                通用Switch点击操作(this,aSwitch_403,"403");
                break;
            case R.id.switch_404:
                通用Switch点击操作(this,aSwitch_404,"404");
                break;
            case R.id.name_305:
                if(name_305_1.isShown()){
                    name_305_1.setVisibility(View.GONE);
                }else{
                    name_305_1.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /*
    private void 通用Switch点击操作(Switch aSwitch,String name){
        if(aSwitch.isChecked()) {
            if(!tryDelete(name)){
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        } else{
            if(!tryCreate(name)){
                Toast.makeText(this, "操作失败", Toast.LENGTH_SHORT).show();
            }
        }
        aSwitch.setChecked(!isExist(name));
    }
    */

    private void checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "本软件需要读写外部储存才能使用", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
}
