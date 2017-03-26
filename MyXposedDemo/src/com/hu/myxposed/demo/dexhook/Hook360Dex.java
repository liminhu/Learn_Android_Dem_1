package com.hu.myxposed.demo.dexhook;

import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Hook360Dex implements IXposedHookLoadPackage {
	private String TAG = "Hook_360";

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

		if (lpparam.packageName.equals("com.mdcn.mdonline")) {
			Log.d(TAG, "开始hook....");
			// hook加固后的包，首先hook getNewAppInstance这个方法来获取context对象
			XposedHelpers.findAndHookMethod("com.qihoo.util.StubApp579459766",
					lpparam.classLoader, "getNewAppInstance", Context.class,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param)
								throws Throwable {
							super.afterHookedMethod(param);
							// 获取到360的Context对象，通过这个对象来获取classloader
							Context context = (Context) param.args[0];
							// 获取360的classloader，之后hook加固后的就使用这个classloader
							ClassLoader classLoader = context.getClassLoader();
							// 下面就是强classloader修改成360的classloader就可以成功的hook了
							XposedHelpers.findAndHookMethod("xxx.xxx.xxx.xxx",
									classLoader, "xxx", String.class,
									String.class, new XC_MethodHook() {
										@Override
										protected void beforeHookedMethod(
												MethodHookParam param)
												throws Throwable {
											super.beforeHookedMethod(param);
											Log.i(TAG, "密钥： "
													+ (String) param.args[0]);
											Log.i(TAG, "内容： "
													+ (String) param.args[1]);
											param.setResult((String) param.args[1]);
										}
									});
						}
					});
		}
	}
}
