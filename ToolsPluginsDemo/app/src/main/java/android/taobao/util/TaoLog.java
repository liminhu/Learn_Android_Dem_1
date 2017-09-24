package android.taobao.util;

import android.util.Log;

public final class TaoLog {
    public static final String APICONNECT_TAG = "TaoSdk.ApiRequest";
    public static final String ENDCASE_TAG = "TaoSdk.EndUT";
    public static final String ETAOLOCAL_TAG = "EtaoLocal";
    public static final String ETAO_APIURL_TAG = "etao_apiurl";
    public static final String ETAO_TAG = "Etao";
    public static final String IMGPOOL_COMPRESSION_RATIO_TAG = "Image_Compression";
    public static final String IMGPOOL_TAG = "TaoSdk.ImgPool";
    public static final String MEM_TRACE = "mem_Trace";
    public static final String PANELMANAGER_TAG = "PanelManager";
    public static final String SIGN_TAG = "tag_sign";
    public static final String STARTUTCASE_TAG = "TaoSdk.StartUT";
    public static final String TAOBAO_TAG = "Taobao";
    private static boolean isPrintLog;

    static {
        TaoLog.isPrintLog = true;
    }

    public TaoLog() {
        super();
    }

    public static void Logd(String arg1, String arg2) {
        TaoLog.isPrintLog=true;
        if(arg1 != null && arg2 != null && (TaoLog.isPrintLog)) {
            Log.d(arg1, arg2);
        }
    }

    public static void Loge(String arg1, String arg2) {
        TaoLog.isPrintLog=true;
        if(arg1 != null && arg2 != null && (TaoLog.isPrintLog)) {
            Log.e(arg1, arg2);
        }
    }

    public static void Logi(String arg1, String arg2) {
        TaoLog.isPrintLog=true;
        if(arg1 != null && arg2 != null && (TaoLog.isPrintLog)) {
            Log.i(arg1, arg2);
        }
    }

    public static void Logv(String arg1, String arg2) {
        TaoLog.isPrintLog=true;
        if(arg1 != null && arg2 != null && (TaoLog.isPrintLog)) {
            Log.v(arg1, arg2);
        }
    }

    public static void Logw(String arg1, String arg2) {
        TaoLog.isPrintLog=true;
        if(arg1 != null && arg2 != null && (TaoLog.isPrintLog)) {
            Log.w(arg1, arg2);
        }
    }

    public static boolean getLogStatus() {
        TaoLog.isPrintLog=true;
        return TaoLog.isPrintLog;
    }

    public static void setLogSwitcher(boolean arg0) {
        TaoLog.isPrintLog = arg0;
        TaoLog.isPrintLog=true;
    }
}

