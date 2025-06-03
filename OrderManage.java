package td;

import java.sql.SQLException;
import java.util.*;

import static td.Pro.productDatabase;

public class OrderManage {
    static Map<Integer, Integer> orderDatabase = new HashMap<>();

    public static void home() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== ��������ģ�� =====");
            System.out.println("1. �鿴���ж���");
            System.out.println("2. ɾ������");
            System.out.println("0. ������ҳ");

            System.out.print("��ѡ������������Ӧ�����֣���");
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
                    System.out.println("��Ч��ѡ�����������롣");
            }
        }
    }

    private static void displayAllOrders() {
        System.out.println("===== ���ж����б� =====");
        if(!orderDatabase.isEmpty())
        for (Map.Entry<Integer, Integer> entry : orderDatabase.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Pro product = getProductById(productId);
            double subtotal = quantity * product.getPrice();

            System.out.println("��ƷID: " + productId +
                    ", ��Ʒ����: " + product.getName() +
                    ", ����: " + product.getPrice() +
                    ", ����: " + quantity +
                    ", С��: " + subtotal);
        }
        else{
            System.out.println("������տ���Ҳ����ȥ�������ǵ���Ʒ��~");
        }
    }

    private static void sortProductDatabase() {
        Collections.sort(productDatabase, Comparator.comparingInt(Pro::getId));
    }

    private static Pro getProductById(int productId) {
        // ȷ����Ʒ���ݿⰴ��ID����
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
        System.out.print("������Ҫɾ���Ķ���ID: ");
        int orderId = scanner.nextInt();

        if (orderDatabase.containsKey(orderId)) {
            orderDatabase.remove(orderId);
            System.out.println("����ɾ���ɹ���");
        } else {
            System.out.println("���������ڣ�ɾ��ʧ�ܡ�");
        }
    }
}