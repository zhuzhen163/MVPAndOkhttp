package com.common.zhuz.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.common.zhuz.application.ZApplication;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	private static Handler mainHandler;


	public static void initThreadUtil(Context context){
		mainHandler = new Handler(context.getMainLooper());
	}
    /**
     * newFixedThreadPool就是一个固定大小的ThreadPool
     * @param nThreads
     * @return
     */
    public static ExecutorService newFixedThreadPool(int nThreads) {  
        return new ThreadPoolExecutor(nThreads, nThreads,  
                                      0L, TimeUnit.MILLISECONDS,  
                                      new LinkedBlockingQueue<Runnable>());  
    }  
    /**
     * newCachedThreadPool比较适合没有固定大小并且比较快速就能完成的小任务，没必要维持一个Pool，
     * 这比直接new Thread来处理的好处是能在60秒内重用已创建的线程。
     * @return
     */
    public static ExecutorService newCachedThreadPool() {  
        return new ThreadPoolExecutor(2,6,  
                                      60L, TimeUnit.SECONDS,  
                                      new LinkedBlockingQueue<Runnable>());  
    }  
	/** 获取主线程的handler */
	public static Handler getHandler() {
		return ZApplication.getMainThreadHandler();
	}

	/** 延时在主线程执行runnable */
	public static boolean postDelayed(Runnable runnable, long delayMillis) {
		return getHandler().postDelayed(runnable, delayMillis);
	}

	/** 在主线程执行runnable */
	public static boolean post(Runnable runnable) {
		return getHandler().post(runnable);
	}

	/** 从主线程looper里面移除runnable */
	public static void removeCallbacks(Runnable runnable) {
		getHandler().removeCallbacks(runnable);
	}

	public static void execute(Runnable runnable){

		executorService.execute(runnable);
	}

	public static void executeOnMainThread(Runnable runnable){

		mainHandler.post(runnable);
	}
	public static boolean isMainThread(){
		Looper myLooper = Looper.myLooper();
		Looper mainLooper = Looper.getMainLooper();
		return myLooper == mainLooper;
	}

	public static ExecutorService getExecutorService() {
		return executorService;
	}
}
