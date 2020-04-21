package com.cpersimmon.advanced.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
public class AppState {
    private Context context;
    public AppState(Context context){
        this.context=context;
    }

    public int sAppState(String pkg){
        int  state;
        PackageManager pm=context.getPackageManager();
        if( checkAppInstalled(context,pkg)){
            state=pm.getApplicationEnabledSetting(pkg);
            return (state==0||state==1)?1:0;//0和1都是未冻结
        }
        else
            return -1;
    }

    private boolean checkAppInstalled(Context context, String pkgName) {
        if (pkgName== null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo == null) {
            return false;
        } else {
            return true;//true为安装了，false为未安装
        }
    }
}
