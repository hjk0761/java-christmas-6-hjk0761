package config;

public enum ErrorMessage {

    NON_VALID_DATE("유효하지 않은 날짜입니다. 다시 입력해 주세요."),
    NON_VALID_MENU("유효하지 않은 주문입니다. 다시 입력해 주세요.");

    private final String errorMessage;
    private final static String errorPrefix = "[ERROR] ";

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorPrefix.concat(errorMessage);
    }
}
