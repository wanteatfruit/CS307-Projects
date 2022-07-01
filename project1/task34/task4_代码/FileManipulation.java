import bplustree.Bplustree;

import java.io.*;
import java.util.*;

public class FileManipulation implements DataManipulation {

    @Override
    public void openDatasource() {

    }

    @Override
    public void closeDatasource() {

    }

    @Override
    public String allOrders() {
        String line;
        String[] arr;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                for (int i = 0; i < arr.length; i++) {
                    sb.append(arr[i]).append("\t");
                }
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    @Override
    public String findOrdersByModel(String model) {//like?
        String line;
        String[] arr;
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                char start = model.charAt(0);
                char end = model.charAt(model.length() - 1);
                boolean test = false;
                if (start == '%' && end == '%') {//%mp%
                    if (arr[2].contains(model.substring(1, model.length() - 1))) {
                        test = true;
                    }
                } else if (start == '%' && end == '_') {//%mp_
                    int a = arr[2].length() - 1 - model.length();
                    if (a >= 0) {
                        String str = arr[2].substring(a);
                        if (str.substring(0, str.length() - 1).equals(model.substring(1, model.length() - 1))) {//包含
                            test = true;
                        }
                    }
                } else if (start == '%') {//%mp
                    if (arr[2].endsWith(model.substring(1))) {
                        test = true;
                    }
                } else if (start == '_' && end == '%') {//_mp%
                    if (arr[2].startsWith(model.substring(1, model.length() - 1), 1)) {
                        test = true;
                    }
                } else if (start == '_' && end == '_') {//_mp_
                    if (arr[2].substring(1, arr[2].length() - 1).equals(model.substring(1, model.length() - 1)) && model.length() == arr[2].length()) {
                        test = true;
                    }
                } else if (start == '_') {//_mp
                    if (arr[2].endsWith(model.substring(1)) && model.length() == arr[2].length()) {
                        test = true;
                    }
                } else if (end == '%') {//mp%
                    if (arr[2].startsWith(model.substring(0, model.length() - 1))) {
                        test = true;
                    }
                } else if (end == '_') {
                    if (arr[2].startsWith(model.substring(0, model.length() - 1)) && arr[2].length() == model.length()) {
                        test = true;
                    }
                } else {//mp
                    if (arr[2].equals(model)) {
                        test = true;
                    }
                }
                if (test) {
                    cnt++;
                    for (int i = 0; i < arr.length; i++) {
                        sb.append(arr[i]).append("\t");
                    }
                    sb.append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(cnt);
        return sb.toString();
    }


    @Override
    public String findOrdersByQuantity(int min, int max) {
        String line;
        String[] arr;
        int quantity;
        int cnt = 0;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                quantity = Integer.parseInt(arr[5]);
                if (quantity >= min && quantity <= max) {
                    cnt++;
                    sb.append(arr[1]).append("\t").append(arr[2]).append("\t").append(arr[5]).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(cnt);
        return sb.toString();
    }

    @Override
    public int countAllContracts() {//可以输出每个的订单数
        String line;
        int index = 1;
        Map<String, Integer> contractCount = new HashMap<>();
//        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                line = line.split(",")[index];
                if (contractCount.containsKey(line)) {
                    contractCount.put(line, contractCount.get(line) + 1);
                } else contractCount.put(line, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contractCount.size();
    }

    @Override
    public String maxQuantityContract() {
        String line;
        int quantity;
        Map<String, Integer> contractCount = new HashMap<>();
        ArrayList<String> compare = new ArrayList<>();//用来升序输出
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                quantity = Integer.parseInt(line.split(",")[5]);
                line = line.split(",")[1];

                if (contractCount.containsKey(line)) {
                    if (contractCount.get(line) < quantity) {
                        contractCount.put(line, quantity);
                    }
                } else {
                    contractCount.put(line, quantity);
                    compare.add(line);
                }
            }
            Collections.sort(compare);
            for (int i = 0; i < compare.size(); i++) {
                String str = compare.get(i);
                sb.append(contractCount.get(str)).append("\t").append(str).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String countOrdersByModel(String model) {
        String line;
        int cnt = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                line = line.split(",")[2];
                if (line.equals(model)) {
                    cnt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.toString(cnt);
    }

    @Override
    public String mostOrdersContract() {
        String line;
        Map<String, Integer> contractCount = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                line = line.split(",")[1];
                if (contractCount.containsKey(line)) {
                    contractCount.put(line, contractCount.get(line) + 1);
                } else contractCount.put(line, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size = 0;
        String max = "";
        for (Map.Entry<String, Integer> entry :
                contractCount.entrySet()) {
            if (size < entry.getValue()) {
                size = entry.getValue();
                max = entry.getKey();
            }
        }
        return sb.append(size).append("\t").append(max).toString();
    }

    public Map<String, String[]> getIDtoNameAndNumber() {
        String line;//0->id;2->name
        Map<String, String[]> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("salesman_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                if (!map.containsKey(temp[0])) {
                    map.put(temp[0], new String[]{temp[1], temp[2]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String countSalesmanOrders() {
        Map<String, String[]> nameMap = getIDtoNameAndNumber();
        Map<String, Integer> count = new HashMap<>();//id次数统计,下标为6
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                if (count.containsKey(temp[6])) {
                    count.put(temp[6], count.get(temp[6]) + 1);
                } else count.put(temp[6], 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map.Entry<String, Integer>> compare = new ArrayList<>(count.entrySet());
        Collections.sort(compare, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : compare) {
            String[] temp = nameMap.get(entry.getKey());
            sb.append(temp[0]).append("\t").append(String.format("%-20s", temp[1])).append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    public Map<String, Integer> getModelPrice() {
        String line;
        Map<String, Integer> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("model_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                map.put(temp[0], Integer.parseInt(temp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String findOrdersByPrice(int min, int max) {
        String line;
        //1 contract_number 2->product_model; 5-> quantity
        Map<String, Integer> price = getModelPrice();//model -- price
        List<String[]> info = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                if (price.get(temp[2]) >= min && price.get(temp[2]) <= max) {
                    info.add(new String[]{temp[1], temp[2], temp[5]});
                }
            }
            Collections.sort(info, new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    return o2[0].compareTo(o1[0]);
                }
            });
            for (String[] str : info
            ) {
                sb.append(str[0]).append("\t").append(str[2]).append("\t").append(str[1]).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public Map<String, String> getClientID() {
        String line;
        Map<String, String> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("contract_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                map.put(temp[1], temp[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Map<String, String[]> getClientIndustry() {
        String line;
        Map<String, String[]> map = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("client_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                map.put(temp[0], new String[]{temp[1], temp[5]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String findContractClientIndustry(String industry) {
        Map<String, String> clientID = getClientID();
        Map<String, String[]> enterprise = getClientIndustry();
        String line;
        StringBuilder sb = new StringBuilder();
//        int cnt = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                String[] info = enterprise.get(clientID.get(temp[1]));
                if (info[1].equals(industry)) {
                    sb.append(temp[1]).append("\t").append(temp[2]).append("\t").append(info[0]).append("\n");
//                    cnt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(cnt);
        return sb.toString();
    }

    @Override
    public void addOneOrder(String str) {
        try (FileWriter writer = new FileWriter("order.csv", true)) {
            writer.write(str);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderByID(int id) {
        String line;
        String[] temp;
        ArrayList<String> text = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                temp = line.split(",");
                if (Integer.parseInt(temp[0]) != id) {
                    text.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter("order.csv")) {
            for (int i = 0; i < text.size(); i++) {
                writer.write(text.get(i));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateQuantityByID(int quantity, int id) {
        String line;
        String[] temp;
        ArrayList<String> text = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                temp = line.split(",");
                if (Integer.parseInt(temp[0]) == id) {
                    flag = true;
                    temp[5] = Integer.toString(quantity);
                    for (int i = 0; i < temp.length; i++) {
                        if (i == temp.length - 1) {
                            sb.append(temp[i]);
                        } else sb.append(temp[i]).append(",");
                    }
                    text.add(sb.toString());
                } else {
                    text.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (flag) {
            try (FileWriter writer = new FileWriter("order.csv")) {
                for (int i = 0; i < text.size(); i++) {
                    writer.write(text.get(i));
                    writer.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public String findOrderByID(int id) {
        String line;
        String[] arr;
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                arr = line.split(",");
                if (Integer.parseInt(arr[0]) == id) {
                    for (int i = 0; i < arr.length; i++) {

                        sb.append(arr[i]).append("\t");
                        found = true;

                    }
                }
                if (found) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public String findRangeOrderByID(int min, int max) {
        return null;
    }

    public Bplustree setOrderIndex() { //create index on a specific column
        Bplustree tree = new Bplustree();
        tree.maxD = 10000;
        ArrayList<String> list = new ArrayList<>();
        String line;
//        String[] arr;
        int cnt = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] arr = line.split(",");
                String id = arr[0];
                String model = arr[2];
                tree.insert(Integer.parseInt(id), line);
                //list.add(model);
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tree;
    }

    public Bplustree setModelIndex(){
        Bplustree tree = new Bplustree();
        tree.maxD = 10000;
        HashMap<String,Integer> model_map=new HashMap<>();
        HashMap<Integer,String> toInsert=new HashMap<>();
        String line;
//        String[] arr;
        int cnt = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("model_filtered.csv"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] arr = line.split(",");
                String model = arr[0];
                model_map.put(model,cnt);
                toInsert.put(cnt,"");
                cnt++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"));
            while ((line = bufferedReader.readLine()) != null) {

                String[] arr = line.split(",");
                String id=arr[0];
                String model = arr[2];
                int key=model_map.get(model);
                String cur=toInsert.get(key);
                cur=cur+line+";";
                toInsert.replace(key,cur);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int key:toInsert.keySet()){
            tree.insert(key,toInsert.get(key));
        }

        return tree;

    }

    public String findOrderByModelIndex(String model,Bplustree tree,HashMap<String,Integer> modelMap){
        String s= (String) tree.searchValue(modelMap.get(model));
        StringBuilder sb = new StringBuilder();
        String[] arr = s.split(";");
        for (int i = 0; i < arr.length; i++) {
            String[] order=arr[i].split(",");
            for (int j = 0; j < order.length; j++) {
                sb.append(order[j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String findRangeOrderByID(int min,int max,Bplustree tree){
        ArrayList<String> s = (ArrayList<String>) tree.rangeSearch(min,max);
        StringBuilder sb = new StringBuilder();
        String[] arr=new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            arr[i]=s.get(i);
            String[] order=arr[i].split(",");
            for (int j = 0; j < order.length; j++) {
                sb.append(order[j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();

    }

    public String findOrderByIndex(int id, Bplustree tree) { //using index to find order
        String s = (String) tree.searchValue(id);
        StringBuilder sb = new StringBuilder();
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]).append("\t");
        }
        return sb.toString();
    }

    public String updateOrderQuantityByIndex(int id, int quantity, Bplustree tree){
        String s= (String) tree.searchValue(id);
        String[] arr1=s.split(",");
        arr1[5]= String.valueOf(quantity);
        String newString=Arrays.toString(arr1);
        String res= (String) tree.updateValue(id,newString);
        StringBuilder sb = new StringBuilder();
        String[] arr = s.split(",");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]).append("\t");
        }
        return sb.toString();
    }


}

