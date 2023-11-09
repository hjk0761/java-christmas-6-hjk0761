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
        Date date = inputView.readDate();
        outputView.printOrder();
        OrderedMenu orderedMenu = inputView.readMenu();
        eventPlanner = new EventPlanner(date, orderedMenu);
    }

    private void process(){
        int totalValueBeforeDiscount = eventPlanner.getTotalValueBeforeDiscount();
        eventPlanner.calculateGiveaway();
        eventPlanner.calculateBenefits();
        int totalBenefits = eventPlanner.calculateTotalBenefits();
        int totalValueAfterDiscount = eventPlanner.calculateTotalValueAfterDiscount();
        // TODO
    }

    private void finish(){
        // TODO
    }
}
