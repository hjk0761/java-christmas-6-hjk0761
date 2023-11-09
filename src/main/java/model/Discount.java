package model;

import config.EventConfig;

public class Discount {

    public int calculateDiscount(int day, int dessert, int main){
        return discountD_DAY(day) + discountWeekDay(day, dessert) + discountWeedEnd(day, main) + discountSpecial(day);
    }

    private int discountD_DAY(int day){
        if (day <= 25){
            return 900 + day * 100;
        }
        return 0;
    }

    private int discountWeekDay(int day, int dessert){
        if (!EventConfig.WEEKEND.contains(day)){
            return dessert * 2023;
        }
        return 0;
    }

    private int discountWeedEnd(int day, int main){
        if (EventConfig.WEEKEND.contains(day)){
            return main * 2023;
        }
        return 0;
    }

    private int discountSpecial(int day){
        if (EventConfig.SPECIAL.contains(day)){
            return 1000;
        }
        return 0;
    }
}
