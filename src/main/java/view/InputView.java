package view;

import camp.nextstep.edu.missionutils.Console;
import model.Date;
import model.OrderedMenu;

public class InputView {

    public Date readDate(){
        String input = Console.readLine();
        return new Date(input);
    }

    public OrderedMenu readMenu(){
        String input = Console.readLine();
        return new OrderedMenu(input);
    }
}
