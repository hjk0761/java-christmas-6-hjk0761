package model;

import config.ErrorMessage;
import config.EventConfig;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderedMenu {

    private static final String ORDER_SEPARATOR = ",";
    private static final String MENU_SEPARATOR = "-";
    private static final int MAX_MENU = 20;
    private static final String UNIQUE_CONDITION = "beverage";

    private final Map<String, Integer> orderedMenu;

    public OrderedMenu(String inputOrderedMenu) {
        Map<String, Integer> orderedMenu;
        validateEmpty(inputOrderedMenu);
        orderedMenu = validateForm(inputOrderedMenu);
        validateNotInMenu(orderedMenu);
        validateMenuNumber(orderedMenu);
        validateNotUnique(orderedMenu);
        this.orderedMenu = orderedMenu;
    }

    public Map<String, Integer> getOrderedMenu() {
        return orderedMenu;
    }

    public int calculateTotalValue() {
        int totalValue = 0;
        for (Map.Entry<String, Integer> entry : orderedMenu.entrySet()) {
            totalValue += EventConfig.MENU.valueOfKoreanName(entry.getKey()).getValue() * entry.getValue();
        }
        return totalValue;
    }

    public Map<String, Integer> calculateFoodTypeNumber() {
        Map<String, Integer> foodTypeNumber = EventConfig.MENU.valueOfFoodTypeList()
                .stream()
                .collect(Collectors.toMap(type -> type, type -> 0));
        for (Map.Entry<String, Integer> entry : orderedMenu.entrySet()) {
            String foodType = EventConfig.MENU.valueOfKoreanName(entry.getKey()).getFoodType();
            foodTypeNumber.put(foodType, foodTypeNumber.get(foodType) + entry.getValue());
        }
        return foodTypeNumber;
    }

    private void validateEmpty(String inputOrderedMenu) {
        if (inputOrderedMenu == null || inputOrderedMenu.equals("")) {
            throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
        }
    }

    private Map<String, Integer> validateForm(String inputOrderedMenu) {
        List<String> eachMenus = List.of(inputOrderedMenu.split(ORDER_SEPARATOR));
        Map<String, Integer> orderingMenu = new HashMap<>();
        for (String eachMenu : eachMenus) {
            List<String> menuNumber = List.of(eachMenu.split(MENU_SEPARATOR));
            try {
                if (orderingMenu.containsKey(menuNumber.get(0))) {
                    throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
                }
                orderingMenu.put(menuNumber.get(0), Integer.parseInt(menuNumber.get(1)));
            } catch (NumberFormatException | IndexOutOfBoundsException e) {
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
        return orderingMenu;
    }

    private void validateNotInMenu(Map<String, Integer> orderedMenu) {
        for (String menuName : orderedMenu.keySet()) {
            if (EventConfig.MENU.valueOfKoreanName(menuName) == null) {
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
    }

    private void validateMenuNumber(Map<String, Integer> orderedMenu) {
        int totalMenuNumber = 0;
        for (int menuNumber : orderedMenu.values()) {
            totalMenuNumber += menuNumber;
            if (menuNumber <= 0) {
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
        if (totalMenuNumber > MAX_MENU) {
            throw new IllegalArgumentException(String.format(ErrorMessage.OVER_MAX_MENU.getErrorMessage(), MAX_MENU));
        }
    }

    private void validateNotUnique(Map<String, Integer> orderedMenu) {
        Set<String> typeTester = new HashSet<>();
        for (String menuName : orderedMenu.keySet()) {
            typeTester.add(EventConfig.MENU.valueOfKoreanName(menuName).getFoodType());
        }
        if (typeTester.size() == 1 && typeTester.contains(UNIQUE_CONDITION)) {
            throw new IllegalArgumentException(ErrorMessage.ALL_BEVERAGE_MENU.getErrorMessage());
        }
    }
}
