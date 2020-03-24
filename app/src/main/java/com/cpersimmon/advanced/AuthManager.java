package com.cpersimmon.advanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.cpersimmon.advanced.Utils.shellUtils;

import static com.cpersimmon.advanced.Utils.toWork.tryCreate;
import static com.cpersimmon.advanced.Utils.toWork.tryDelete;
import static com.cpersimmon.advanced.Utils.toWork.通用Switch点击操作;
import static com.cpersimmon.advanced.Utils.toWork.isExist;

public class AuthManager extends AppCompatActivity implements View.OnClickListener{
    TextView t1;
    TextView t2;
    Switch s1;
    Switch s2;
    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_manager);
        t1=findViewById(R.id.text_1);
        t2=findViewById(R.id.text_2);
        s1=findViewById(R.id.switch_1);
        s2=findViewById(R.id.switch_2);
        b1=findViewById(R.id.button_1);
        b2=findViewById(R.id.button_2);
        t1.setText("取消读取手机权限");
        t2.setText("取消读写存储空间");
        s1.setChecked(!isExist("501"));
        s1.setOnClickListener(this);
        s2.setChecked(!isExist("502"));
        s2.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.switch_1:
                通用Switch点击操作(this,s1,"501");
                //生效问题处理();
            break;
            case R.id.switch_2:
                通用Switch点击操作(this,s2,"502");
                //生效问题处理();
            break;
            case  R.id.button_1:
                生效问题处理(1);
            break;
            case R.id.button_2:
                生效问题处理(2);
            break;
        }
    }
    private boolean 生效问题处理(int i){
        shellUtils su=new shellUtils();
        //shellUtils.CommandResult Result=new shellUtils.CommandResult();
        Log.e("测试","OK");
        String str="am kill";
        String str_0="pm disable";
        String str_1="pm enable";
        String str1=" ";
        String str2="com.miui.securitycenter";
        String str3="com.lbe.security.miui";
        String s1=str+str1+str2;
        switch (i){
            case 1:
                shellUtils.CommandResult Result1=su.execCommand(s1,true,true);

                return (Result1.result==0);
            case 2:
                boolean b=isExist("403");
                if(b){
                    tryDelete("403");
                }
                shellUtils.CommandResult Result2=su.execCommand(str_0+str1+str3+"\n"+str_1+str1+str3,true,true);
                //su.execCommand(str_1+str1+str3,true,true);
                if(b){
                    tryCreate("403");
                }
                return (Result2.result==0);
            default:
                return false;
        }
        //shellUtils.CommandResult Result=su.execCommand("pm enable com.x7890.shortcutcreator",true,true);
        /*
        shellUtils.CommandResult Result=su.execCommand(s1,true,true);
        try {
            Thread.sleep(100);
        }catch (Exception e){
            Log.e("重要",e.toString());
        }
        shellUtils.CommandResult Result1=su.execCommand(s2,true,true);
        Log.e("重要信息1",String.valueOf(Result1.result));
        Log.e("重要信息2",Result1.errorMsg);
        Log.e("重要信息3",Result1.successMsg);
        return (Result1.result==0)&&(Result1.result==0);
        */
        /*
        if(Result.result==0)
            return true;
        else
            return false;
            */
    }

}
