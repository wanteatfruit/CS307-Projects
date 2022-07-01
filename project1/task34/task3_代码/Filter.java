import java.io.*;
import java.util.*;

public class Filter {
    public static void main(String[] args) throws IOException {

        ArrayList<String[]> list=new ArrayList<>();
        BufferedReader bufferedReader=new BufferedReader(new FileReader("contract.csv"));
        String line="";
        String split=",";

        while ((line= bufferedReader.readLine())!=null){
            String[] l=line.split(split);
            list.add(l);
        }


        ArrayList<String[]> res=new ArrayList<>();
        res.add(list.get(0));
        boolean add=true;


        for (int i = 1; i < list.size(); i++) {
            add=true;
            String [] cur=list.get(i);
            for (int j = 0; j < res.size(); j++) {
                if(Objects.equals(res.get(j)[0], cur[0])){
                    add=false;
                    break;
                }
            }
            if(add){
                res.add(cur);

            }
        }

        FileWriter writerClient=new FileWriter("contract_filtered.csv");

        for (String[] cur : res) {
            for (String s : cur) {
                writerClient.append(s);
                writerClient.append(",");
            }
            writerClient.append("\n");
        }

        writerClient.flush();

//        for (int i = 0; i < res.size(); i++) {
//            System.out.println(Arrays.toString(res.get(i)));
//        }
    }
}
