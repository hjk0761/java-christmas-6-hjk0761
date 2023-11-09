package model;

import config.EventConfig;

import java.util.HashMap;
import java.util.Map;

public class EventPlanner {
    private static final int GIVEAWAY_THRESHOLD = 120000;
    private static final String GIVEAWAY_ITEM = "샴페인";

    private final Date date;
    private final OrderedMenu orderedMenu;
    private final int totalValueBeforeDiscount;
    private final Map<String, Integer> giveaway = new HashMap<>();
    private Map<String, Integer> benefits;

    Discount discount = new Discount();

    public EventPlanner(Date date, OrderedMenu orderedMenu){
        this.date = date;
        this.orderedMenu = orderedMenu;
        this.totalValueBeforeDiscount = orderedMenu.calculateTotalValue();
    }

    public int getTotalValueBeforeDiscount(){
        return totalValueBeforeDiscount;
    }

    public void calculateGiveaway(){
        if (totalValueBeforeDiscount >= GIVEAWAY_THRESHOLD){
            giveaway.put(GIVEAWAY_ITEM, EventConfig.MENU.valueOfKoreanName(GIVEAWAY_ITEM).getValue());
        }
    }

    public void calculateBenefits(){
        Map<String, Integer> discounts = discount.calculateDiscount(date.getDate(), orderedMenu.calculateFoodTypeNumber().get("dessert"), orderedMenu.calculateFoodTypeNumber().get("main"));
        benefits = new HashMap<>(discounts);
        giveaway.forEach((key, value) -> benefits.merge(key, value, (v1, v2) -> v2));
    }

    public int calculateTotalBenefits(){
        int totalBenefits = 0;
        for (Map.Entry<String, Integer> entry : benefits.entrySet()){
            totalBenefits += entry.getValue();
        }
        return totalBenefits;
    }

    public int calculateTotalValueAfterDiscount(){
        return totalValueBeforeDiscount - discount.getTotalDiscount();
    }
}
