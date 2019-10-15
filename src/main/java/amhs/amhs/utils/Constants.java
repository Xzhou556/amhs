package amhs.amhs.utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public interface Constants {


    /**
     * 年月日时分秒 默认格式
     */
   SimpleDateFormat COMMON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 时间 默认格式
     */
  SimpleDateFormat COMMON_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    /**
     * 年月日 默认格式
     */
     SimpleDateFormat COMMON_DATE_FORMAT_NYR = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 年月 默认格式
     */
     SimpleDateFormat COMMON_DATE_FORMAT_NY = new SimpleDateFormat("yyyy-MM");

    /**
     * 月日 默认格式
     */
 SimpleDateFormat COMMON_DATE_FORMAT_YR = new SimpleDateFormat("MM-dd");


    /**
     * 月 默认格式
     */
  SimpleDateFormat COMMON_DATE_FORMAT_Y = new SimpleDateFormat("MM");

    /**
     * 星期 默认格式
     */
  String COMMON_TIME_FORMAT_XQ ="星期";
    /**
     * 星期 默认格式
     */
  String COMMON_TIME_FORMAT_Z ="周";

    /**
     * date-年月时分秒
     */
 List<String> EXCEL_FORMAT_INDEX_DATE_NYRSFM_STRING = Arrays.asList(
            "yyyy/m/d\\ h:mm;@","m/d/yy h:mm","yyyy/m/d\\ h:mm\\ AM/PM",
            "[$-409]yyyy/m/d\\ h:mm\\ AM/PM;@","yyyy/mm/dd\\ hh:mm:dd","yyyy/mm/dd\\ hh:mm",
            "yyyy/m/d\\ h:m","yyyy/m/d\\ h:m:s",
            "yyyy/m/d\\ h:mm","m/d/yy h:mm;@","yyyy/m/d\\ h:mm\\ AM/PM;@"

    );
    /**
     * date-年月日(中文）
     */
    List<String> EXCEL_FORMAT_INDEX_DATE_CNYR_STRING=Arrays.asList(
                    "reserved-0x1f"
            );
    /**
     * date-年月日(中文）
     */
  List<String> EXCEL_FORMAT_INDEX_DATE_NYR_STRING=Arrays.asList(
            "m/d/yy","[$-F800]dddd\\,\\ mmmm\\ dd\\,\\ yyyy",
            "[DBNum1][$-804]yyyy\"年\"m\"月\"d\"日\";@","yyyy\"年\"m\"月\"d\"日\";@","yyyy/m/d;@",
            "yy/m/d;@","m/d/yy;@","[$-409]d/mmm/yy","[$-409]dd/mmm/yy;@","reserved-0x1F","reserved-0x1E",
            "mm/dd/yy;@","yyyy/mm/dd","d-mmm-yy","[$-409]d\\-mmm\\-yy;@","[$-409]d\\-mmm\\-yy",
            "[$-409]dd\\-mmm\\-yy;@","[$-409]dd\\-mmm\\-yy","[DBNum1][$-804]yyyy\"年\"m\"月\"d\"日\"","yy/m/d",
            "mm/dd/yy","dd\\-mmm\\-yy"
    );
    /**
     * date-年月
     */
 List<String> EXCEL_FORMAT_INDEX_DATE_NY_STRING =Arrays.asList(
            "[DBNum1][$-804]yyyy\"年\"m\"月\";@","[DBNum1][$-804]yyyy\"年\"m\"月\"",
            "yyyy\"年\"m\"月\";@","yyyy\"年\"m\"月\"","[$-409]mmm\\-yy;@","[$-409]mmm\\-yy",
            "[$-409]mmm/yy;@","[$-409]mmm/yy","[$-409]mmmm/yy;@","[$-409]mmmm/yy",
            "[$-409]mmmmm/yy;@", "[$-409]mmmmm/yy","mmm-yy","yyyy/mm","mmm/yyyy",
            "[$-409]mmmm\\-yy;@","[$-409]mmmmm\\-yy;@","mmmm\\-yy","mmmmm\\-yy"
    );
    /**
     * date-月日
     */
  List<String>EXCEL_FORMAT_INDEX_DATE_YR_STRING=Arrays.asList(
            "[DBNum1][$-804]m\"月\"d\"日\";@", "[DBNum1][$-804]m\"月\"d\"日\"",
            "m\"月\"d\"日\";@","m\"月\"d\"日\"","[$-409]d/mmm;@","[$-409]d/mmm",
            "m/d;@","m/d","d-mmm","d-mmm;@","mm/dd","mm/dd;@","[$-409]d\\-mmm;@","[$-409]d\\-mmm"
    );
    /**
     * date-星期x
     */
    List<String>EXCEL_FORMAT_INDEX_DATE_XQ_STRING=Arrays.asList(
            "[$-804]aaaa;@","[$-804]aaaa"
    );
    /**
     * date-周x
     */
 List<String>EXCEL_FORMAT_INDEX_DATE_Z_STRING=Arrays.asList(
            "[$-804]aaa;@","[$-804]aaa"
    );
    /**
     * date-月x
     */
   List<String>EXCEL_FORMAT_INDEX_DATE_Y_STRING=Arrays.asList(
            "[$-409]mmmmm;@","[$-409]mmmmm","mmmmm"
    );
    /**
     * time-时间
     */
       List<String>EXCEL_FORMAT_INDEX_TIME_STRING=Arrays.asList(
            "mm:ss.0","h:mm","h:mm\\ AM/PM","h:mm:ss","h:mm:ss\\ AM/PM",
            "reserved-0x20", "reserved-0x21","[DBNum1]h\"时\"mm\"分\"","[DBNum1]上午/下午h\"时\"mm\"分\"","mm:ss",
            "[h]:mm:ss","h:mm:ss;@","[$-409]h:mm:ss\\ AM/PM;@","h:mm;@","[$-409]h:mm\\ AM/PM;@",
            "h\"时\"mm\"分\";@","h\"时\"mm\"分\"\\ AM/PM;@","h\"时\"mm\"分\"ss\"秒\";@",
            "h\"时\"mm\"分\"ss\"秒\"_ AM/PM;@","上午/下午h\"时\"mm\"分\";@","上午/下午h\"时\"mm\"分\"ss\"秒\";@",
            "[DBNum1][$-804]h\"时\"mm\"分\";@","[DBNum1][$-804]上午/下午h\"时\"mm\"分\";@","h:mm AM/PM","h:mm:ss AM/PM",
            "[$-F400]h:mm:ss\\ AM/PM"
    );
    /**
     * date-当formatString为空的时候-年月
     */
  Short EXCEL_FORMAT_INDEX_DATA_EXACT_NY = 57;
    /**
     * date-当formatString为空的时候-月日
     */
    Short EXCEL_FORMAT_INDEX_DATA_EXACT_YR = 58;
    /**
     * TIME-当formatString为空的时候-时间
     */
     List<Short>  EXCEL_FORMAT_INDEX_TIME_EXACT = Arrays.asList(new Short[]{55,56});
    /**
     * excel表中时间或者日期格式的所有dateformat
     */
  List<Short> EXCEL_FORMAT_INDEX_TIME_DATE = Arrays.asList(new Short[]{
       31,58,56,14,17,22,20,21,181,182,183,184,185,186,187,188,189,190,191,195,198,199,200,201,202,204,205,206,
            207,208,209,211,18,32,33,55,57,192,193,194,15,16,195,196,197
    });
    /**
     * 格式化星期或者周显示
     */
 String[] WEEK_DAYS ={"日","一","二","三","四","五","六"};
    /**
     * 07版excel后缀名
     */
 String EXCEL_SUFFIX_07 = "xlsx";
    /**
     * 07版excel后缀名
     */
 String EXCEL_SUFFIX_03 = "xls";
}
