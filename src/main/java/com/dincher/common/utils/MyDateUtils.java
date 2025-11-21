package com.dincher.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期获取工具类
 *
 * @author Liangjie Zhang
 * @since 2022-08-19 14:41:19
 */
public class MyDateUtils {

    /**
     * 获取当前日期前a天的日期集合
     *
     * @param a 天数
     * @return
     */
    public static List<String> getLatestDayList(int a) {
        List<String> latestDayList = new ArrayList<>();
        for (int i = 0; i > -a; i--) {
            String date = getDate(i);
            latestDayList.add(date);
        }
        return latestDayList;
    }

    /**
     * 获取当前日期前i天的日期
     *
     * @param i
     * @return
     */
    private static String getDate(int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //i=-1获取的是前一天  1=1获取的是后一天  1=0获取的是当天
        calendar.add(Calendar.DATE, i);
        String format = simpleDateFormat.format(calendar.getTime());
        return format;
    }

    /**
     * 获取上周的周一和周日
     * return String
     */
    public static String getLastTimeInterval(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        String lastBeginDate = sdf.format(calendar1.getTime());
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastBeginDate + "," + lastEndDate;
    }

    /**
     * 获取本月的所有天数
     * return List<String>
     */
    public static List<String> getLatestMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, 1);
        int month = cal.get(Calendar.MONTH);
        while (cal.get(Calendar.MONTH) == month) {
            String format = sdf.format(cal.getTime());
            list.add(format);
            cal.add(Calendar.DATE, 1);
        }
        return list;
    }

    /**
     * 获取本周的所有天数
     * return List<String>
     */
    public static List<String> getLatestWeek(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        // 今天是一周中的第几天
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (c.getFirstDayOfWeek() == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        List<String> list = new ArrayList<String>();
        // 计算一周开始的日期
        c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
        for (int i = 1; i <= 7; i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            String d = sdf.format(c.getTime());
            list.add(d);
        }
        return list;
    }

    /**
     * 获取本年所有月份
     * return List<String>
     */
    public static List<String> getAllMonth() {
        List<String> dateList = new ArrayList<>();
        Date d1 = null;//定义起始日期
        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        try {
            d1 = new SimpleDateFormat("yyyy-MM").parse(year + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar dd = Calendar.getInstance();//定义日期实例
        dd.setTime(d1);//设置日期起始时间
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            dateList.add(sdf.format(dd.getTime()));
            dd.add(Calendar.MONTH, 1);//进行当前日期 月份加1
        }
        return dateList;
    }


    /**
     * 获取某一月份的前六个月
     *
     * @param date 当前月份
     * @return
     */
    public static List<String> getSixMonth(String date) {
        List<String> list = new ArrayList<String>();
        int month = Integer.parseInt(date.substring(5, 7));
        int year = Integer.parseInt(date.substring(0, 4));
        for (int i = 5; i >= 0; i--) {
            if (month > 6) {
                if (month - i >= 10) {
                    list.add(year + "-" + String.valueOf(month - i));
                } else {
                    list.add(year + "-0" + String.valueOf(month - i));
                }
            } else {
                if (month - i <= 0) {
                    if (month - i + 12 >= 10) {
                        list.add(String.valueOf(year - 1) + "-" + String.valueOf(month - i + 12));
                    } else {
                        list.add(String.valueOf(year - 1) + "-0" + String.valueOf(month - i + 12));
                    }
                } else {
                    if (month - i >= 10) {
                        list.add(String.valueOf(year) + "-" + String.valueOf(month - i));
                    } else {
                        list.add(String.valueOf(year) + "-0" + String.valueOf(month - i));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取当前年份近几年年份集合
     *
     * @param num 负数为之前年,0为当前年,正数为之后年
     * @Description 获取年份
     **/

    public static List<String> getLatestYears(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        List<String> list = new ArrayList<>();
        int count = -num;
        for (int i = 0; i < count; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.YEAR, -i);
            Date y = c.getTime();
            list.add(format.format(y));
        }
        return list;
    }

    /**
     * 获取某一月份的前12个月月份集合
     *
     * @param date 当前月份
     * @return
     */
    public static List<String> get12Month(String date) {
        List<String> list = new ArrayList<String>();
        int month = Integer.parseInt(date.substring(5, 7));
        int year = Integer.parseInt(date.substring(0, 4));
        for (int i = 11; i >= 0; i--) {
            if (month > 12) {
                if (month - i >= 10) {
                    list.add(year + "-" + String.valueOf(month - i));
                } else {
                    list.add(year + "-0" + String.valueOf(month - i));
                }
            } else {
                if (month - i <= 0) {
                    if (month - i + 12 >= 10) {
                        list.add(String.valueOf(year - 1) + "-" + String.valueOf(month - i + 12));
                    } else {
                        list.add(String.valueOf(year - 1) + "-0" + String.valueOf(month - i + 12));
                    }
                } else {
                    if (month - i >= 10) {
                        list.add(String.valueOf(year) + "-" + String.valueOf(month - i));
                    } else {
                        list.add(String.valueOf(year) + "-0" + String.valueOf(month - i));
                    }
                }
            }
        }
        return list;
    }

    /**
     * 比较两个日期时间,在当前日期之后返回true
     */
    public static boolean compareDateTime(String paramTime) {
        LocalDateTime paramDate = LocalDateTime.parse(paramTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (LocalDateTime.now().isAfter(paramDate)) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        List<String> latest7DayList = getLatestDayList(7);
        System.out.println("最近7天===>" + latest7DayList);
        List<String> latest28DayList = getLatestDayList(28);
        System.out.println("最近28天===>" + latest28DayList);
        List<String> all = getSixMonth("2022-08");
        System.out.println("当前月份的前六个月集合（最近半年）===>" + all);
        List<String> all2 = get12Month("2022-08");
        System.out.println("当前月份的前12个月集合（最近一年）===>" + all2);
        List<String> latestYears = getLatestYears(-5);
        System.out.println("获取当前年份近几年年份集合===>" + latestYears);
        String date = DateUtils.getDate();
        List<String> month = MyDateUtils.get12Month(date);
        List<String> month2 = MyDateUtils.getSixMonth(date);
        List<String> month3 = MyDateUtils.getAllMonth();
        System.out.println(month);
        System.out.println(month2);
        System.out.println(month3);
        boolean b = compareDateTime("2022-08-09 12:12:12");
        System.out.println(b);
    }
}
