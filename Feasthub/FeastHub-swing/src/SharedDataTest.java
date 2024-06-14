public class SharedDataTest {

    public static void main(String[] args) {
        SharedData sharedData1 = SharedData.getInstance();
        SharedData sharedData2 = SharedData.getInstance();
        if (sharedData1 == sharedData2) {
            System.out.println("Singleton başarılı bir şekilde oluşturuldu.");
        } else {
            System.out.println("Singleton başarısız oldu!");
        }
    }
}