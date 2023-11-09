package model;

import config.ErrorMessage;

public class Date {
    private final int date;

    public Date(String inputDate){
        int date = validateNumeric(inputDate);
        validateRange(date);
        this.date = date;
    }

    public int getDate(){
        return date;
    }

    private int validateNumeric(String inputDate){
        try {
            return Integer.parseInt(inputDate);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.NON_VALID_DATE.getErrorMessage());
        }
    }

    private void validateRange(int date){
        if (date < 1 || date > 31){
            throw new IllegalArgumentException(ErrorMessage.NON_VALID_DATE.getErrorMessage());
        }
    }
}
