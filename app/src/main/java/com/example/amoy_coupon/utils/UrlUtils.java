package com.example.amoy_coupon.utils;

public class UrlUtils {
    public static String createHomePagerUrl(int id, int pager) {
        return "discovery/" + id + "/" + pager;
    }

    public static String getCoverPath(String pict_url) {
        return "https:" + pict_url;
    }
}
