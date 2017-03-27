package com.lmhu.firstcodebooksource.chapter10.service.download;

/**
 * Created by hulimin on 2017/3/27.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
