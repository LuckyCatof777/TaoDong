package td;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

public class Knn {
    /* knn 方法
     */
    public void knn(List<List<String>> trainData, List<List<String>> testData, int k) throws SQLException {

        int i = 0;
        LinkedList<Product> kData = new LinkedList<Product>();
        for (List<String> record : testData) {
            i = 0;
            kData.clear();
            for (List<String> dat : trainData) {                    //先计算某一条测试数据同训练的前k条数据的距离判断
                if (i++ >= k) break;
                Product p = new Product();
                p.setName(dat.get(0));
                p.setType(dat.get(dat.size() - 1));
                p.setDistance(calDistance(record, dat));
                kData.add(p);
            }

            int j = 0;
            for (List<String> dat : trainData) {
                if (j++ <= k) continue;
                getKData(kData, record, dat);                    //kData 是当前距离最近的k个数据的集合， record是选择的一条测试数据 ，dat 是训练数据中排除已被选择的k条数据的一条
            }
            String str = judgeClass(kData);                      //判断类型
            System.out.println("商品:" + record.get(0) + " 属于:" + str);
            Conn.updata(str,record.get(0));
        }

    }

    /* 实现维护k个最近的数据类型
     *  kData 为k随机的k个已经被训练过的数据
     *  record 是一条测试数据
     *  dat 是训练集合中除去 k个的其他一个待比较数据
     */
    public void getKData(LinkedList<Product> kData, List<String> record, List<String> dat) {
        Double Distance = 0.0;

        int local = 0;
        for (int j = 1; j < kData.size(); j++) {          //获取到kData中的最大项
            if (kData.get(local).getDistance() < kData.get(j).getDistance()) {
                local = j;
            }
        }

        int i = 0;
        Distance = calDistance(record, dat);            //计算距离
        if (Distance < kData.get(local).getDistance()) {  //测试数据与一条训练数据之间的距离 同 kData集合中的最大距离比较
            kData.remove(local);
            Product product = new Product();
            product.setName(dat.get(0));              //设置添加到kData中的名字
            product.setType(dat.get(dat.size() - 1)); //设置添加到kData中的类型
            product.setDistance(Distance);            //设置添加到kData中的距离
            kData.add(product);
        }
    }

    /*计算测试数据到集合点的距离
     */
    public Double calDistance(List<String> record, List<String> dat) {
        Double distance = 0.0;
        Double x = 0.0;
        Double y = 0.0;
        for (int i = 1, j = 1; i < record.size(); i++, j++) {  //第一项为名字
            x = Double.parseDouble(record.get(i));
            y = Double.parseDouble(dat.get(j));
            distance += (x - y) * (x - y);    //计算距离一般用欧氏距离     注：还可以使用加权欧氏距离公式计算距离更实用于实际
        }
        return distance;
    }

    /*
     * 类型判断
     */
    public String judgeClass(List<Product> kData) {
        Map<String, Integer> productClass = new HashMap<String, Integer>();
        String res = "";
        for (int i = 0; i < kData.size(); i++) {
            if (!productClass.containsKey(kData.get(i).getType())) {
                productClass.put(kData.get(i).getType(), 1);
            } else {
                productClass.put(kData.get(i).getType(), productClass.get(kData.get(i).getType()) + 1);
            }
        }
        for (Map.Entry<String, Integer> entry1 : productClass.entrySet()) {
            res = entry1.getKey();
            for (Map.Entry<String, Integer> entry2 : productClass.entrySet()) {
                if (entry1.getValue() < entry2.getValue()) {
                    res = entry2.getKey();
                }
            }
            break;
        }
        return res;
    }
}
