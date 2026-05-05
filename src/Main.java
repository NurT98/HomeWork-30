

public class Main {

    public static void main(String[] args) {

        // это для домашки
        // выберите любое количество заказов, какое вам нравится.

        //var orders = RestaurantOrders.read("orders_100.json").getOrders();
        //var orders = RestaurantOrders.read("orders_1000.json").getOrders();
        //var orders = RestaurantOrders.read("orders_10_000.json").getOrders();

        // протестировать ваши методы вы можете как раз в этом файле (или в любом другом, в котором вам будет удобно)

        var ro = RestaurantOrders.read("orders_100.json");

        System.out.println("Полный список заказов");
        ro.printOrders();

        System.out.println("\nТоп 3 дорогих заказов");
        ro.getTopNExpensiveOrders(3).forEach(System.out::println);

        System.out.println("\nТоп 3 дешевых заказов");
        ro.getTopNCheapestOrders(3).forEach(System.out::println);

        System.out.println("\nВсе заказы с доставкой на дом");
        ro.getHomeDeliveryOrders().forEach(System.out::println);

        System.out.println("\nОбщая прибыль с доставок");
        ro.printMinMaxHomeOrders();

        double min = 10.0;
        double max = 100.0;
        ro.getOrdersInPriceRange(min, max).forEach(System.out::println);

        System.out.println("\nОбщая прибыль ресторана");
        System.out.printf("Сумма всех заказов: %.2f\n", ro.getTotalCostOfAllOrders());

        System.out.println("\nСписок уникальных адресов электронной почты");
        System.out.println(ro.getUniqueSortedEmails());
    }
}
