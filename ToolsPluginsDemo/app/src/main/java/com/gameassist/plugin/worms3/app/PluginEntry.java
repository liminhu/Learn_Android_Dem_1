package com.gameassist.plugin.worms3.app;

import android.util.Log;
import android.view.View;

import com.gameassist.plugin.Plugin;

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
        System.loadLibrary(LIB_HOOK_PLUGIN);
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