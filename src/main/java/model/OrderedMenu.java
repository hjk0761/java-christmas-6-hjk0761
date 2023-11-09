package model;


import config.ErrorMessage;
import config.EventConfig;

import java.util.*;

public class OrderedMenu {

    private static final String ORDER_SEPARATOR = ",";
    private static final String MENU_SEPARATOR = "-";
    private static final int MAX_MENU = 20;
    private static final String UNIQUE_CONDITION = "beverage";

    private final Map<String, Integer> orderedMenu;

    public OrderedMenu(String inputOrderedMenu){
        Map<String, Integer> orderedMenu;
        validateEmpty(inputOrderedMenu);
        orderedMenu = validateForm(inputOrderedMenu);
        validateNotInMenu(orderedMenu);
        validateMenuNumber(orderedMenu);
        validateDuplication(orderedMenu);
        validateNotUnique(orderedMenu);
        this.orderedMenu = orderedMenu;
    }

    public int calculateTotalValue() {
        int totalValue = 0;
        for (Map.Entry<String, Integer> entry : orderedMenu.entrySet()) {
            totalValue += entry.getValue();
        }
        return totalValue;
    }

    public Map<String, Integer> calculateFoodTypeNumber() {
        Map<String, Integer> foodTypeNumber = new HashMap<>();
        for (Map.Entry<String, Integer> entry : orderedMenu.entrySet()) {
            if (foodTypeNumber.containsKey(entry.getKey())){
                foodTypeNumber.put(entry.getKey(), foodTypeNumber.get(entry.getKey() + 1));
                continue;
            }
            foodTypeNumber.put(entry.getKey(), 1);
        }
        return foodTypeNumber;
    }

    private void validateEmpty(String inputOrderedMenu){
        if (inputOrderedMenu == null || inputOrderedMenu.equals("")){
            throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
        }
    }

    private Map<String, Integer> validateForm(String inputOrderedMenu){
        List<String> temp = List.of(inputOrderedMenu.split(ORDER_SEPARATOR));
        List<List<String>> temp2 = new ArrayList<>();
        Map<String, Integer> temp3 = new HashMap<>();
        for (String tempMenu : temp){
            temp2.add(List.of(tempMenu.split(MENU_SEPARATOR)));
        }
        for (List<String> temp4 : temp2){
            try{
                temp3.put(temp4.get(0), Integer.parseInt(temp4.get(1)));
            } catch (NumberFormatException e){
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
        return temp3;
    }

    private void validateNotInMenu(Map<String, Integer> orderedMenu){
        for (String menuName : orderedMenu.keySet()){
            if (EventConfig.MENU.valueOfKoreanName(menuName) == null){
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
    }

    private void validateMenuNumber(Map<String, Integer> orderedMenu){
        int totalMenuNumber = 0;
        for (int menuNumber : orderedMenu.values()){
            totalMenuNumber += menuNumber;
            if (menuNumber <= 0){
                throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
            }
        }
        if (totalMenuNumber > MAX_MENU){
            throw new IllegalArgumentException(String.format(ErrorMessage.OVER_MAX_MENU.getErrorMessage(), MAX_MENU));
        }
    }

    private void validateDuplication(Map<String, Integer> orderedMenu){
        Set<String> duplicationTester = new HashSet<>(orderedMenu.keySet());
        if (duplicationTester.size() != orderedMenu.size()){
            throw new IllegalArgumentException(ErrorMessage.NON_VALID_MENU.getErrorMessage());
        }
    }

    private void validateNotUnique(Map<String, Integer> orderedMenu){
        Set<String> typeTester = new HashSet<>();
        for (String menuName : orderedMenu.keySet()){
            typeTester.add(EventConfig.MENU.valueOfKoreanName(menuName).getFoodType());
        }
        if (typeTester.size() == 1 && typeTester.contains(UNIQUE_CONDITION)){
            throw new IllegalArgumentException(ErrorMessage.ALL_BEVERAGE_MENU.getErrorMessage());
        }
    }
}
