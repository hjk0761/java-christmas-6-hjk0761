package config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventConfig {

    public static final List<Integer> WEEKEND = new ArrayList<>(List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30));
    public static final List<Integer> SPECIAL = new ArrayList<>(List.of(3, 10, 17, 24, 25, 31));

    public enum MENU {
        MUSHROOM_SOUP("양송이수프", "appetizer", 6000),
        TAPAS("타파스", "appetizer", 5500),
        CAESAR_SALAD("시저샐러드", "appetizer", 8000),
        T_BONE_STEAK("티본스테이크", "main", 55000),
        BARBEQUE_RIBS("바비큐립", "main", 54000),
        SEAFOOD_PASTA("해산물파스타", "main", 35000),
        CHRISTMAS_PASTA("크리스마스파스타", "main", 25000),
        CHOCO_CAKE("초코케이크", "dessert", 15000),
        ICE_CREAM("아이스크림", "dessert", 5000),
        ZERO_COKE("제로콜라", "beverage", 3000),
        RED_WINE("레드와인", "beverage", 60000),
        CHAMPAGNE("샴페인", "beverage", 25000);

        private final String koreanName;
        private final String foodType;
        private final int value;

        MENU(String koreanName, String foodType, int value){
            this.koreanName = koreanName;
            this.foodType = foodType;
            this.value = value;
        }

        public String getFoodType() {
            return foodType;
        }

        public int getValue() {
            return value;
        }

        public static MENU valueOfKoreanName(String koreanName) {
            return Arrays.stream(values())
                    .filter(value -> Objects.equals(value.koreanName, koreanName))
                    .findAny()
                    .orElse(null);
        }

        public static List<String> valueOfFoodTypeList() {
            return Arrays.stream(values())
                    .map(MENU::getFoodType)
                    .collect(Collectors.toSet())
                    .stream()
                    .toList();
        }
    }
}
