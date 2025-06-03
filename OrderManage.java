package td;

import java.sql.SQLException;
import java.util.*;

import static td.Pro.productDatabase;

public class OrderManage {
    static Map<Integer, Integer> orderDatabase = new HashMap<>();

    public static void home() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== 订单管理模块 =====");
            System.out.println("1. 查看所有订单");
            System.out.println("2. 删除订单");
            System.out.println("0. 返回主页");

            System.out.print("请选择操作（输入对应的数字）：");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllOrders();
                    break;
                case 2:
                    deleteOrder();
                    break;
                case 0:
                    Home.list();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private static void displayAllOrders() {
        System.out.println("===== 所有订单列表 =====");
        if(!orderDatabase.isEmpty())
        for (Map.Entry<Integer, Integer> entry : orderDatabase.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Pro product = getProductById(productId);
            double subtotal = quantity * product.getPrice();

            System.out.println("商品ID: " + productId +
                    ", 商品名称: " + product.getName() +
                    ", 单价: " + product.getPrice() +
                    ", 数量: " + quantity +
                    ", 小计: " + subtotal);
        }
        else{
            System.out.println("订单里空空如也，快去购买心仪的商品吧~");
        }
    }

    private static void sortProductDatabase() {
        Collections.sort(productDatabase, Comparator.comparingInt(Pro::getId));
    }

    private static Pro getProductById(int productId) {
        // 确保产品数据库按照ID排序
        sortProductDatabase();

        int left = 0;
        int right = productDatabase.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Pro currentProduct = productDatabase.get(mid);

            if (currentProduct.getId() == productId) {
                return currentProduct;
            } else if (currentProduct.getId() < productId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    private static void deleteOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入要删除的订单ID: ");
        int orderId = scanner.nextInt();

        if (orderDatabase.containsKey(orderId)) {
            orderDatabase.remove(orderId);
            System.out.println("订单删除成功。");
        } else {
            System.out.println("订单不存在，删除失败。");
        }
    }
}