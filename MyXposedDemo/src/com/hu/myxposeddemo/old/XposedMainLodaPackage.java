package com.hu.myxposeddemo.old;

import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import com.hu.myxposeddemo.old.test.DexInstall;

import android.os.Build;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedMainLodaPackage implements IXposedHookLoadPackage {
	public static final String PACKAGE_NAME ="com.joym.xiongchumo2";
	public static String HOOK_TAG = "hook_tag";

	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		//Log.v(HOOK_TAG+"_packageName",lpparam.packageName);
		
		if(lpparam.packageName.equals(PACKAGE_NAME)){
			Log.e(HOOK_TAG, "begin packageName: " + lpparam.packageName);
		}
		
		if (lpparam.packageName.equals(PACKAGE_NAME)) {
			Log.e(HOOK_TAG, "Loaded App packageName: " + lpparam.packageName);
			//HookPackageClass.hookAll(lpparam.classLoader, PACKAGE_NAME);
	
					
			Class clazz1 = XposedHelpers.findClass(
					"com.secneo.mmb.DexInstall", lpparam.classLoader);

			Method m1 = XposedHelpers.findMethodExact(clazz1, "expandFieldArray",
					Object.class, String.class, Object[].class);

			m1.setAccessible(true);

			XposedBridge.hookMethod(m1, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param)
						throws Throwable {
					Log.i("hook_expandFieldArray","call -> " + (String) param.args[1]);
					DexInstall.expandFieldArray((Object)param.args[0], (String)param.args[1], (Object[])param.args[2]);
					super.beforeHookedMethod(param);
				}
		
			});

			  findAndHookConstructor("dalvik.system.BaseDexClassLoader", lpparam.classLoader, String.class, File.class, String.class, ClassLoader.class, new XC_MethodHook() {
		            @Override
		            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		                String outDir ="/sdcard";
		                String dexPath = (String) param.args[0];

		                //Ignore loading of files from /system, comment this out if you wish
		                if (dexPath.startsWith("/system/"))
		                    return;

		                Log.i("hook_dexClassLoader","Hooking dalvik.system.BaseDexClassLoader for " + PACKAGE_NAME);
		                String uniq = UUID.randomUUID().toString();
		                outDir = outDir + "/" + PACKAGE_NAME  + dexPath.replace("/", "_") + "-" + uniq;

		                Log.i("hook_dexClassLoader_1","Capturing " + dexPath);
		                Log.i("hook_dexClassLoader_2","Writing to " + outDir);

		                InputStream in = new FileInputStream(dexPath);
		                OutputStream out = new FileOutputStream(outDir);
		                byte[] buf = new byte[1024];
		                int len;
		                while ((len = in.read(buf)) > 0) {
		                    out.write(buf, 0, len);
		                }
		                in.close();
		                out.close();
		            }
		        });
			  
			  findAndHookMethod("dalvik.system.DexFile", lpparam.classLoader, "openDexFile", String.class, String.class, int.class, new XC_MethodHook() {
			        
				  @Override
		            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
		                String outDir ="/sdcard";
		                String dexPath = (String) param.args[0];

		                //Ignore loading of files from /system, comment this out if you wish
		                if (dexPath.startsWith("/system/"))
		                    return;

		                Log.i("hook1_dexClassLoader","Hooking dalvik.system.BaseDexClassLoader for " + PACKAGE_NAME);
		                String uniq = UUID.randomUUID().toString();
		                outDir = outDir + "/" + PACKAGE_NAME  + dexPath.replace("/", "_") + "-" + uniq;

		                Log.i("hook1_dexClassLoader_1","Capturing " + dexPath);
		                Log.i("hook1_dexClassLoader_2","Writing to " + outDir);

		                InputStream in = new FileInputStream(dexPath);
		                OutputStream out = new FileOutputStream(outDir);
		                byte[] buf = new byte[1024];
		                int len;
		                while ((len = in.read(buf)) > 0) {
		                    out.write(buf, 0, len);
		                }
		                in.close();
		                out.close();
		            }
		        });
			  
		}
	}
}
