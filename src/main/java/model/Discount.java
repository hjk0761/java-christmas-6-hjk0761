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
        int d_day = discountD_DAY(date);
        if (d_day != 0){
            temp.put(D_DAY_DISCOUNT, d_day);
        }
        int weekday = discountWeekDay(date, dessert);
        if (weekday != 0){
            temp.put(WEEKDAY_DISCOUNT, weekday);
        }
        int weekend = discountWeedEnd(date, main);
        if (weekend != 0){
            temp.put(WEEKEND_DISCOUNT, weekend);
        }
        int special = discountSpecial(date);
        if (special != 0){
            temp.put(SPECIAL_DISCOUNT, special);
        }
        return temp;
    }

    private int discountD_DAY(int day){
        if (day <= D_DAY){
            return 900 + day * 100;
        }
        return 0;
    }

    private int discountWeekDay(int day, int dessert){
        if (!EventConfig.WEEKEND.contains(day)){
            return dessert * WEEKDAY_DISCOUNT_AMOUNT;
        }
        return 0;
    }

    private int discountWeedEnd(int day, int main){
        if (EventConfig.WEEKEND.contains(day)){
            return main * WEEKEND_DISCOUNT_AMOUNT;
        }
        return 0;
    }

    private int discountSpecial(int day){
        if (EventConfig.SPECIAL.contains(day)){
            return SPECIAL_DISCOUNT_AMOUNT;
        }
        return 0;
    }
}
