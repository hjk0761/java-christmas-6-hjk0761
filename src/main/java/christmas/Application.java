package christmas;

import controller.EventPlannerController;

public class Application {
    public static void main(String[] args) {
        EventPlannerController controller = new EventPlannerController();
        controller.start();
    }
}
