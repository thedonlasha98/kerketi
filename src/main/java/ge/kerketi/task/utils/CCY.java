package ge.kerketi.task.utils;

import org.springframework.util.StringUtils;

public enum CCY {
    GEL, USD, EUR;

    public static boolean contains(String ccy){
        if (StringUtils.isEmpty(CCY.valueOf(ccy))){
            return false;
        }
        return true;
    }
}
