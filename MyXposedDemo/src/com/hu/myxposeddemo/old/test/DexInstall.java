package com.hu.myxposeddemo.old.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;

import android.util.Log;


public class DexInstall {
    public static void expandFieldArray(Object arg7, String arg8, Object[] arg9) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field v1 = DexInstall.findField(arg7, arg8);
        String nameString=v1.getName();
        Log.i("hook_DexInstall_length",nameString);
        Object v2 = v1.get(arg7);
        byte[] bytes=null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(v2);        
		    oos.flush();         
		    bytes = bos.toByteArray();  
		    oos.close();         
	        bos.close();
	        Log.i("hook_Object_v2", v2.toString()+"  -------\t:+bos.toByteArray()-"+bos.toByteArray().length);
		} catch (IOException e) {
			Log.e("hook_Object_v2", e.getMessage());
			e.printStackTrace();
		}         
        int length=( bytes==null ? 0 : bytes.length);
        Log.i("hook_Object_v2", v2.toString()+"  -------\t:+byte-"+length);
        Log.i("hook_Object_v2_length", ""+v2.toString().getBytes().length);
       /* Object v0 = Array.newInstance(v2.getClass().getComponentType(), v2.length + arg9.length);
        System.arraycopy(v2, 0, v0, 0, v2.length);
        System.arraycopy(arg9, 0, v0, v2.length, arg9.length);
        v1.set(arg7, v0);*/
    }
    
    
    
    

    /*private static void install(ClassLoader arg11, List arg12, File arg13) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException {
        IOException[] v1_2;
        Log.e("sdk", "v19 install");
        Object v2 = DexInstall.findField(arg11, "pathList").get(arg11);
        ArrayList v6 = new ArrayList();
        DexInstall.expandFieldArray(v2, "dexElements", V19.makeDexElements(v2, new ArrayList(((Collection)arg12)), arg13, v6));
        if(v6.size() > 0) {
            Iterator v4 = v6.iterator();
            while(v4.hasNext()) {
                v4.next().printStackTrace(System.out);
            }

            Field v7 = DexInstall.findField(arg11, "dexElementsSuppressedExceptions");
            Object v1 = v7.get(arg11);
            if(v1 == null) {
                Object[] v1_1 = v6.toArray(new IOException[v6.size()]);
            }
            else {
                IOException[] v0 = new IOException[v6.size() + v1.length];
                v6.toArray(((Object[])v0));
                System.arraycopy(v1, 0, v0, v6.size(), v1.length);
                v1_2 = v0;
            }

            v7.set(arg11, v1_2);
        }
    }*/
    
    
    private static Field findField(Object arg5, String arg6) throws NoSuchFieldException {
        Field v1;
        Class v0 = arg5.getClass();
        while(true) {
            if(v0 == null) {
            	 throw new NoSuchFieldException("Field " + arg6 + " not found in " + arg5.getClass());
            }

            try {
                v1 = v0.getDeclaredField(arg6);
                if(!v1.isAccessible()) {
                    v1.setAccessible(true);
                }

                return v1;
            }
            catch(NoSuchFieldException v2) {
                v0 = v0.getSuperclass();
                continue;
            }
        }
      //  return v1;       
    }
}
