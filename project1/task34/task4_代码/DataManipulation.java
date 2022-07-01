public interface DataManipulation {

    public void openDatasource();
    public void closeDatasource();

    //select
    public String allOrders();
    public String findOrdersByModel(String model);
    public String findOrdersByQuantity(int min, int max);
    public int countAllContracts();
    public String maxQuantityContract();
    public String countOrdersByModel(String model);
    public String mostOrdersContract();
    public String countSalesmanOrders();
    public String findOrdersByPrice(int min,int max);
    public String findContractClientIndustry(String industry);



    //insert
    public void addOneOrder(String str);

    //delete
    public void deleteOrderByID(int id);

    //update
    public void updateQuantityByID(int quantity,int id);


    String findOrderByID(int i);
    String findRangeOrderByID(int min,int max);
}
