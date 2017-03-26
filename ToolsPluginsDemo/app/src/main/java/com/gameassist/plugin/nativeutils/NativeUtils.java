package com.gameassist.plugin.nativeutils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.R.bool;
import android.content.Context;
import android.util.Log;

public class NativeUtils {
	

	public static native void nativeDoCheat(int flag, int arg1, int arg2);


}
