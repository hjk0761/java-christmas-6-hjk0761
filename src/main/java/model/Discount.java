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

    private Map<String, Integer> discountD_DAY(int day){
        if (day <= D_DAY){
            return new HashMap<>(){{put(D_DAY_DISCOUNT, 900 + day * 100);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountWeekDay(int day, int dessert){
        if (!EventConfig.WEEKEND.contains(day)){
            return new HashMap<>(){{put(WEEKDAY_DISCOUNT, dessert * WEEKDAY_DISCOUNT_AMOUNT);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountWeekEnd(int day, int main){
        if (EventConfig.WEEKEND.contains(day)){
            return new HashMap<>(){{put(WEEKEND_DISCOUNT, main * WEEKEND_DISCOUNT_AMOUNT);}};
        }
        return new HashMap<>();
    }

    private Map<String, Integer> discountSpecial(int day){
        if (EventConfig.SPECIAL.contains(day)){
            return new HashMap<>(){{put(SPECIAL_DISCOUNT, SPECIAL_DISCOUNT_AMOUNT);}};
        }
        return new HashMap<>();
    }
}
