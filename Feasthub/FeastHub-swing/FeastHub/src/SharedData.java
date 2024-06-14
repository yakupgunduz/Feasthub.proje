import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class SharedData { // Bu Singleton sınıfıdır bir nesne oluşturulur ve bu nesne ihtiyaca göre her sınıfta döndürülür
    private static SharedData instance;
    private double totalPrice;
    private HashMap<String,Double> addedProductMap;
    private String username;
    private String nameSurname;
    private String address;
    private String telno;


    private SharedData() {
        this.totalPrice = 0.0;
        this.addedProductMap = new HashMap<String,Double>();
        this.username = "";
    }

    public static SharedData getInstance() { // Nesneyi Cagirma Metodu
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public double getTotalPrice() { // Toplam Fiyatı Cagirma Metodu
        return totalPrice;
    }

    public void addToTotalPrice(double amount) { // Urun Eklenirken Kullanilacak
        this.totalPrice += amount;
    }

    public void setTotalPrice(double totalPrice) { // Fiyat Duzenlemeleri İndirimler İcin Kullanilacak
        this.totalPrice = totalPrice;
    }

    public void addProduct(String itemName,double price){
        int i = 1;
        if(addedProductMap.containsKey(itemName)){
            // Eğer listede aynı ürün varsa fiyatını güncelle
            addedProductMap.put(itemName,addedProductMap.get(itemName)+price);
        }
        else{
            addedProductMap.put(itemName,price);
        }
    }

    public HashMap<String, Double> getAddedProductMap() { // Ürün Listesini Çağırma Metodu
        return addedProductMap;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getNameSurname(){
        return nameSurname;
    }

    public void setNameSurname(String nameSurname){
        this.nameSurname = nameSurname;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getTelno(){
        return telno;
    }

    public void setTelno(String telno){
        this.telno = telno;
    }

    public void populateSiparisTextArea(JTextArea siparisTextArea) {
        for (Map.Entry<String, Double> entry : addedProductMap.entrySet()) {
            siparisTextArea.append(entry.getKey() + ": " + entry.getValue() + "₺\n");
        }
    }

}
