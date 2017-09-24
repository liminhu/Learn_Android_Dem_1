package com.hu.xposed.hook.main;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMainLodaPackage  implements IXposedHookLoadPackage {	
	public static final  String PACKAGE_NAME="com.joym.xiongchumo2";
	public static String HOOK_TAG="hook_tag";
	
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		//Log.v(HOOK_TAG+"_packageName",lpparam.packageName);
		if (lpparam.packageName.indexOf(PACKAGE_NAME) != -1) {
			Log.v(HOOK_TAG, "Loaded App packageName: "+ lpparam.packageName);
			HookPackageClass.hookAll(lpparam.classLoader, PACKAGE_NAME);
		}
	}
}
