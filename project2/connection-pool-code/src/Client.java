import Util.OrdinaryUtil;
import Util.ProxoolUtil;
import dao.DataManipulation;
import dao.DatabaseManipulation;

import java.util.List;

public class Client {

    public static void main(String[] args) {

        try {
            dbRequestArrived(2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dbRequestArrived(5);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void dbRequestArrived(int count) {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                DataManipulation dm = new DatabaseManipulation(ProxoolUtil.getInstance());
                dm.getConnection();
                List<String> result = dm.selectOrder("CSE0000108");
                result.add(0,"------Thread " + Thread.currentThread().getId() + " print output------");
                for (int j = 0; j < result.size(); j++) {
                    System.out.println(result.get(j));
                }
                dm.closeConnection();
            }).start();
        }
    }
}

