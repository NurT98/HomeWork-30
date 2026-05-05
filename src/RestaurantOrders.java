import com.google.gson.Gson;
import domain.Item;
import domain.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantOrders {
    // Этот блок кода менять нельзя! НАЧАЛО!
    private List<Order> orders;

    private RestaurantOrders(String fileName) {
        var filePath = Path.of("data", fileName);
        Gson gson = new Gson();
        try {
            orders = List.of(gson.fromJson(Files.readString(filePath), Order[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RestaurantOrders read(String fileName) {
        var ro = new RestaurantOrders(fileName);
        ro.getOrders().forEach(Order::calculateTotal);
        return ro;
    }

    public List<Order> getOrders() {
        return orders;
    }
    // Этот блок кода менять нельзя! КОНЕЦ!

    //----------------------------------------------------------------------
    //------   Реализация ваших методов должна быть ниже этой линии   ------
    //----------------------------------------------------------------------

    // Наполните этот класс решением домашнего задания.
    // Вам необходимо создать все необходимые методы
    // для решения заданий из домашки :)
    // вы можете добавлять все необходимые imports
    //


    public void printOrders() {
        orders.forEach(System.out::println);
    }

    public List<Order> getTopNExpensiveOrders(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> getTopNCheapestOrders(int n) {
        return orders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<Order> getHomeDeliveryOrders() {
        return orders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

    public void printMinMaxHomeOrders() {
        orders.stream()
                .filter(Order::isHomeDelivery)
                .max(Comparator.comparingDouble(Order::getTotal))
                .ifPresent(o -> System.out.println("Самый прибыльный заказ на дом: " + o));

        orders.stream()
                .filter(Order::isHomeDelivery)
                .min(Comparator.comparingDouble(Order::getTotal))
                .ifPresent(o -> System.out.println("Наименее прибыльный заказ на дом: " + o));
    }

    public List<Order> getOrdersInPriceRange(double min, double max) {
        return orders.stream()
                .filter(o -> o.getTotal() > min && o.getTotal() < max)
                .collect(Collectors.toList());
    }

    public double getTotalCostOfAllOrders() {
        return orders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public Set<String> getUniqueSortedEmails() {
        return orders.stream()
                .map(o -> o.getCustomer().getEmail())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public Map<String, List<Order>> getOrdersByCustomerName() {
        return orders.stream()
                .collect(Collectors.groupingBy(order -> order.getCustomer().getFullName()));
    }

    public Map<String, Double> getTotalSumByCustomerName() {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getCustomer().getFullName(),
                        Collectors.summingDouble(Order::getTotal)
                ));
    }

    public String getCustomerWithMaxSum() {
        return getTotalSumByCustomerName().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет данных");
    }

    public String getCustomerWithMinSum() {
        return getTotalSumByCustomerName().entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет данных");
    }

    public Map<String, Long> getItemsCount() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(
                        Item::getName,
                        Collectors.counting()
                ));
    }


}
