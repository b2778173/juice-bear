package com.juice.system.domain.vo;

import com.juice.common.utils.JbStringUtils;

/**
 * @author cheng
 * @since 2023/11/4 4:09 PM
 **/
public class MetaVo {
    /**
     * 設定該路由在側邊欄和麵包屑中顯示的名字
     */
    private String title;

    /**
     * 設定該路由的圖標，對應路徑src/assets/icons/svg
     */
    private String icon;

    /**
     * 設定為true，則不會被 <keep-alive>快取
     */
    private boolean noCache;

    /**
     * 內部連結位址（http(s)://開頭）
     */
    private String link;

    public MetaVo() {
    }

    public MetaVo(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public MetaVo(String title, String icon, boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }

    public MetaVo(String title, String icon, String link) {
        this.title = title;
        this.icon = icon;
        this.link = link;
    }

    public MetaVo(String title, String icon, boolean noCache, String link) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
        if (JbStringUtils.isHttp(link)) {
            this.link = link;
        }
    }

}