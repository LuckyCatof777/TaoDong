package td;

import java.sql.SQLException;
import java.util.*;

import static td.ShoppingCart.shoppingCart;
import static td.Pro.productDatabase;
public class ProManage {

    public static void home() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== 商品管理模块 =====");
            System.out.println("1. 查看所有商品");
            System.out.println("0. 返回主页");
            System.out.print("请选择操作（输入对应的数字）：");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllProducts(shoppingCart, productDatabase);
                    break;
                case 0:
                    Home.list();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    static void displayAllProducts(Map<Integer, Integer> shoppingCart, List<Pro> productDatabase) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== 所有商品列表 =====");
        List<List<String>> data = Conn.getData();
        for (List<String> record : data) {
            System.out.println("ID:" + record.get(record.size()-1) + "\t名称：" + record.get(0) + "\t类型：" + record.get(record.size()-2) + "\t单价：" + record.get(1) + "\t库存量：" + record.get(2));
        }
        while (true) {
            System.out.println("1. 添加商品入购物车");
            System.out.println("0. 返回主页");
            System.out.print("请选择操作（输入对应的数字）：");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addToShoppingCart(shoppingCart, productDatabase);
                    break;
                case 0:
                    Home.list();
                    return;
                default:
                    System.out.println("无效的选择，请重新输入。");
            }
        }
    }

    private static void addToShoppingCart(Map<Integer, Integer> shoppingCart, List<Pro> productDatabase) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入要添加到购物车的商品ID：");
        int productId = scanner.nextInt();

        Pro product = getProductById(productId, productDatabase);

        if (product != null) {
            System.out.print("请输入要购买的数量：");
            int quantity = scanner.nextInt();

            if (quantity > 0 && quantity <= product.getQuantity()) {
                // 检查购物车中是否已存在该商品
                if (shoppingCart.containsKey(productId)) {
                    // 如果存在，累积数量
                    int currentQuantity = shoppingCart.get(productId);
                    shoppingCart.put(productId, currentQuantity + quantity);
                } else {
                    // 如果不存在，将商品添加到购物车
                    shoppingCart.put(productId, quantity);
                }

                System.out.println("商品已添加到购物车！");
            } else {
                System.out.println("无效的数量，请重新输入。");
            }
        } else {
            System.out.println("未找到该商品ID，请检查输入。");
        }
    }


    public static Pro getProductById(int productId, List<Pro> productDatabase) {
        Queue<Pro> queue = new LinkedList<>();

        for (Pro product : productDatabase) {
            queue.offer(product);
        }

        while (!queue.isEmpty()) {
            Pro currentProduct = queue.poll();

            if (currentProduct.getId() == productId) {
                return currentProduct;
            }
        }

        return null;
    }
}
