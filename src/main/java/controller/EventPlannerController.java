package controller;

import model.Date;
import model.EventPlanner;
import model.OrderedMenu;
import view.InputView;
import view.OutputView;

public class EventPlannerController {
    private EventPlanner eventPlanner;
    InputView inputView = new InputView();
    OutputView outputView = new OutputView();

    public void init(){
        start();
        process();
        finish();
    }

    private void start(){
        outputView.printInit();
        Date date = setDate();
        OrderedMenu orderedMenu = setOrderedMenu();
        eventPlanner = new EventPlanner(date, orderedMenu);
    }

    private Date setDate(){
        Date date = null;
        do {
            try {
                outputView.printDate();
                date = inputView.readDate();
            } catch (IllegalArgumentException e){
                outputView.printMessage(e.getMessage());
            }
        } while(date == null);
        return date;
    }

    private OrderedMenu setOrderedMenu(){
        OrderedMenu orderedMenu = null;
        do {
            try {
                outputView.printOrder();
                orderedMenu = inputView.readMenu();
            } catch (IllegalArgumentException e){
                outputView.printMessage(e.getMessage());
            }
        } while(orderedMenu == null);
        return orderedMenu;
    }

    private void process(){
        outputView.printEvent(eventPlanner.getDate());
        outputView.printOrderedMenu(eventPlanner.getOrderedMenu());
        outputView.printTotalValueBeforeDiscount(eventPlanner.getTotalValueBeforeDiscount());
        outputView.printGiveaway(eventPlanner.calculateGiveaway());
        outputView.printBenefits(eventPlanner.calculateBenefits());
        outputView.printTotalBenefits(eventPlanner.calculateTotalBenefits());
        outputView.printTotalValueAfterDiscount(eventPlanner.calculateTotalValueAfterDiscount());
        // TODO: Badge
    }

    private void finish(){
        // TODO
    }
}
