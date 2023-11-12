package view;

import model.OrderedMenu;

import java.text.DecimalFormat;
import java.util.Map;

public class OutputView {
    private static final String INIT_MESSAGE = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String READ_DATE = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String READ_MENU = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String INIT_EVENT = "12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDERED_MENU = "<주문 메뉴>";
    private static final String TOTAL_VALUE_BEFORE_DISCOUNT = "<할인 전 총주문 금액>";
    private static final String GIVEAWAY = "<증정 메뉴>";
    private static final String BENEFITS = "<혜택 내역>";
    private static final String TOTAL_BENEFITS = "<총혜택 금액>";
    private static final String TOTAL_VALUE_AFTER_DISCOUNT = "<할인 후 예상 결제 금액>";
    private static final String BADGE = "<12월 이벤트 배지>";
    private static final DecimalFormat THOUSAND_SEPARATOR = new DecimalFormat("#,###");
    private static final String MENU_FORMAT = "%s %d개";

    public void printMessage(String message){
        System.out.println(message);
    }

    public void printInit() {
        System.out.println(INIT_MESSAGE);
    }

    public void printDate() {
        System.out.println(READ_DATE);
    }

    public void printOrder() {
        System.out.println(READ_MENU);
    }

    public void printEvent(int date) {
        System.out.printf(INIT_EVENT, date);
        System.out.println();
        System.out.println();
    }

    public void printOrderedMenu(OrderedMenu orderedMenu) {
        System.out.println(ORDERED_MENU);
        for (Map.Entry<String, Integer> entry : orderedMenu.getOrderedMenu().entrySet()){
            System.out.printf(MENU_FORMAT, entry.getKey(), entry.getValue());
            System.out.println();
        }
        System.out.println();
    }

    public void printTotalValueBeforeDiscount(int totalValueBeforeDiscount) {
        System.out.println(TOTAL_VALUE_BEFORE_DISCOUNT);
        System.out.println(THOUSAND_SEPARATOR.format(totalValueBeforeDiscount) + "원");
        System.out.println();
    }

    public void printGiveaway(Map<String, Integer> giveaway) {
        String printingGiveaway = "없음";
        if (giveaway.size() != 0) {
            for (Map.Entry<String, Integer> entry : giveaway.entrySet()){
                printingGiveaway = String.format(MENU_FORMAT, entry.getKey(), entry.getValue());
            }
        }
        System.out.println(GIVEAWAY);
        System.out.println(printingGiveaway);
        System.out.println();
    }

    public void printBenefits(Map<String, Integer> benefits) {
        System.out.println(BENEFITS);
        if (benefits.size() != 0) {
            for (Map.Entry<String, Integer> entry : benefits.entrySet()) {
                System.out.println(entry.getKey() + ": -" + THOUSAND_SEPARATOR.format(entry.getValue()) + "원");
            }
            System.out.println();
            return;
        }
        System.out.println("없음");
        System.out.println();
    }

    public void printTotalBenefits(int totalBenefits) {
        String printingTotalBenefits = "0원";
        System.out.println(TOTAL_BENEFITS);
        if (totalBenefits > 0) {
            printingTotalBenefits = "-" + THOUSAND_SEPARATOR.format(totalBenefits) + "원";
        }
        System.out.println(printingTotalBenefits);
        System.out.println();
    }

    public void printTotalValueAfterDiscount(int totalValueAfterDiscount) {
        System.out.println(TOTAL_VALUE_AFTER_DISCOUNT);
        System.out.println(THOUSAND_SEPARATOR.format(totalValueAfterDiscount) + "원");
        System.out.println();
    }

    public void printBadge(String badge) {
        String printingBadge = "없음";
        if (badge.length() != 0){
            printingBadge = badge;
        }
        System.out.println(BADGE);
        System.out.println(printingBadge);
    }
}
