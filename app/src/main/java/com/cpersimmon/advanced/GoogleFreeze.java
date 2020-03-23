package com.cpersimmon.advanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GoogleFreeze extends AppCompatActivity {
    String pkgName[]={"com.google.android.gms","com.android.vending","com.google.android.gsf",
            "com.google.android.googlequicksearchbox","com.google.android.onetimeinitializer","com.google.android.gms.location.history",
            "com.google.android.tts","com.google.android.syncadapters.calendar","com.google.android.partnersetup",
            "com.google.android.syncadapters.contacts"};
    int pkgState[]={0,0,0,0,0,0,0,0,0,0};
    //总共十个。


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_freeze);
    }
}
