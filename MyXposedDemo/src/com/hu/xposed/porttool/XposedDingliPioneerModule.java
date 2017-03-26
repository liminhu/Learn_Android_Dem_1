package com.hu.xposed.porttool;

import com.hu.myxposeddemo.old.StringUtil;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedDingliPioneerModule implements IXposedHookLoadPackage{
	private  static final String PACKAGE_NAME="com.dingli.pioneer";
	private  static int num=0;
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		//Log.e("hook_Loaded App:-----"+num, lpparam.packageName);
		num++;
		
		if(lpparam.packageName.equals(PACKAGE_NAME)){
			Log.v("hook_Loaded App:"+num, lpparam.packageName);
			
			XposedHelpers.findAndHookMethod("com.duowei.appmonitor.LicenseCheckerNew", lpparam.classLoader, "license_check", String.class,
					String.class,
					
					new XC_MethodHook() {
				
				
				    @Override
				    	protected void beforeHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    	    String p0=(String)param.args[0];
				    	    String p1=(String)param.args[1];
				    		Log.i( "hook_my_p0: ",""+p0);
				    		Log.i( "hook_my_p1: ",""+p1);
				    	}
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    		Integer result=(Integer)param.getResult();
				    		Log.i( "hook_my_LicenseCheckerNew_result",""+result);
				    		//result=0;
				    		//param.setResult(0);
				    		//Log.i("hook_my_MainActivity_result_change",""+result);
				    	}
				});
			
			
			XposedHelpers.findAndHookMethod("com.duowei.appmonitor.LicenseCheckerNew", lpparam.classLoader, "genKey", String.class,
				
					new XC_MethodHook() {
				
				
				    @Override
				    	protected void beforeHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    	    String p0=(String)param.args[0];
				         	Log.i( "hook_my_genKey ",""+p0);
				    	}
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    		byte[] result=(byte[])param.getResult();
				    		String str=StringUtil.byteArrayToHexString(result);
				    		Log.i( "hook_my_genKey_result",""+str);
				    	}
				});
			
			
			
			
			XposedHelpers.findAndHookMethod("com.duowei.appmonitor.LicenseCheckerNew", lpparam.classLoader, "downloadFile", String.class,
					String.class,
					new XC_MethodHook() {
				
				
				    @Override
				    	protected void beforeHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    	    String p0=(String)param.args[0];
				       	    String p1=(String)param.args[1];
				         	Log.i( "hook_my_downloadFile_p0",""+p0);
				        	Log.i( "hook_my_downloadFile_p1",""+p1);
				    	}
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    		Boolean result=(Boolean)param.getResult();
				    		Log.i( "hook_my_downloadFile_result",""+result);
				    		param.setResult(true);
				    	}
				});
		}
		
	}
		
    
}
