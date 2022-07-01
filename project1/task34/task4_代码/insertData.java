import java.io.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class insertData {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        String[] contract = new String[5000];
        int i = 0;
        String[] model = new String[961];
        int j = 0;
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("contract_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                contract[i++] = line.split(",")[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("model_filtered.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                model[j++] = line.split(",")[0];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = new HashSet<>();//存储contract,model  现有set
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("order.csv"))) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] arr = line.split(",");
                StringBuilder sb = new StringBuilder();
                sb.append(arr[1]).append(",").append(arr[2]);
                set.add(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileWriter writer=new FileWriter("insertData.csv");
        int cnt = 50001;
        for (int k = 0; k < contract.length; k++) {
            int count = 0;
            for (int l = 0; l < model.length; l++) {
                StringBuilder sb = new StringBuilder();
                sb.append(contract[k]).append(",").append(model[l]);
                if (!set.contains(sb.toString())){
                    writer.append(Integer.toString(cnt)).append(",")
                            .append(sb).append(",")
                            .append("2022-04-13").append(",")//est_date
                            .append("2022-04-17").append(",")// log_date
                            .append(Integer.toString(random.nextInt(901)+100)).append(",")// quantity
                            .append(Integer.toString(random.nextInt(990)+1)).append("\n");// salesmanID:1-990
                    cnt++;
                    count++;
                    if (count >=10){
                        break;
                    }
                }
            }
        }
    }

}
