package td;

import java.sql.SQLException;
import java.util.*;

import static td.ShoppingCart.shoppingCart;
import static td.Pro.productDatabase;
public class ProManage {

    public static void home() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== ��Ʒ����ģ�� =====");
            System.out.println("1. �鿴������Ʒ");
            System.out.println("0. ������ҳ");
            System.out.print("��ѡ������������Ӧ�����֣���");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displayAllProducts(shoppingCart, productDatabase);
                    break;
                case 0:
                    Home.list();
                    return;
                default:
                    System.out.println("��Ч��ѡ�����������롣");
            }
        }
    }

    static void displayAllProducts(Map<Integer, Integer> shoppingCart, List<Pro> productDatabase) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== ������Ʒ�б� =====");
        List<List<String>> data = Conn.getData();
        for (List<String> record : data) {
            System.out.println("ID:" + record.get(record.size()-1) + "\t���ƣ�" + record.get(0) + "\t���ͣ�" + record.get(record.size()-2) + "\t���ۣ�" + record.get(1) + "\t�������" + record.get(2));
        }
        while (true) {
            System.out.println("1. �����Ʒ�빺�ﳵ");
            System.out.println("0. ������ҳ");
            System.out.print("��ѡ������������Ӧ�����֣���");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addToShoppingCart(shoppingCart, productDatabase);
                    break;
                case 0:
                    Home.list();
                    return;
                default:
                    System.out.println("��Ч��ѡ�����������롣");
            }
        }
    }

    private static void addToShoppingCart(Map<Integer, Integer> shoppingCart, List<Pro> productDatabase) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("������Ҫ��ӵ����ﳵ����ƷID��");
        int productId = scanner.nextInt();

        Pro product = getProductById(productId, productDatabase);

        if (product != null) {
            System.out.print("������Ҫ�����������");
            int quantity = scanner.nextInt();

            if (quantity > 0 && quantity <= product.getQuantity()) {
                // ��鹺�ﳵ���Ƿ��Ѵ��ڸ���Ʒ
                if (shoppingCart.containsKey(productId)) {
                    // ������ڣ��ۻ�����
                    int currentQuantity = shoppingCart.get(productId);
                    shoppingCart.put(productId, currentQuantity + quantity);
                } else {
                    // ��������ڣ�����Ʒ��ӵ����ﳵ
                    shoppingCart.put(productId, quantity);
                }

                System.out.println("��Ʒ����ӵ����ﳵ��");
            } else {
                System.out.println("��Ч�����������������롣");
            }
        } else {
            System.out.println("δ�ҵ�����ƷID���������롣");
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
