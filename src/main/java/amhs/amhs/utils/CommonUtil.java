package amhs.amhs.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * 高频方法工具类
 * @author tomsun28
 * @date 14:08 2018/3/12
 */
public class CommonUtil {


    /**
     * description 获取指定位数的随机数
     *
     * @param length 1
     * @return java.lang.String
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     *
     * @param date
     * @return
     */

    public static String dateToWeek(Date date){
        if (date==null){
            return  "";
        }
        //获得一个日历
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        //指示一个星期的某天
        int w = cal.get(Calendar.DAY_OF_WEEK)-1;
        if (w<0){
            w=0;
        }
        return "";
    }

}
