package com.hu.xposed.porttool;

import android.content.Context;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedPorttoolModule implements IXposedHookLoadPackage{
	private  static final String PACKAGE_NAME="com.dingli.porttool";
         //"com.dingli.porttool";	
	
	private  static int num=0;
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		//Log.e("hook_Loaded App:-----"+num, lpparam.packageName);
		num++;
		
		if(lpparam.packageName.equals(PACKAGE_NAME)){
			Log.v("hook_Loaded App:"+num, lpparam.packageName);
			
			XposedHelpers.findAndHookMethod("com.dingli.porttool.MainActivity", lpparam.classLoader, "b", Context.class,
					new XC_MethodHook() {
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    		Integer result=(Integer)param.getResult();
				    		Log.i( "hook_my_MainActivity_result",""+result);
				    		//result=0;
				    		param.setResult(0);
				    		Log.i("hook_my_MainActivity_result_change",""+result);
				    	}
				});
			
			
			XposedHelpers.findAndHookMethod("com.dingli.porttool.MainActivity", lpparam.classLoader, "b", String.class,
					new XC_MethodHook() {
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    	    Boolean result=(Boolean)param.getResult();
				    		Log.i( "hook_my_result",""+result);
				            param.setResult(true);
				    	}
				});
			
			
			
			
			XposedHelpers.findAndHookMethod("java.lang.StringBuilder", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_string_build", resultString);
				    }
				});
			
			XposedHelpers.findAndHookMethod("org.json.JSONObject", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_JSONObject", resultString);
				    }
				});
			
			XposedHelpers.findAndHookMethod("java.util.Arrays", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_Arrays", resultString);
				    }
				});
			
			XposedHelpers.findAndHookMethod("android.net.Uri", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_Uri", resultString);
				    }
				});
			
			XposedHelpers.findAndHookMethod("org.apache.http.util.EntityUtils", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_EntityUtils", resultString);
				    }
				});

			
			XposedHelpers.findAndHookMethod("java.util.ArrayList", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_ArrayList", resultString);
				    }
				});
			
			XposedHelpers.findAndHookMethod("java.util.ArrayList", lpparam.classLoader, "toString",
					new XC_MethodHook() {
				    @Override
				    protected void afterHookedMethod(MethodHookParam param)
				    		throws Throwable {
				    	super.afterHookedMethod(param);
				    	String resultString=(String)param.getResult();
				    	Log.v("hook_ArrayList", resultString);
				    }
				});
			
			
		/*	
			XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "init", int.class,Key.class,AlgorithmParameterSpec.class,
					new XC_MethodHook() {
				    
				
				@Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	byte[] key = ((SecretKey)param.args[1]).getEncoded() ;
						byte[] iv =  ((IvParameterSpec)param.args[2]).getIV();
						Log.i( "hook_Cipher_init_key", Arrays.toString(key) );
						Log.i( "hook_key_byte", StringUtil.byteArrayToHexString(key) );
						Log.i( "hook_Cipher_init_iv", Arrays.toString(iv) );
						Log.i( "hook_iv_byte", StringUtil.byteArrayToHexString(iv) );
				    }
				});
			
			
			
			
			XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal",byte[].class,
					new XC_MethodHook() {
				  
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	byte[] key = (byte[])param.args[0];
				    	Log.e("hook_doFinal_str", new String(key));
						Log.i( "hook_doFinal_beforeHookedMethod", Arrays.toString(key) );
						Log.e("hook_doFinal", StringUtil.byteArrayToHexString(key));
				    }
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				    		byte[] result=(byte[])param.getResult();
				    		Log.i( "hook_doFinal_afterHookedMethod", Arrays.toString(result) );
				    		Log.e("hook_doFinal_result", StringUtil.byteArrayToHexString(result));
				    	}
				});
			
			
			
			
			
			
			
			
			
			
			
			
			
	
			
			
			XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "init", int.class,Key.class,AlgorithmParameterSpec.class,
					new XC_MethodHook() {
				    
				
				@Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	byte[] key = ((SecretKey)param.args[1]).getEncoded() ;
						byte[] iv =  ((IvParameterSpec)param.args[2]).getIV();
						Log.i( "hook_Cipher_init_key", Arrays.toString(key) );
						Log.i( "hook_Cipher_init_iv", Arrays.toString(iv) );
				    }
				});
			
			XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "doFinal",byte[].class,
					new XC_MethodHook() {
				  
				@Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	byte[] key = (byte[])param.args[0];
						Log.i( "hook_Cipher_doFinal", Arrays.toString(key) );
				    }
				});
			
			
			XposedHelpers.findAndHookMethod("java.security.KeyPair", lpparam.classLoader, "decryptByPrivateKey", byte[].class, String.class,
					new XC_MethodHook() {
				  
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	byte[] args1 = (byte[])param.args[0];
				    	Log.v("hook_KeyPair", "prikey =" + (String)param.args[1]);
						Log.i( "hook_KeyPair", Arrays.toString(args1) );
						Log.v("hook_KeyPair", "data(base64) = "+Base64.encodeBase64String(args1));
						byte[] result = (byte[])param.getResult();
						Log.v("hook_KeyPair", "result=" + new String(result, "UTF-8"));
				    }
				});
	*/
			
		}
	}
	
	
	
    
}
