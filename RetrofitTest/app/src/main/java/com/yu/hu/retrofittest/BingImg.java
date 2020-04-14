package com.yu.hu.retrofittest;

import androidx.annotation.Keep;

import java.util.List;

/**
 * @author Hy
 * created on 2020/04/14 14:27
 **/
@Keep
@SuppressWarnings({"unused", "WeakerAccess"})
public class BingImg {

    //bing每日一图：https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1
    public static final String REQUEST_URL = "https://cn.bing.com/";

    private static final String BASE_IMAGE_URL = "https://www.bing.com/";

    /**
     * images : [{"startdate":"20200413","fullstartdate":"202004131600","enddate":"20200414","url":"/th?id=OHR.BWFlipper_ZH-CN1813139386_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp","urlbase":"/th?id=OHR.BWFlipper_ZH-CN1813139386","copyright":"伊斯塔帕海岸的热带斑海豚，墨西哥 (© Christian Vizl/Tandem Stills + Motion)","copyrightlink":"https://www.bing.com/search?q=%E7%83%AD%E5%B8%A6%E6%96%91%E6%B5%B7%E8%B1%9A&form=hpcapt&mkt=zh-cn","title":"","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20200413_BWFlipper%22&FORM=HPQUIZ","wp":true,"hsh":"b8381f6da75ae67b3581c0ca162718ac","drk":1,"top":1,"bot":1,"hs":[]}]
     * tooltips : {"loading":"正在加载...","previous":"上一个图像","next":"下一个图像","walle":"此图片不能下载用作壁纸。","walls":"下载今日美图。仅限用作桌面壁纸。"}
     */

    public TooltipsBean tooltips;
    public List<ImagesBean> images;

    public static class TooltipsBean {
        /**
         * loading : 正在加载...
         * previous : 上一个图像
         * next : 下一个图像
         * walle : 此图片不能下载用作壁纸。
         * walls : 下载今日美图。仅限用作桌面壁纸。
         */

        public String loading;
        public String previous;
        public String next;
        public String walle;
        public String walls;
    }

    public static class ImagesBean {
        /**
         * startdate : 20200413
         * fullstartdate : 202004131600
         * enddate : 20200414
         * url : /th?id=OHR.BWFlipper_ZH-CN1813139386_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
         * urlbase : /th?id=OHR.BWFlipper_ZH-CN1813139386
         * copyright : 伊斯塔帕海岸的热带斑海豚，墨西哥 (© Christian Vizl/Tandem Stills + Motion)
         * copyrightlink : https://www.bing.com/search?q=%E7%83%AD%E5%B8%A6%E6%96%91%E6%B5%B7%E8%B1%9A&form=hpcapt&mkt=zh-cn
         * title :
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20200413_BWFlipper%22&FORM=HPQUIZ
         * wp : true
         * hsh : b8381f6da75ae67b3581c0ca162718ac
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        public String startdate;
        public String fullstartdate;
        public String enddate;
        public String url;
        public String urlbase;
        public String copyright;
        public String copyrightlink;
        public String title;
        public String quiz;
        public boolean wp;
        public String hsh;
        public int drk;
        public int top;
        public int bot;
        public List<?> hs;
    }

    /**
     * 获取每日一图的url
     *
     * @return url
     */
    public String getImgUrl() {
        if (images != null && images.size() >= 1) {
            return BASE_IMAGE_URL + images.get(0).url;
        }
        return null;
    }
}
