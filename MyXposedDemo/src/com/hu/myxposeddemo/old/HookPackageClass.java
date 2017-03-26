package com.hu.myxposeddemo.old;


import android.util.Log;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookPackageClass extends XC_MethodHook {
	private static HookPackageClass hookPackageClass;

	public HookPackageClass() {
	}

	public static void hookAll(ClassLoader classLoader, String packageName) {
		Class<?> c = XposedHelpers.findClass("java.lang.StringBuilder", classLoader);
		XposedHelpers.findAndHookMethod(c, "toString", new Object[] { getInstance()});
	}

	public static HookPackageClass getInstance() {
		if (hookPackageClass == null) {
			hookPackageClass = new HookPackageClass();
		}
		return hookPackageClass;
	}

	@Override
	protected void afterHookedMethod(MethodHookParam param) throws Throwable {
		super.afterHookedMethod(param);
		String result=(String)param.getResult();
		Log.i(XposedMainLodaPackage.HOOK_TAG+"_StringBuild","toString_"+result);
	}
	
}
