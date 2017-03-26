package com.hu.myxposed.demo.dexhook;



import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by jcase on 9/7/15.
 */
public class DexHook implements IXposedHookLoadPackage {

    //set this to target a specific package only
    XSharedPreferences pref = new XSharedPreferences("com.hu.myxposeddemo", "dexhook_sp");
    String targetPackage = pref.getString("package_name", "com.dingli.pioneer");
    String packageName = null;

    String baseDir = pref.getString("dir_path", "/sdcard");

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        packageName = lpparam.packageName;

        if (!targetPackage.isEmpty() && !targetPackage.equals(lpparam.packageName)) {
        	Log.i("hook_package", packageName);
        	//Log.d("hook_targetPackage", targetPackage+"");
            return;
        }else{
        	Log.i("hook_package_=", packageName);
        	Log.i("hook_package_=", lpparam.packageName);
        }


        findAndHookConstructor("dalvik.system.BaseDexClassLoader", lpparam.classLoader, String.class, File.class, String.class, ClassLoader.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String outDir = baseDir;
                String dexPath = (String) param.args[0];

                //Ignore loading of files from /system, comment this out if you wish
                if (dexPath.startsWith("/system/"))
                    return;

                XposedBridge.log("Hooking dalvik.system.BaseDexClassLoader for " + packageName);
                String uniq = UUID.randomUUID().toString();
                outDir = outDir + "/" + packageName  + dexPath.replace("/", "_") + "-" + uniq;

                XposedBridge.log("Capturing " + dexPath);
                XposedBridge.log("Writing to " + outDir);

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

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

            }
        });

        //I forgot some silly packers load one class at a time using DexFile
        findAndHookMethod("dalvik.system.DexFile", lpparam.classLoader, "openDexFile", String.class, String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String outDir = baseDir;
                String dexPath = (String) param.args[0];

                //Ignore loading of files from /system, comment this out if you wish
                if (dexPath.startsWith("/system/"))
                    return;

                XposedBridge.log("Hooking dalvik.system.DexFile for " + packageName);
                String uniq = UUID.randomUUID().toString();
                outDir = outDir + "/" + packageName  + dexPath.replace("/", "_") + "-" + uniq;

                XposedBridge.log("Capturing " + dexPath);
                XposedBridge.log("Writing to " + outDir);

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
