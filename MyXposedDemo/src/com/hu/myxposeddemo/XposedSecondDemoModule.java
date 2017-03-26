package com.hu.myxposeddemo;

import java.security.Key;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.text.StringCharacterIterator;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.hu.myxposeddemo.old.StringUtil;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedSecondDemoModule implements IXposedHookLoadPackage{
	private  static final String PACKAGE_NAME="com.joym.xiongchumo2";	//"com.brianbaek.popstar"; //
	
    
    public static void test(){
    	Log.i("hook_print_stack","begin ...");
       	Exception e = new Exception("this is a log");
      	StackTraceElement[]  elements=e.getStackTrace();
      	StringBuilder sb=new StringBuilder();
      	for(int i=0; i<elements.length; i++){
      		sb.append(elements[i]);
      		sb.append("\n");
      	}
    	Log.e( "hook_Exception", sb.toString());
    }
	
	
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if(lpparam.packageName.equals(PACKAGE_NAME)){
			Log.v("hook_Loaded App:", lpparam.packageName);
			
			XposedHelpers.findAndHookMethod("javax.crypto.Cipher", lpparam.classLoader, "init", int.class,Key.class,AlgorithmParameterSpec.class,
					new XC_MethodHook() {
				    
				
				@Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					    test();
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
				    		test();
				    	}
				});
			
			
		
			XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "getInstance", 
					String.class,
					new XC_MethodHook() {
				  
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	String args1 = (String)param.args[0];
				    	Log.d("hook_MessageDigest", args1);
				    	//test();
				    }
				});
				
			XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "update", 
					byte[].class,
					new XC_MethodHook() {
				  
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				       	byte[] key = (byte[])param.args[0];
				    	Log.d("hook_MessageDigest_update", new String(key));
						Log.e("hook_MessageDigest_update", StringUtil.byteArrayToHexString(key));
				    }
				});	
			
			
			
			XposedHelpers.findAndHookMethod("java.security.MessageDigest", lpparam.classLoader, "digest", 
					new XC_MethodHook() {
				    
				    @Override
				    	protected void afterHookedMethod(MethodHookParam param)
				    			throws Throwable {
				          	byte[] key = (byte[])param.getResult();
				    	    Log.d("hook_MessageDigest_digest", new String(key));
						    Log.e("hook_MessageDigest_digest", StringUtil.byteArrayToHexString(key));
						   // test();
				    	}
				});	
			
			
			
			XposedHelpers.findAndHookMethod("com.chinaMobile.MobileAgent_", lpparam.classLoader, "access$200", 
					Context.class,String.class, JSONObject.class,
					new XC_MethodHook() {    
				
				@Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
						Log.i( "hook_HttpPostData_1", (String)param.args[1]);
						JSONObject json=(JSONObject)param.args[2];
						Log.i( "hook_HttpPostData_JSON", json.toString());
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
			
			
			
			XposedHelpers.findAndHookMethod("com.ccit.mmwlan.httpClient.HTTPConnectionToolForBilling", lpparam.classLoader, "doPost",String.class, byte[].class, 
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_URL", "URL=" + (String)param.args[0]);
				    	byte[] args1 = (byte[])param.args[1];
				    	Log.i( "hook_doPost_data", Arrays.toString(args1) );
						Log.e( "hook_doPost_data", StringUtil.byteArrayToHexString(args1));
				    }
				});
			
	
			
			
			XposedHelpers.findAndHookMethod("com.ccit.mmwlan.RSAEncryptWithPubKey", lpparam.classLoader, "RSAEncryptWithPubKey",String.class,
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_input", "args0=" + (String)param.args[0]);
				    }
				});
	
			
			
			
			XposedHelpers.findAndHookMethod("com.ccit.mmwlan.httpClient.HTTPConnectionToolForLogin", lpparam.classLoader, "doPostByHttpClient",String.class, byte[].class, 
					HttpHost.class,
					
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_URL", "URL=" + (String)param.args[0]);
				    	byte[] args1 = (byte[])param.args[1];
				    	Log.i( "doPost() request -> ", Arrays.toString(args1) );
						Log.e( "doPost() request -> ", StringUtil.byteArrayToHexString(args1));
				    }
				});
			
			
			
			XposedHelpers.findAndHookMethod("com.ccit.mmwlan.phone.HTTPConnectionTool", lpparam.classLoader, "doPost_0ld",String.class, byte[].class, 
					HttpHost.class,String.class,
					
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_URL", "URL=" + (String)param.args[0]);
				    	byte[] args1 = (byte[])param.args[1];
				    	Log.i( "doPost() request -> ", Arrays.toString(args1) );
						Log.e( "doPost() request -> ", StringUtil.byteArrayToHexString(args1));
				    }
				});
			
			
			
			XposedHelpers.findAndHookMethod("mm.purchasesdk.core.i.l", lpparam.classLoader, "IiiiIiiIiI",String.class,
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_mm", "data=" + (String)param.args[0]);
				
				    }
				});
			
			XposedHelpers.findAndHookMethod("mm.purchasesdk.core.a.d", lpparam.classLoader, "iiIIIIIiIi",String.class,
					new XC_MethodHook() {
				    @Override
				    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				    	Log.v("hook_mm", "data=" + (String)param.args[0]);
				        test();
				    }
				});
			
		}
	}
	
	
	
    
}
