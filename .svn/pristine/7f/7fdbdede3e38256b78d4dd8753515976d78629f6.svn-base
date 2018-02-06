package com.risfond.rnss.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Abbott on 2017/6/26.
 */

public class NumberUtil {

    /**
     * 回款金额保留两位小数，直接删除多余的小数
     *
     * @param money
     * @return
     */
    public static String payment(BigDecimal money) {
        BigDecimal decimal = money.setScale(2, BigDecimal.ROUND_DOWN);
        return decimal.toString();
    }

    /**
     * 整数，三位加一个逗号
     *
     * @param data
     * @return
     */
    public static String formatString(BigDecimal data) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(data);
    }

}
