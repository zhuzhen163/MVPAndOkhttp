package com.common.zhuz.http;

import com.common.zhuz.application.ZApplication;
import com.common.zhuz.tools.LogUtil;
import com.common.zhuz.tools.NetworkUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhuzhen on 2017/11/2.
 * retrofit+okhttp+rxjava+mvp  基本配置
 */

public class Http {
    private static Gson gson;
    private static OkHttpClient client;
    private static HttpService httpService;
    private static Retrofit retrofit;
    public static int per_page_more = 10;

    /**
     * @return retrofit的底层利用反射的方式, 获取所有的api接口的类
     */
    public static HttpService getHttpService(String url) {
        if (httpService == null) {
            httpService = getRetrofit(url).create(HttpService.class);
        }
        return httpService;
    }

    //----------------------------------------------------------------------
    private static HttpService httpDouban;
    public static HttpService getAPI_DOUBAN(String url) {
        if (httpDouban == null){
            httpDouban = getRetrofitAPI_DOUBAN(url).create(HttpService.class);
        }
        return httpDouban;
    }

    public static Retrofit getRetrofitAPI_DOUBAN(String url) {
            synchronized (Http.class) {
                initokMHttp();
                // 获取retrofit的实例
                retrofit = new Retrofit
                        .Builder()
                        .baseUrl(url)
                        .client(client)
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create(getGson()))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
                }
        return retrofit;
    }
    //----------------------------------------------------------------------
    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
//                        .addQueryParameter("phoneSystem", "")
//                        .addQueryParameter("phoneModel", "")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();
                        // Provide your custom header here
//                        .header("token", (String) SpUtils.get("token", ""))
                        //数据压缩  如果服务器支持Gzip
//                        .addHeader("Content-Encoding", "gzip")
//                        .addHeader("Connection", "keep-alive")
//                        .addHeader("Accept", "*/*")
//                        //里面做一下适配
//                        .addHeader("User-Agent", AppUtils.getUserAgent())
//                        .method(originalRequest.method(), originalRequest.body());
//                //get post请求添加的不同
//                if (originalRequest.method().equals("GET")){
//                    requestBuilder.addHeader("Content-Type", "application/json; charset=utf-8");
//                }
//                else {
//                    requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//                }

                requestBuilder.addHeader("Accept", "application/json;versions=1");
                if (NetworkUtil.isNetworkConnected(ZApplication.getAppContext())) {
                    int maxAge = 60;
                    requestBuilder.addHeader("Cache-Control", "public, max-age=" + maxAge);
                } else {
                    int maxStale = 60 * 60 * 24 * 28;
                    requestBuilder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
                }

                Request request = requestBuilder.build();
                //打印请求头
                LogUtil.i("header",request.headers().toString());
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * 设置缓存
     */
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkConnected(ZApplication.getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkConnected(ZApplication.getAppContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }

    public static Retrofit getRetrofit(String url) {
        if (retrofit == null) {
            synchronized (Http.class) {
                if (retrofit == null) {
                    initokMHttp();
                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(url)  //自己配置
                            .client(client)
                            .addConverterFactory(new NullOnEmptyConverterFactory())
                            .addConverterFactory(GsonConverterFactory.create(getGson()))
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * .retrofit2.0后：BaseUrl要以/结尾；@GET 等请求不要以/开头；@Url: 可以定义完整url，不要以 / 开头。
     * .addConverterFactory提供Gson支持，可以添加多种序列化Factory，但是GsonConverterFactory必须放在最后,否则会抛出异常。
     *
     * @return baseUrl 这里不规范只能传入这个Url(没办法)
     * 规范的是整个用一个基类的url就可以
     * Picasso picasso = new Picasso.Builder(HomeActivity.this).downloader(new OkHttpDownloader(okHttpClient)).build();
     */
    public static void initokMHttp() {
        //添加一个log拦截器,打印所有的log
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //可以设置请求过滤的水平,body,basic,headers
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置 请求的缓存的大小跟位置
//                    CookieJar cookieJar =
//        File cacheFile = new File(QZApplication.getmApplication,().getCacheDir(), "cache");
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); //30Mb 缓存的大小
//        final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        client = new OkHttpClient
                .Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
//              .cache(cache)  //添加缓存
                //30秒  读写100秒
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                //自动管理cookie
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        cookieStore.put("cookie", cookies);
//                        if (cookies != null && cookies.size() > 0) {
//                            LogUtil.i("newnew", cookies.get(0).toString());
//                            ConfigUtils.saveCookie(cookies.get(0).toString());
//                        }
//                    }
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies = cookieStore.get("cookie");
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                })
                .addInterceptor(addHeaderInterceptor()) // 请求头拦截添加
                .build();
    }

    private static Gson getGson() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private static class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    /**
     * ============================Https默认信任所有的证书================================
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)

                throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
