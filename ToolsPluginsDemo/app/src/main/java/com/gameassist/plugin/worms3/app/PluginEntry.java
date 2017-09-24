package com.gameassist.plugin.worms3.app;

import android.util.Log;
import android.view.View;

import com.gameassist.plugin.ClassLoaderCallback;
import com.gameassist.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class PluginEntry extends Plugin {

    private static final String LIB_HOOK_PLUGIN = "myworms3";
    private View pluginView;

    private static PluginEntry instance;

    public static PluginEntry getInstance() {
        return instance;
    }

    @Override
    public boolean OnPluginCreate() {
        Log.i("hook_OnPluginCreate","begin_111");
      /*  ClassLoader loader=getTargetApplication().getClassLoader();
        try{
            Class c= loader.loadClass("android.taobao.util.TaoLog");
            //修改属性
            Constructor c1 = c.getConstructor();
            Object obj= c1.newInstance();
            Field field=c.getDeclaredField("isPrintLog");
            field.setAccessible(true);
            field.setBoolean(obj, true);
            Log.i("hook_OnPluginCreate","begin_2222");
        }catch (Exception e){

        }*/


        getPluginManager().registerClassLoaderOverride(this, getClass().getClassLoader(), new ClassLoaderCallback() {

            @Override
            public boolean shouldOverrideClass(String s) {
                if(s.equals("android.taobao.util.TaoLog")){
                    Log.i("hook_OnPluginCreate","begin_2222---");
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldOverrideResource(String s) {
                return false;
            }
        });

    // System.loadLibrary(LIB_HOOK_PLUGIN);
        instance = this;
        return false;
    }

    public boolean pluginHasUI() {
        return false;
    }

    @Override
    public void OnPlguinDestroy() {
    }

    @Override
    public void OnPluginUIHide() {
    }

    @Override
    public View OnPluginUIShow() {
        return pluginView;
    }
}