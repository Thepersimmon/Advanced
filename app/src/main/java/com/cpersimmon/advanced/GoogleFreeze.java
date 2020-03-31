package com.cpersimmon.advanced;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cpersimmon.advanced.Utils.appState;
import com.cpersimmon.advanced.Utils.freezeApp;
import com.cpersimmon.advanced.Utils.shellUtils;

public class GoogleFreeze extends AppCompatActivity implements View.OnClickListener{
    String pkgName[]={"com.google.android.gms","com.android.vending","com.google.android.gsf",
            "com.google.android.googlequicksearchbox","com.google.android.onetimeinitializer","com.google.android.gms.location.history",
            "com.google.android.tts","com.google.android.syncadapters.calendar","com.google.android.partnersetup",
            "com.google.android.syncadapters.contacts"};
    int pkgState[]={0,0,0,0,0,0,0,0,0,0};
    //总共十个。
    TextView description1,description2,description3;
    Button b1,b2,b3,b4,b5,b6;
    boolean isRoot;


    public void 初始化处理(TextView d1,TextView d2,TextView d3){
        appState state=new appState(this);
        for(int i=0;i<=9;i++){
            pkgState[i]=state.sAppState(pkgName[i]);
        }
        d1.setText(生成描述文字(1));
        d2.setText(生成描述文字(3));
        d3.setText(生成描述文字(10));

    }
    public String 生成描述文字(int num){
        appState state=new appState(this);
        String str="应用详情：";
        String str1="\n";
        String str2=": 已停用";
        String str3=": 已启用";
        freezeApp f=new freezeApp(this);
        for(int i=0;i<=num-1;i++)
            switch (state.sAppState(pkgName[i])){
                case -1:
                    break;
                case 0:
                    str=str+str1+f.findName(pkgName[i])+str2;
                    break;
                case 1:
                    str=str+str1+f.findName(pkgName[i])+str3;
            }
        boolean isNull=true;
        for(int i=0;i<=num-1;i++){
            if(state.sAppState(pkgName[i])!=-1){
                isNull=false;
            }
        }
        if(isNull){
            return "应用详情：\n哦豁，空空如也";
        }
        //Log.e("重要",str);
        return str;
    }
    public void 集中处理findViewByid和点击监听(){
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

    //////////////////
    private void freeze(int num){
        //Log.e("重要","1");
        boolean isNeedDo=false;
        for(int i=0;i<=num-1;i++){
            appState state=new appState(this);
            //确保有应用需要冻结
            if(state.sAppState(pkgName[i])==1) {
                isNeedDo=true;
                break;
            }
        }
        if(!isNeedDo) {
            Toast.makeText(this, "没有应用需要执行此操作", Toast.LENGTH_SHORT).show();
            return;//无应用需要冻结
        }
        freezeApp w=new freezeApp(this);
        if(!isRoot)
        for(int i=0;i<=num-1;i++){
            //Log.e("重要","无root");
                if (pkgState[i]==1){
                    w.freeze(1,pkgName[i]);
                }
        }
        else{
            shellUtils su=new shellUtils();
            String str1="pm disable ";
            String str2="\n";
            String str = "";
            for(int i=0;i<=num-1;i++){
                if (pkgState[i]==1) {
                    str = str + str1 + pkgName[i] + str2;
                }
            }
            //Log.e("重要",str);
            su.execCommand(str,true,true);
        }
        //Log.e("重要","2");
        初始化处理(description1,description2,description3);
    }
    private void unFreeze(int num){
        boolean isNeedDo=false;
       // Log.e("重要","开始启用1");
        for(int i=0;i<=num-1;i++){
            appState state=new appState(this);
            //确保有应用需要冻结
            if(state.sAppState(pkgName[i])==0) {
                isNeedDo=true;
                break;
            }
        }
        if(!isNeedDo) {
            Toast.makeText(this, "没有应用需要执行此操作", Toast.LENGTH_SHORT).show();
           // Log.e("重要","开始启用");
            return;//无应用需要解冻
        }
        freezeApp w=new freezeApp(this);
        if(!isRoot)
        for(int i=0;i<=num-1;i++){
            //Log.e("重要","无root");
                if (pkgState[i] == 0) {
                    w.freeze(0, pkgName[i]);
                    //  Log.e("重要","开始启用"+pkgName[i]);
                }
            /*
            else {

                String str1="pm enable ";
                String str2="\n";
                for(i=0;i<=num-1;i++){
                }
             }
                    */
        }
        else{
            shellUtils su=new shellUtils();
            String str1="pm enable ";
            String str2="\n";
            String str = "";
            for(int i=0;i<=num-1;i++){
                if (pkgState[i] == 0) {
                    str = str + str1 + pkgName[i] + str2;
                }
            }
            //Log.e("重要",str);
            su.execCommand(str,true,true);
        }
        初始化处理(description1,description2,description3);
    }
    //////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_freeze);
        集中处理findViewByid和点击监听();
        初始化处理(description1,description2,description3);
        SharedPreferences settings = getSharedPreferences("data", 0);
        isRoot = settings.getBoolean("isRootOrNot", false);
        //description1.setVisibility(View.VISIBLE);
        //description2.setVisibility(View.VISIBLE);
        //description3.setVisibility(View.VISIBLE);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.google_main, menu);
        if(isRoot) {
            //Log.e("重要", "isRoot为true");
            menu.findItem(R.id.menu_root).setChecked(true);
        }
        else {
            //Log.e("重要", "isRoot为false");
            menu.findItem(R.id.menu_noroot).setChecked(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        RadioButton r1=findViewById(R.id.menu_root);
        RadioButton r2=findViewById(R.id.menu_noroot);
        SharedPreferences settings = getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = settings.edit();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
            case R.id.menu_root:
                item.setChecked(true);
                editor.putBoolean("isRootOrNot", true);
                isRoot=true;
                editor.apply();
                //Toast.makeText(this, "点了第一个", Toast.LENGTH_SHORT).show();
                //r1.setChecked(true);
                //r2.setChecked(false);
                break;
            case R.id.menu_noroot:
                item.setChecked(true);
                isRoot=false;
                editor.putBoolean("isRootOrNot", false);
                editor.apply();
                //r1.setChecked(false);
                //r2.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
