import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashMap;

import bplustree.*;

public class Client {
    static double start = 0;
    static double end = 0;

    public static void main(String[] args) {
        try {
            FileManipulation fm = new FileManipulation();
            DataManipulation dm = new DatabaseManipulation();

            benchmarkDM(dm);
            //benchmarkFM(fm);
            //benchmarkFMIndex(fm);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    static void benchmarkDM(DataManipulation dm) {
        dm.openDatasource();


/* delete
        start=System.currentTimeMillis();
        for (int i = 50401; i <= 50500 ; i++) {
            dm.deleteOrderByID(i);
        }
        end=System.currentTimeMillis();
*/

/* update
        start=System.currentTimeMillis();
        for (int i = 1; i < 101; i++) {
            dm.updateQuantityByID(5000,i);
        }
        end=System.currentTimeMillis();
*/

/* insert
            try {
                String line=null;
                String[] arr;
                BufferedReader bufferedReader = new BufferedReader(new FileReader("insertData(1).csv"));
                start=System.currentTimeMillis();
                while ((line=bufferedReader.readLine())!=null){
                    dm.addOneOrder(line);
                }
                end=System.currentTimeMillis();
            }
            catch (IOException e){
                e.printStackTrace();
            }
*/

/* select
            dm.findContractClientIndustry("Internet");
            dm.findContractClientIndustry("Game");
            dm.findOrdersByPrice(400,500);
            dm.findOrdersByPrice(100,900);
            dm.countSalesmanOrders();
            dm.mostOrdersContract();
            dm.countAllContracts();
            dm.maxQuantityContract();
            dm.countOrdersByModel("Mp437");
            dm.countOrdersByModel("YubaR4");
            dm.maxQuantityContract();
            dm.allOrders();
            dm.findOrdersByQuantity(100,500);
            dm.findOrdersByQuantity(500,900);
            dm.findOrdersByModel("TvBaseR1");
            dm.findOrdersByModel("Mp4%");
            dm.findOrdersByModel("Yuba%");

            dm.findOrdersByModel("Mp437");
            dm.findOrdersByModel("YubaR4");
*/
        start=System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
           dm.findOrdersByModel("Mp437");
        }
        end=System.currentTimeMillis();

        dm.closeDatasource();
        System.out.println("Running time of DBMS is " + (end - start) + " ms");
    }

    static void benchmarkFM(FileManipulation fm) {

/* delete
        start=System.currentTimeMillis();
        for (int i = 50401; i <= 50500; i++) {
            fm.deleteOrderByID(i);
        }
        end=System.currentTimeMillis();
*/

/* update
        start=System.currentTimeMillis();
        for (int i = 1; i < 101; i++) {
            fm.updateQuantityByID(5000,i);
        }
        end=System.currentTimeMillis();
*/

/* insert
        start = System.currentTimeMillis();
        try {
            String line=null;
            String[] arr;
            BufferedReader bufferedReader = new BufferedReader(new FileReader("insertData(1).csv"));
            start=System.currentTimeMillis();
            while ((line=bufferedReader.readLine())!=null){
                fm.addOneOrder(line);
            }
            end=System.currentTimeMillis();
        }
        catch (IOException e){
            e.printStackTrace();
        }
*/


/* select
        fm.findContractClientIndustry("Internet");
        fm.findContractClientIndustry("Game");
        fm.findOrdersByPrice(400, 500);
        fm.findOrdersByPrice(100, 900);
        fm.countSalesmanOrders();
        fm.mostOrdersContract();
        fm.countOrdersByModel("Mp437");
        fm.countOrdersByModel("YubaR4");
        fm.maxQuantityContract();
        fm.mostOrdersContract();
        fm.allOrders();
        fm.findOrdersByQuantity(100, 500);
        fm.findOrdersByQuantity(500, 900);
        fm.findOrdersByModel("Mp437");
        fm.findOrdersByModel("YubaR4");
        fm.findOrdersByModel("TvBaseR1");
        fm.findOrdersByModel("Mp4%");
        fm.findOrdersByModel("Yuba%");
        fm.maxQuantityContract();
*/
        start=System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            fm.findOrdersByModel("Mp437");
        }
        end=System.currentTimeMillis();


        System.out.println("Running time of file I/O is " + (end - start) + " ms");
    }

    static void benchmarkFMIndex(FileManipulation fm) {
//        String line;
//        int cnt = 1;
//        HashMap<String,Integer> model_map=new HashMap<>();
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("model_filtered.csv"));
//            while ((line = bufferedReader.readLine()) != null) {
//
//                String[] arr = line.split(",");
//                String model = arr[0];
//                model_map.put(model,cnt);
//                cnt++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        start = System.currentTimeMillis();
        Bplustree tree = fm.setOrderIndex(); //create index
        for (int i = 0; i < 100; i++) {
            fm.findRangeOrderByID(25000,30000,tree);
        }
        end = System.currentTimeMillis();
        System.out.println("Running time of file I/O with index is " + (end - start) + " ms");
    }

}

