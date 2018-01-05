package com.common.zhuz.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.common.zhuz.activity.MainActivity;
import com.common.zhuz.base.BasePresenter;
import com.common.zhuz.entity.RaidersDataEntity;
import com.common.zhuz.model.MainModel;
import com.common.zhuz.service.DownloadService;
import com.common.zhuz.tools.SpUtils;

import java.io.File;

/**
 * Created by zhuzhen on 2017/11/2.
 */

public class MainPresenter extends BasePresenter<MainModel, MainActivity>{

    private ServiceConnection conn;

    public void postPresener(int page, int num) {
        if (getView() != null){
            getView().showLoading();
        }
        getModel().postRequestDemo(page, num, new MainModel.PostInterface() {
            @Override
            public void sucesspost(RaidersDataEntity entity) {
                if (getView() != null) {
                    getView().postceshi(entity);
                    getView().hideLoading();
                }
            }

            @Override
            public void failpost(String msg) {
                if (getView() != null) {
                    getView().failPostceshi(msg);
                    getView().hideLoading();
                }
            }
        });
    }

    public void checkUpdate(String local){
        //假设获取得到最新版本
        String version = "2.0";
        String ignore = SpUtils.getInstance().getString("ignore");
        if (!ignore.equals(version) && !ignore.equals(local)) {
            getView().showUpdate(version);
        }
    }

    public void downApk(Context context) {
        final String url = "https://dianfenqi.cn/data/ffmpeg/upload/images/android/20171206/dianfenqi.apk";
        if (conn == null)
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
                    DownloadService myService = binder.getService();
                    myService.downApk(url, new DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @Override
                        public void onProgress(int progress) {
                            getView().showProgress(progress);
                        }

                        @Override
                        public void onComplete(File file) {
                            getView().showComplete(file);
                        }

                        @Override
                        public void onFail(String msg) {
                            getView().showFail(msg);
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    //意味中断，较小发生，酌情处理
                }
            };
        Intent intent = new Intent(context,DownloadService.class);
        context.bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    public void setIgnore(String version) {
        SpUtils.getInstance().putString("ignore",version);
    }

    @Override
    public MainModel loadModel() {
        return new MainModel();
    }
}
