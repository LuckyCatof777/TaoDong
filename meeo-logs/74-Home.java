package td;

import java.sql.SQLException;
import java.util.*;

public class Home {
    public static void main(String[] args) throws SQLException {

        Conn con = new Conn();
        con.conn();

        List<List<String>> trainData = con.getTrainData();
        List<List<String>> testData = con.getTestData();
        int k = 1;
        Knn test = new Knn();
        test.knn(trainData, testData, k);

        Pro.initializeProductDatabase();
        list();
    }

    public static void list() throws SQLException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== �Զ�����ƽ̨��ҳ =====");
            System.out.println("1. ��Ʒ�б�");
            System.out.println("2. ���ﳵ");
            System.out.println("3. ��������");
            System.out.println("4. �������Ƽ�");
            System.out.println("0. �˳�");

            System.out.print("��ѡ��ģ�飨�����Ӧ�����֣���");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // ��Ʒģ��
                    System.out.println("������Ʒģ��");
                    ProManage.home();
                case 2:
                    // ���ﳵģ��
                    System.out.println("���빺�ﳵģ��");
                    ShoppingCart.home();
                case 3:
                    // ��������ģ��
                    System.out.println("���붩������ģ��");
                    OrderManage.home();
                case 4:
                    // �������Ƽ�ģ��
                    System.out.println("�����������Ƽ�ģ��");
                    Search.home();
                case 0:
                    // �˳�����
                    System.out.println("��лʹ�ã��ټ���");
                    System.exit(0);
                    break;
                default:
                    System.out.println("��Ч��ѡ�����������롣");
            }
        }
    }

}
