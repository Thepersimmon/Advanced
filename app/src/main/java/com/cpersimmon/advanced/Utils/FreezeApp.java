package com.cpersimmon.advanced.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
//import android.util.Log;

public class FreezeApp extends AppCompatActivity {
    private Context ct_sc1;
    private int i_sc1;
    private String name_sc1;
    public FreezeApp(Context ct_sc0){
        ct_sc1=ct_sc0;
    }
    //0是解冻，1是冻结
    public boolean freeze(int i_sc0,String name_sc0){
        i_sc1=i_sc0;
        name_sc1=name_sc0;
        int check;
        try{
            PackageManager pm=ct_sc1.getPackageManager();
            if(i_sc1==0){
                pm.setApplicationEnabledSetting(name_sc1, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 0);
                check=pm.getApplicationEnabledSetting(name_sc1);
                if(check==0||check==1){
                    return true;
                }
                else
                    return false;
            }
            else {
                pm.setApplicationEnabledSetting(name_sc1, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, 0);
                check=pm.getApplicationEnabledSetting(name_sc1);
                if(check==0||check==1){
                    return false;
                }
                else
                    return true;
            }

        }catch (Exception e){
            return false;
        }
    }
    public String findName(String pkg){
        PackageManager pm=ct_sc1.getPackageManager();
        try {
            return pm.getApplicationLabel(pm.getApplicationInfo(pkg, PackageManager.GET_META_DATA)).toString();
        }catch (Exception e){
            Log.e("获取应用名称出错",e.toString());
            return pkg;
        }
    }
}

