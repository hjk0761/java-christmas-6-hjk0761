package christmas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import model.Date;
import model.EventPlanner;
import model.OrderedMenu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EventPlannerTest {

    @DisplayName("날짜가 숫자가 아닌 경우 예외가 발생한다.")
    @Test
    void createDateByNoneNumber(){
        assertThatThrownBy(() -> new Date("1a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("날짜가 범위를 벗어난 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "-24", "35"})
    void createDateByOverRange(String test){
        assertThatThrownBy(() -> new Date(test))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 없는 주문인 경우 예외가 발생한다.")
    @Test
    void createOrderedMenuByNotInMenu(){
        assertThatThrownBy(() -> new OrderedMenu("바비큐립-1,미트볼스파게티-1,제로콜라-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 개수가 숫자가 아닌 경우 예외가 발생한다.")
    @Test
    void createOrderedMenuByWrongNumber(){
        assertThatThrownBy(() -> new OrderedMenu("바비큐립-1a,제로콜라-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 개수가 범위를 벗어난 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"바비큐립-0,제로콜라-1", "바비큐립-10,제로콜라-10,아이스크림-1"})
    void createOrderedMenuByWrongRangeNumber(String test){
        assertThatThrownBy(() -> new OrderedMenu(test))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 형식이 다른 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"바비큐립-1제로콜라-1", "바비큐립1,제로콜라1", "바비큐립=1,제로콜라-1", "1-바비큐립,1-제로콜라"})
    void createOrderedMenuByWrongFormat(String test){
        assertThatThrownBy(() -> new OrderedMenu(test))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("중복된 메뉴의 경우 예외가 발생한다.")
    @Test
    void createOrderedMenuByDuplicatedMenu(){
        assertThatThrownBy(() -> new OrderedMenu("바비큐립-1,제로콜라-1,바비큐립-3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("음료만 주문한 경우 예외가 발생한다.")
    @Test
    void createOrderedMenuByOnlyBeverage(){
        assertThatThrownBy(() -> new OrderedMenu("레드와인-1,제로콜라-2,샴페인-3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정해진 날에 특별 할인을 적용한다.")
    @Test
    void specialDiscountTest(){
        EventPlanner eventPlanner = new EventPlanner(new Date("17"), new OrderedMenu("티본스테이크-1"));
        eventPlanner.calculateGiveaway();
        assertThat(eventPlanner.calculateBenefits()).containsKey("특별 할인");
        assertThat(eventPlanner.calculateBenefits()).containsValue(1000);
    }

    @DisplayName("일요일~목요일에 평일 할인을 적용한다.")
    @Test
    void weekdayDiscountTest(){
        EventPlanner eventPlanner = new EventPlanner(new Date("12"), new OrderedMenu("티본스테이크-3,아이스크림-2"));
        eventPlanner.calculateGiveaway();
        assertThat(eventPlanner.calculateBenefits()).containsKey("평일 할인");
        assertThat(eventPlanner.calculateBenefits()).containsValue(4046);
    }

    @DisplayName("금, 토요일에 주말 할인을 적용한다.")
    @Test
    void weekendDiscountTest(){
        EventPlanner eventPlanner = new EventPlanner(new Date("23"), new OrderedMenu("티본스테이크-3,아이스크림-2"));
        eventPlanner.calculateGiveaway();
        assertThat(eventPlanner.calculateBenefits()).containsKey("주말 할인");
        assertThat(eventPlanner.calculateBenefits()).containsValue(6069);
    }

    @DisplayName("25일까지 디데이 할인을 적용한다.")
    @Test
    void d_dayDiscountTest(){
        EventPlanner eventPlanner = new EventPlanner(new Date("23"), new OrderedMenu("티본스테이크-3,아이스크림-2"));
        eventPlanner.calculateGiveaway();
        assertThat(eventPlanner.calculateBenefits()).containsKey("크리스마스 디데이 할인");
        assertThat(eventPlanner.calculateBenefits()).containsValue(3200);
    }

    @DisplayName("총 주문 금액이 12만원 이상일 때, 샴페인 1개를 증정한다.")
    @Test
    void giveawayTest(){
        EventPlanner eventPlanner = new EventPlanner(new Date("25"), new OrderedMenu("티본스테이크-2,바비큐립-2,해산물파스타-1,레드와인-2"));
        assertThat(eventPlanner.calculateGiveaway()).containsKey("샴페인");
        assertThat(eventPlanner.calculateGiveaway()).containsValue(1);
        assertThat(eventPlanner.calculateBenefits()).containsKey("증정 이벤트");
        assertThat(eventPlanner.calculateBenefits()).containsValue(25000);
    }

    @DisplayName("총혜택 금액에 따라 배지를 증정한다.")
    @ParameterizedTest
    @MethodSource("generateBadgeCase")
    void badgeTest(String order, String badge){
        EventPlanner eventPlanner = new EventPlanner(new Date("7"), new OrderedMenu(order));
        eventPlanner.calculateGiveaway();
        eventPlanner.calculateBenefits();
        eventPlanner.calculateTotalBenefits();
        assertThat(eventPlanner.calculateBadge()).isEqualTo(badge);
    }

    static Stream<Arguments> generateBadgeCase() {
        return Stream.of(
                Arguments.of("티본스테이크-2,크리스마스파스타-2,레드와인-3", "산타"),
                Arguments.of("초코케이크-3,아이스크림-2", "트리"),
                Arguments.of("초코케이크-2,제로콜라-1", "별"),
                Arguments.of("양송이수프-1", "없음"));
    }
}
