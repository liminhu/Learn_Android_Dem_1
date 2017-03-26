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
			Log.d(TAG, "��ʼhook....");
			// hook�ӹ̺�İ�������hook getNewAppInstance�����������ȡcontext����
			XposedHelpers.findAndHookMethod("com.qihoo.util.StubApp579459766",
					lpparam.classLoader, "getNewAppInstance", Context.class,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param)
								throws Throwable {
							super.afterHookedMethod(param);
							// ��ȡ��360��Context����ͨ�������������ȡclassloader
							Context context = (Context) param.args[0];
							// ��ȡ360��classloader��֮��hook�ӹ̺�ľ�ʹ�����classloader
							ClassLoader classLoader = context.getClassLoader();
							// �������ǿclassloader�޸ĳ�360��classloader�Ϳ��Գɹ���hook��
							XposedHelpers.findAndHookMethod("xxx.xxx.xxx.xxx",
									classLoader, "xxx", String.class,
									String.class, new XC_MethodHook() {
										@Override
										protected void beforeHookedMethod(
												MethodHookParam param)
												throws Throwable {
											super.beforeHookedMethod(param);
											Log.i(TAG, "��Կ�� "
													+ (String) param.args[0]);
											Log.i(TAG, "���ݣ� "
													+ (String) param.args[1]);
											param.setResult((String) param.args[1]);
										}
									});
						}
					});
		}
	}
}
