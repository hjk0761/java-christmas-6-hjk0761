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
    private int totalBenefit = 0;

    Discount discount = new Discount();

    public EventPlanner(Date date, OrderedMenu orderedMenu){
        this.date = date;
        this.orderedMenu = orderedMenu;
        this.totalValueBeforeDiscount = orderedMenu.calculateTotalValue();
    }

    public int getDate() {
        return date.getDate();
    }

    public OrderedMenu getOrderedMenu() {
        return orderedMenu;
    }

    public int getTotalValueBeforeDiscount() {
        return totalValueBeforeDiscount;
    }

    public boolean calculateGiveaway() {
        if (totalValueBeforeDiscount >= GIVEAWAY_THRESHOLD){
            giveaway.put(GIVEAWAY_ITEM, EventConfig.MENU.valueOfKoreanName(GIVEAWAY_ITEM).getValue());
        }
        return giveaway.size() == 1;
    }

    public Map<String, Integer> calculateBenefits(){
        Map<String, Integer> discounts = discount.calculateDiscount(date.getDate(), orderedMenu.calculateFoodTypeNumber().get("dessert"), orderedMenu.calculateFoodTypeNumber().get("main"));
        benefits = new HashMap<>(discounts);
        giveaway.forEach((key, value) -> benefits.merge(key, value, (v1, v2) -> v2));
        return benefits;
    }

    public int calculateTotalBenefits(){
        for (Map.Entry<String, Integer> entry : benefits.entrySet()){
            totalBenefit += entry.getValue();
        }
        return totalBenefit;
    }

    public int calculateTotalValueAfterDiscount(){
        return totalValueBeforeDiscount - discount.getTotalDiscount();
    }

    public String calculateBadge() {
        if (totalBenefit >= 20000){
            return "산타";
        }
        if (totalBenefit >= 10000){
            return "트리";
        }
        if (totalBenefit >= 5000){
            return "별";
        }
        return "";
    }
}
