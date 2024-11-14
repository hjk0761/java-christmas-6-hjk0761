package config;

public enum ErrorMessage {

    NON_VALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    NON_VALID_MENU("유효하지 않은 주문입니다. 다시 입력해 주세요."),
    OVER_MAX_MENU("메뉴는 최대 %d 개 까지만 주문할 수 있습니다. 다시 입력해 주세요."),
    ALL_BEVERAGE_MENU("음료만 주문은 불가능합니다. 다시 입력해 주세요.");

    private final String errorMessage;
    private final static String errorPrefix = "[ERROR] ";

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorPrefix.concat(errorMessage);
    }
}
