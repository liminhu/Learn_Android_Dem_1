package com.hu.myxposeddemo;

import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class DouYuXposedThreeDemoModule implements IXposedHookLoadPackage {
	private static final String PACKAGE_NAME = "air.tv.douyu.android";

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (lpparam.packageName.equals(PACKAGE_NAME)) {
			Log.v("hook_Loaded App:", lpparam.packageName);
			// t a(Context arg3, String arg4, int arg5, long arg6)
			XposedHelpers.findAndHookMethod(
					"com.douyu.lib.xdanmuku.x.JniDanmu", lpparam.classLoader,
					"a", Context.class, String.class, int.class, long.class,
					new XC_MethodHook() {

						@Override
						protected void afterHookedMethod(MethodHookParam param)
								throws Throwable {
							int data = (Integer) param.getResult();
							Log.v("hook_afterHookedMethod_result", "" + data);
						}

						@Override
						protected void beforeHookedMethod(MethodHookParam param)
								throws Throwable {
							// this.e("[start] ip:" + arg4 + ",port:" + arg5 +
							// ",timestamp:" + arg6);
							Log.i("hook_JniDanmu_a_2_ip", "" + param.args[1]);
							Log.i("hook_JniDanmu_a_3_port", "" + param.args[2]);
							Log.i("hook_JniDanmu_a_4_timestamp", ""
									+ param.args[3]);
						}

					});

		}
	}

}
