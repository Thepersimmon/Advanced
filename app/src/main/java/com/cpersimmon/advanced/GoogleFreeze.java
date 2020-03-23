package com.cpersimmon.advanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cpersimmon.advanced.Utils.appState;
import com.cpersimmon.advanced.Utils.freezeApp;

public class GoogleFreeze extends AppCompatActivity implements View.OnClickListener{
    String pkgName[]={"com.google.android.gms","com.android.vending","com.google.android.gsf",
            "com.google.android.googlequicksearchbox","com.google.android.onetimeinitializer","com.google.android.gms.location.history",
            "com.google.android.tts","com.google.android.syncadapters.calendar","com.google.android.partnersetup",
            "com.google.android.syncadapters.contacts"};
    int pkgState[]={0,0,0,0,0,0,0,0,0,0};
    //总共十个。
    TextView description1,description2,description3;
    Button b1,b2,b3,b4,b5,b6;
    boolean isRoot=false;


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
        if(!isNeedDo)
            return;//无应用需要冻结
        freezeApp w=new freezeApp(this);
        for(int i=0;i<=num-1;i++){
            if(!isRoot)
                if (pkgState[i]==1){
                    w.freeze(1,pkgName[i]);
                }
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
           // Log.e("重要","开始启用");
            return;//无应用需要解冻
        }
        freezeApp w=new freezeApp(this);
        for(int i=0;i<=num-1;i++){
            if(!isRoot)
                if (pkgState[i]==0){
                    w.freeze(0,pkgName[i]);
                  //  Log.e("重要","开始启用"+pkgName[i]);
                }
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
        //description1.setVisibility(View.VISIBLE);
        //description2.setVisibility(View.VISIBLE);
        //description3.setVisibility(View.VISIBLE);
    }
}
