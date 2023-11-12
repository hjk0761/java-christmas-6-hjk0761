package model;

import config.EventConfig;

import java.util.HashMap;
import java.util.Map;

public class Discount {

    private static final int D_DAY = 25;
    private static final int WEEKDAY_DISCOUNT_AMOUNT = 2023;
    private static final int WEEKEND_DISCOUNT_AMOUNT = 2023;
    private static final int SPECIAL_DISCOUNT_AMOUNT = 1000;
    private static final String D_DAY_DISCOUNT = "크리스마스 디데이 할인";
    private static final String WEEKDAY_DISCOUNT = "평일 할인";
    private static final String WEEKEND_DISCOUNT = "주말 할인";
    private static final String SPECIAL_DISCOUNT = "특별 할인";
    private int totalDiscount = 0;

    public Map<String, Integer> calculateDiscount(int date, int dessert, int main){
        Map<String, Integer> temp = new HashMap<>();
        Map<String, Integer> d_day = discountD_DAY(date);
        d_day.forEach((key, value) -> temp.merge(key, value, (v1, v2) -> v2));
        Map<String, Integer> weekday = discountWeekDay(date, dessert);
        weekday.forEach((key, value) -> temp.merge(key, value, (v1, v2) -> v2));
        Map<String, Integer> weekend = discountWeekEnd(date, main);
        weekend.forEach((key, value) -> temp.merge(key, value, (v1, v2) -> v2));
        Map<String, Integer> special = discountSpecial(date);
        special.forEach((key, value) -> temp.merge(key, value, (v1, v2) -> v2));
        return temp;
    }

    public int getTotalDiscount(){
        return totalDiscount;
    }

    private Map<String, Integer> discountD_DAY(int day){
        if (day <= D_DAY){
            int d_day = 900 + day * 100;
            totalDiscount += d_day;
            return new HashMap<>(){{put(D_DAY_DISCOUNT, d_day);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountWeekDay(int day, int dessert){
        if (!EventConfig.WEEKEND.contains(day) && dessert != 0){
            int weekday = dessert * WEEKDAY_DISCOUNT_AMOUNT;
            totalDiscount += weekday;
            return new HashMap<>(){{put(WEEKDAY_DISCOUNT, weekday);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountWeekEnd(int day, int main){
        if (EventConfig.WEEKEND.contains(day) && main != 0){
            int weekend = main * WEEKEND_DISCOUNT_AMOUNT;
            totalDiscount += weekend;
            return new HashMap<>(){{put(WEEKEND_DISCOUNT, weekend);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountSpecial(int day){
        if (EventConfig.SPECIAL.contains(day)){
            int special = SPECIAL_DISCOUNT_AMOUNT;
            totalDiscount += special;
            return new HashMap<>(){{put(SPECIAL_DISCOUNT, special);}};
        }
        return new HashMap<>();
    }
}
