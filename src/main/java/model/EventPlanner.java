package model;

import config.EventConfig;

import java.util.HashMap;
import java.util.Map;

public class EventPlanner {
    private static final int GIVEAWAY_THRESHOLD = 120000;
    private static final String GIVEAWAY_ITEM = "샴페인";
    private static final int GIVEAWAY_NUMBER = 1;
    private static final String firstBadge = "산타";
    private static final int firstBadgeCondition = 20000;
    private static final String secondBadge = "트리";
    private static final int secondBadgeCondition = 10000;
    private static final String thirdBadge = "별";
    private static final int thirdBadgeCondition = 5000;
    private static final String nonBadge = "없음";

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

    public Map<String, Integer> calculateGiveaway() {
        if (totalValueBeforeDiscount >= GIVEAWAY_THRESHOLD){
            giveaway.put(GIVEAWAY_ITEM, GIVEAWAY_NUMBER);
        }
        return giveaway;
    }

    public Map<String, Integer> calculateBenefits(){
        Map<String, Integer> discounts = discount.calculateDiscount(date.getDate(), orderedMenu.calculateFoodTypeNumber().get("dessert"), orderedMenu.calculateFoodTypeNumber().get("main"));
        benefits = new HashMap<>(discounts);
        if (giveaway.size() != 0){
            benefits.put(GIVEAWAY_ITEM, GIVEAWAY_NUMBER * EventConfig.MENU.valueOfKoreanName(GIVEAWAY_ITEM).getValue());
        }
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
        if (totalBenefit >= firstBadgeCondition){
            return firstBadge;
        }
        if (totalBenefit >= secondBadgeCondition){
            return secondBadge;
        }
        if (totalBenefit >= thirdBadgeCondition){
            return thirdBadge;
        }
        return nonBadge;
    }
}
