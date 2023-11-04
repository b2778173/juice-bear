package com.juice.common.utils;

import com.juice.common.constant.Constants;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cheng
 * @since 2023/11/4 4:10 PM
 **/
@UtilityClass
public class JbStringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 是否為http(s):// 開頭
     *
     * @param link 超連結
     * @return 結果
     */
    public static boolean isHttp(String link) {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }
}
