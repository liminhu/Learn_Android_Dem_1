package com.local.hook.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;



public class NewHookGPS implements IXposedHookLoadPackage{
    private final String TAG = "my_hook_Xposed";
    private LoadPackageParam mLpp;

    public void log(String s){
        Log.d(TAG, s);
        XposedBridge.log(s);
    }

    //不带参数的方法拦截
    private void hook_method(Class<?> clazz, String methodName, Object... parameterTypesAndCallback)
    {
        try {
            XposedHelpers.findAndHookMethod(clazz, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    //不带参数的方法拦截
    private void hook_method(String className, ClassLoader classLoader, String methodName,
            Object... parameterTypesAndCallback)
    {
        try {
            XposedHelpers.findAndHookMethod(className, classLoader, methodName, parameterTypesAndCallback);
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    //带参数的方法拦截
    private void hook_methods(String className, String methodName, XC_MethodHook xmh)
    {
        try {
            Class<?> clazz = Class.forName(className);

            for (Method method : clazz.getDeclaredMethods())
                if (method.getName().equals(methodName)
                        && !Modifier.isAbstract(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())) {
                    XposedBridge.hookMethod(method, xmh);
                }
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }


    @Override
    public void handleLoadPackage(LoadPackageParam lpp) throws Throwable {
        mLpp = lpp;

        String packageName = mLpp.packageName;
        String targetPackage="com.tencent.mm";
        if (packageName.isEmpty() || !packageName.equals(targetPackage)) {
        	//Log.i("hook_package", packageName);
            return;
        }

        hook_method("android.net.wifi.WifiManager", mLpp.classLoader, "getScanResults",
                new XC_MethodHook(){
            /**
             * Android提供了基于网络的定位服务和基于卫星的定位服务两种
             * android.net.wifi.WifiManager的getScanResults方法
             * Return the results of the latest access point scan.
             * @return the list of access points found in the most recent scan.
             */
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                // TODO Auto-generated method stub
                //super.afterHookedMethod(param);
                param.setResult(null);//return empty ap list, force apps using gps information
            }
        });

        hook_method("android.telephony.TelephonyManager", mLpp.classLoader, "getCellLocation",
                new XC_MethodHook(){
            /**
             * android.telephony.TelephonyManager的getCellLocation方法
             * Returns the current location of the device.
             * Return null if current location is not available.
             */
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                // TODO Auto-generated method stub
                //super.afterHookedMethod(param);
                param.setResult(null);//return empty cell id list
            }
        });

        hook_method("android.telephony.TelephonyManager", mLpp.classLoader, "getNeighboringCellInfo",
                new XC_MethodHook(){
            /**
             * android.telephony.TelephonyManager类的getNeighboringCellInfo方法
             * Returns the neighboring cell information of the device.
             */
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                // TODO Auto-generated method stub
                //super.afterHookedMethod(param);
                param.setResult(null);//// return empty neighboring cell info list
            }
        });

        hook_methods("android.location.LocationManager", "requestLocationUpdates",
                new XC_MethodHook() {
            /**
             * android.location.LocationManager类的requestLocationUpdates方法
             * 其参数有4个：
             * String provider, long minTime, float minDistance,LocationListener listener
             * Register for location updates using the named provider, and a pending intent
             */
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                if (param.args.length == 4 && (param.args[0] instanceof String)) {
                    //位置监听器,当位置改变时会触发onLocationChanged方法
                    LocationListener ll = (LocationListener)param.args[3];

                    Class<?> clazz = LocationListener.class;
                    Method m = null;
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.getName().equals("onLocationChanged")) {
                            m = method;
                            break;
                        }
                    }

                    try {
                        if (m != null) {
                        //  mSettings.reload();

                            Object[] args = new Object[1];
                            Location l = new Location(LocationManager.GPS_PROVIDER);

//                          double la = Double.parseDouble(mSettings.getString("latitude", "-10001"));
//                          double lo = Double.parseDouble(mSettings.getString("longitude","-10001"));

                            //39.9057406377,116.5144139525
                            //39.9058229387,116.5143013001
                        //    39.9057600000,116.5145200000
                            //39.9120225963,116.5209251246
                            //39.9054090000,116.5142030000
                            
                            //39.9064763561,116.51260649475
                            double la=39.9054090000;  //39.9064763561
                            double lo=116.50960649475;
                            l.setLatitude(la); 
                            l.setLongitude(lo);

                            args[0] = l;

                            //invoke onLocationChanged directly to pass location infomation
                            m.invoke(ll, args);

                            XposedBridge.log("fake location: " + la + ", " + lo);
                        }
                    } catch (Exception e) {
                        XposedBridge.log(e);
                    }
                }
            }
        });


        hook_methods("android.location.LocationManager", "getGpsStatus",
                new XC_MethodHook(){
            /**
             * android.location.LocationManager类的getGpsStatus方法
             * 其参数只有1个：GpsStatus status
             * Retrieves information about the current status of the GPS engine.
             * This should only be called from the {@link GpsStatus.Listener#onGpsStatusChanged}
             * callback to ensure that the data is copied atomically.
             * 
             */
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                GpsStatus gss = (GpsStatus)param.getResult();
                if (gss == null)
                    return;

                Class<?> clazz = GpsStatus.class;
                Method m = null;
                for (Method method : clazz.getDeclaredMethods()) {
                    if (method.getName().equals("setStatus")) {
                        if (method.getParameterTypes().length > 1) {
                            m = method;
                            break;
                        }
                    }
                }

                //access the private setStatus function of GpsStatus
                m.setAccessible(true);

                //make the apps belive GPS works fine now
                int svCount = 5;
                int[] prns = {1, 2, 3, 4, 5};
                float[] snrs = {0, 0, 0, 0, 0};
                float[] elevations = {0, 0, 0, 0, 0};
                float[] azimuths = {0, 0, 0, 0, 0};
                int ephemerisMask = 0x1f;
                int almanacMask = 0x1f;

                //5 satellites are fixed
                int usedInFixMask = 0x1f;

                try {
                    if (m != null) {
                        m.invoke(gss,svCount, prns, snrs, elevations, azimuths, ephemerisMask, almanacMask, usedInFixMask);
                        param.setResult(gss);
                    }
                } catch (Exception e) {
                    XposedBridge.log(e);
                }
            }
        });
    }
}