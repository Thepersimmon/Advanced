package com.cpersimmon.advanced;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cpersimmon.advanced.Utils.MultiUserAppManager;

public class MultiUserGoogleFreeze extends AppCompatActivity implements View.OnClickListener{
    Long t0,t1;
    private String pkgName[]={"com.google.android.gms","com.android.vending","com.google.android.gsf",
            "com.google.android.googlequicksearchbox","com.google.android.onetimeinitializer","com.google.android.gms.location.history",
            "com.google.android.tts","com.google.android.syncadapters.calendar","com.google.android.partnersetup",
            "com.google.android.syncadapters.contacts"};
    private String appName[]={"Google Play 服务","Google Play 商店","Google 服务框架",
            "Google","Google One Time Init","Google 位置记录",
            "Google 文字转语音引擎","Google 日历同步","Google合作伙伴设置",
            "Google通信录同步"};
    private int pkgState[]={0,0,0,0,0,0,0,0,0,0};
    private int userId=999;
    private MultiUserAppManager multiUserAppManager=new MultiUserAppManager(userId);
    private TextView description1,description2,description3;
    Button b1,b2,b3,b4,b5,b6;
    private int disable=0;
    private int enable=1;
    Handler mainHandler,workHandler;
    HandlerThread mHandlerThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        //
        mainHandler = new Handler();
        mHandlerThread = new HandlerThread("handlerThread");
        mHandlerThread.start();
        workHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg)
            {
                //Log.e("重要信息", "到达");
                multiUserAppManager.freshAppState();
                for(int i=0;i<=9;i++){
                    pkgState[i]=multiUserAppManager.getMultiUserAppState(pkgName[i]);
                }

                mainHandler.post(new Runnable() {
                    @Override
                    public void run () {
                        setContentView(R.layout.activity_google_freeze);
                        main();
                    }
                });
            }
        };
        workHandler.sendMessage(Message.obtain());
        //
        //main();
        //Log.e("重要信息","时间："+(t1-t0));
    }

    private void main(){
        集中处理findViewById和点击监听();
        firstInitSetting(description1,description2,description3);
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSetting(TextView d1, TextView d2, TextView d3){
        //t0 = System.nanoTime()/1000000000;
        multiUserAppManager.freshAppState();
        for(int i=0;i<=9;i++){
            pkgState[i]=multiUserAppManager.getMultiUserAppState(pkgName[i]);
        }
        //t1 = System.nanoTime()/1000000000;
        d1.setText(textDescription(1));
        d2.setText(textDescription(3));
        d3.setText(textDescription(10));
    }
    private void firstInitSetting(TextView d1, TextView d2, TextView d3){
        //t1 = System.nanoTime()/1000000000;
        d1.setText(textDescription(1));
        d2.setText(textDescription(3));
        d3.setText(textDescription(10));
    }
    private String textDescription(int num){
        boolean isNull=true;
        String str="应用详情：";
        String str1="\n";
        String str2=": 已停用";
        String str3=": 已启用";
        for(int i=0;i<=num-1;i++){
            switch(pkgState[i]){
                case -1:
                    break;
                case 0:
                    isNull=false;
                    str=str+str1+appName[i]+str2;
                    break;
                case 1:
                    isNull=false;
                    str=str+str1+appName[i]+str3;
                    break;
                default:
                    break;
            }
        }
        if(isNull){
            return "应用详情：\n哦豁，空空如也";
        }
        return str;
    }

    public void 集中处理findViewById和点击监听(){
        description1=findViewById(R.id.google_description1);
        description2=findViewById(R.id.google_description2);
        description3=findViewById(R.id.google_description3);
        b1=findViewById(R.id.google_button1);
        b2=findViewById(R.id.google_button2);
        b3=findViewById(R.id.google_button3);
        b4=findViewById(R.id.google_button4);
        b5=findViewById(R.id.google_button5);
        b6=findViewById(R.id.google_button6);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.google_button1:
                freeze(1);
                break;
            case R.id.google_button2:
                unFreeze(1);
                break;
            case R.id.google_button3:
                freeze(3);
                break;
            case R.id.google_button4:
                unFreeze(3);
                break;
            case R.id.google_button5:
                freeze(10);
                break;
            case R.id.google_button6:
                unFreeze(10);
                break;
        }
    }
    private void freeze(int num){
        //Log.e("重要信息","点击了冻结");
        boolean isNeedDo=false;
        for(int i=0;i<=num-1;i++){
            if(pkgState[i]==enable){
                isNeedDo=true;
                break;
            }
        }
        if(!isNeedDo) {
            Toast.makeText(this, "没有应用需要执行此操作", Toast.LENGTH_SHORT).show();
            return;//无应用需要冻结
        }
        for(int i=0;i<=num-1;i++){
            if(pkgState[i]==enable){
                multiUserAppManager.setMultiUserAppState(pkgName[i],disable);
            }
        }
        multiUserAppManager.freshAppState();
        initSetting(description1,description2,description3);
        Toast.makeText(this, "执行完毕", Toast.LENGTH_SHORT).show();
    }
    private void unFreeze(int num){
        //Log.e("重要信息","点击了解冻");
        boolean isNeedDo=false;
        for(int i=0;i<=num-1;i++){
            if(pkgState[i]==disable){
                isNeedDo=true;
                break;
            }
        }
        if(!isNeedDo) {
            Toast.makeText(this, "没有应用需要执行此操作", Toast.LENGTH_SHORT).show();
            return;//无应用需要冻结
        }
        for(int i=0;i<=num-1;i++){
            if(pkgState[i]==disable){
                multiUserAppManager.setMultiUserAppState(pkgName[i],enable);
            }
        }
        multiUserAppManager.freshAppState();
        initSetting(description1,description2,description3);
        Toast.makeText(this, "执行完毕", Toast.LENGTH_SHORT).show();
    }
}
