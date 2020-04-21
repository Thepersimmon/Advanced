package com.cpersimmon.advanced.Utils;

public class MultiUserAppManager {
    final private int userId;
    private String allAppList;
    private String disableAppList;
    private String BLANK=" ";
    private ShellUtils shellUtils=new ShellUtils();
    private ShellUtils.CommandResult commandResult;
    private int disable=0;
    private int enable=1;
    private int notInstall=-1;
    public MultiUserAppManager(int userId){
        this.userId=userId;
        freshAppState();
    }
    public void freshAppState(){
        String getAllAppList="pm list package --user"+BLANK+userId;
        String getDisableAppList="pm list package -d --user"+BLANK+userId;
        commandResult=shellUtils.execCommand(getAllAppList,true,true);
        allAppList=commandResult.successMsg;
        commandResult=shellUtils.execCommand(getDisableAppList,true,true);
        disableAppList=commandResult.successMsg;
    }
    public int getMultiUserAppState(String packageName){
        if(allAppList.contains(packageName)){
            if(disableAppList.contains(packageName)){
                return disable;
            } else {
                return enable;
            }
        }else {
            return notInstall;
        }
    }
    public void setMultiUserAppState(String packageName,int state){
        String command;
        if(state==disable){
            command="pm disable"+BLANK+"--user"+BLANK+userId+BLANK+packageName;
            commandResult=shellUtils.execCommand(command,true,true);
        }else {
            command="pm enable"+BLANK+"--user"+BLANK+userId+BLANK+packageName;
            shellUtils.execCommand(command,true,true);
        }
    }
    public static boolean isUserExit(int sUserId){
        String command="pm list package --user"+" "+sUserId;
        ShellUtils sShellUtils =new ShellUtils();
        ShellUtils.CommandResult sCommandResult;
        sCommandResult=sShellUtils.execCommand(command,false,true);
        if((sCommandResult.errorMsg.length()==0)&&(sCommandResult.successMsg.length()==0)){
            return false;
        }else {
            return true;
        }
    }
}
