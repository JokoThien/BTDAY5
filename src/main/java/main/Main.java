package main;

import config.JPAConfig;
import entity.OrderDetailsEntity;
import entity.OrderEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.OrderDetailsRepository;
import repository.OrderRepositoty;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    static ApplicationContext context = new AnnotationConfigApplicationContext(JPAConfig.class);
    static OrderRepositoty orderRepositoty = context.getBean("orderRepositoty", OrderRepositoty.class);
    static OrderDetailsRepository orderDetailsRepository = context.getBean("orderDetailsRepository", OrderDetailsRepository.class);
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        int n;
        boolean check = false;
        do {
            System.out.println("1. create new order in database.");
            System.out.println("2. create new orderDetails in database.");
            System.out.println("3. list all Order and OrderDetails");
            System.out.println("4. Search Order by id.");
            System.out.println("5. List order current month  .");
            System.out.println("6. List order more than 1,000USD.");
            System.out.println("7. List order by name.");
            System.out.println("8. exit.");
            System.out.print(" input : ");
            Scanner sf = new Scanner(System.in);
            switch (n = sf.nextInt()) {
                case 1:
                    createNewOrder();
                    break;
                case 2:
                    createNewOrderDetail();
                    break;
                case 3:
                    listOrderAndOderDetails();
                    break;
                case 4:

                    break;
                case 5:
                    showCurrentMonth();
                    break;
                case 6:
                    showListOrderMore1000();
                    break;
                case 7:
                    ShowListAllOrderByProductName();
                    break;
                case 8:
                    check = true;
                    break;
            }
        } while (!check);

    }
    public static OrderEntity insertNewOrder() {
        Scanner sc = new Scanner(System.in);
        OrderEntity orders = new OrderEntity();
        System.out.println("Customer Name: ");
        orders.setCustomerName(sc.nextLine());
        System.out.println("Customer Address: ");
        orders.setCustomerAddress(sc.nextLine());
        orders.setOrderDate(LocalDate.now());
        orderRepositoty.save(orders);
        return orders;
    }

    public static OrderDetailsEntity insertNewOrderDetail() {
        Scanner sc = new Scanner(System.in);
        OrderDetailsEntity orderDetails = new OrderDetailsEntity();
        System.out.println("ProductName: ");
        orderDetails.setProductName(sc.nextLine());
        System.out.println("Unit Price: ");
        orderDetails.setUnitPrice(sc.nextInt());
        System.out.println("Quantity: ");
        orderDetails.setQuantity(sc.nextInt());
        return orderDetails;
    }

    public static void createNewOrder() {
        OrderEntity orders = insertNewOrder();
        orderRepositoty.save(orders);
        System.out.println("NewOrder");
    }

    public static void createNewOrderDetail() {
        Scanner sc = new Scanner(System.in);
        OrderDetailsEntity orderDetails = insertNewOrderDetail();
        System.out.println("Id order = ");
        Optional<OrderEntity> order = orderRepositoty.findById(sc.nextInt());
        orderDetails.setOrders(order.get());
        orderDetailsRepository.save(orderDetails);
        System.out.println("NewOrderDetail");
    }
    public static void  listOrderAndOderDetails() {
        List<OrderEntity> orderEntities = (List<OrderEntity>) orderRepositoty.findAll();
        for (OrderEntity ordersEntity : orderEntities) {
            System.out.println(" all orderDetails" + ordersEntity.toString());
            List<OrderDetailsEntity> orderDetailsEntities = orderDetailsRepository.getOrderDetailByOrderId(ordersEntity.getId());
            for (OrderDetailsEntity orderDetailsEntity : orderDetailsEntities) {
                System.out.println(" all order" + orderDetailsEntity.toString());
            }
        }
    }

    public static void showCurrentMonth() {
        List<OrderEntity> ordersList = orderRepositoty.ListAllOrderMonth();
        for (OrderEntity orders : ordersList)
            System.out.println(orders.toString());
        ;
    }
    public static void showListOrderMore1000() {
        List<OrderEntity> ordersList = orderRepositoty.ListAllOrderMore1000();
        for (OrderEntity orders : ordersList)
            System.out.println(orders.toString());
        ;
    }
    public static void ShowListAllOrderByProductName() {
        List<OrderEntity> ordersList = orderRepositoty.ListAllOrderByProductName();
        for (OrderEntity orders : ordersList)
            System.out.println(orders.toString());
        ;
    }



}
