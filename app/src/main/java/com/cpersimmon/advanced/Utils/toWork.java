package com.cpersimmon.advanced.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;

import static com.cpersimmon.advanced.Utils.copyAssets.copyFileFromAssets;

public class toWork {
    private static String path = "/storage/emulated/0/Cpersimmon";
    public  static boolean isExist(String name){
        File file= new File(path,name);
        try {
            return file.exists();
        }catch (Exception e){
            return false;
        }
    }
    public static boolean tryCreate(String name){
        File file= new File(path,name);
        预处理();
        try {
            file.createNewFile();
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    public static boolean tryDelete(String name){
        File file= new File(path,name);
        try {
            file.delete();
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private static void 预处理(){
        String path_0="/storage/emulated/0";
        String name_0="Cpersimmon";
        File file=new File(path_0,name_0);
        if(!file.exists()){
            try {
                file.mkdir();
            }catch (Exception e)
            {
                Log.e("veryImportant",e.toString());
            }
        }
    }
    public static void 说明(Context context){
        if(!isExist("请不要修改本目录下文件")){
            tryCreate("请不要修改本目录下文件");
        }
        if(!isExist("README.MD")){
          //  copyAssets(context,"README.MD",path);
           Log.e("MainActivity", "o98k" );
            copyFileFromAssets(context,"README.MD",path+"/README.MD");
        }
    }

    public static void 通用Switch点击操作(Context context, Switch aSwitch, String name){
        if(aSwitch.isChecked()) {
            if(!tryDelete(name)){
                Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
            }
        } else{
            if(!tryCreate(name)){
                Toast.makeText(context, "操作失败", Toast.LENGTH_SHORT).show();
            }
        }
        aSwitch.setChecked(!isExist(name));
    }

}
