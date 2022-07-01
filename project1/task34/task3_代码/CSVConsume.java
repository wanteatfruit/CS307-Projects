import java.io.*;
import java.util.ArrayList;

public class CSVConsume {
    public static void main(String[] args) throws IOException {
        String line="";
        String splitBy=",";
        String[][] table=new String[50001][21];
        try{
            int row=0;
            BufferedReader br=new BufferedReader(new FileReader("contract_info.csv"));
            while ((line= br.readLine())!=null){
                //int col=0;
                String[] order=line.split(splitBy);
                table[row]=order;
                row++;

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

//        FileWriter writerClient=new FileWriter("client.csv");
//        FileWriter writerSales=new FileWriter("salesman.csv");
//        FileWriter writerProduct=new FileWriter("product.csv");
//        FileWriter writerModel=new FileWriter("model.csv");
        FileWriter writerContract=new FileWriter("contract.csv");
        FileWriter writerOrder=new FileWriter("order.csv");

        for (int i = 1; i < table.length; i++) { //skipped first row
            for (int j = 0; j < table[i].length; j++) {
//                if(j==1||j==2||j==3||j==4|j==5){
//                    writerClient.append(table[i][j]);
//                    writerClient.append(",");
//                }
//                if(j==19||j==18||j==17||j==16||j==15){
//                    writerSales.append(table[i][j]);
//                    writerSales.append(",");
//                }
//                if(j==6||j==7){
//                    writerProduct.append(table[i][j]);
//                    writerProduct.append(",");
//                }
//                if(j==8||j==9 || j==6){
//                    writerModel.append(table[i][j]);
//                    writerModel.append(",");
//                }
                if(j==0||j==11||j==14 || j==1){
                    writerContract.append(table[i][j]);
                    writerContract.append(",");
                }
                if(j==0||j==8||j==12||j==13|j==10||j==16){
                    writerOrder.append(table[i][j]);
                    writerOrder.append(",");
                }
            }
//            writerClient.append("\n");
            writerContract.append("\n");
            writerOrder.append("\n");
//
//            writerProduct.append("\n");
//            writerSales.append("\n");
//            writerModel.append("\n");
        }

//        writerClient.flush();
        writerContract.flush();
        writerOrder.flush();
//        writerModel.flush();
//        writerProduct.flush();
//        writerSales.flush();
        

//        for (int i = 0; i < table.length; i++) {
//            for (int j = 0; j < table[i].length; j++) {
//                System.out.print(table[i][j]);
//            }
//            System.out.println();
//        }
    }
}
