package com.common.zhuz.http;

import com.common.zhuz.base.BaseHttpResult;
import com.common.zhuz.entity.BookBean;
import com.common.zhuz.entity.GankIoDataBean;
import com.common.zhuz.entity.HotMovieBean;
import com.common.zhuz.entity.RaidersDataEntity;
import com.common.zhuz.entity.VersionEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuzhen on 2017/11/2.
 * 接口统一处理
 */

public interface HttpService {

    /**
     * 测试速贷之家升级 get请求
     * 测试框架接口是否走通
     * @param versionId
     * @param appType
     * @param versionName
     * @param platType
     * @return
     */
    @GET("?r=api4/version/version")
    Observable<BaseHttpResult<VersionEntity>> checkVersition(@Query("versionId") String versionId, @Query("appType") String appType, @Query("versionName") String versionName, @Query("platType") String platType);
    @FormUrlEncoded()
    @POST("?r=api4/product/guide")
    Observable<BaseHttpResult<RaidersDataEntity>> postDemo(@Field("page") int page, @Field("num") int num);

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    Observable<GankIoDataBean> getCustom(@Path("type") String id, @Path("page") int page, @Path("pre_page") int pre_page);

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

    /**
     * 获取豆瓣电影top250
     *
     * @param start 从多少开始，如从"0"开始
     * @param count 一次请求的数目，如"10"条，最多100
     */
    @GET("v2/movie/top250")
    Observable<HotMovieBean> getMovieTop250(@Query("start") int start, @Query("count") int count);
}
