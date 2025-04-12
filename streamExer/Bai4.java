package streamExer;

import java.io.*;

public class Bai4 {
    public static void main(String[] args) {
        String fileName  = "numbers.dat";
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))){
            int[] nums= {10,20,30,40,50};
            for(int num : nums){
                dos.writeInt(num);

            }
            System.out.println("Ghi dữ liệu thành công vào " + fileName);
        }catch (IOException e){
            e.printStackTrace();
        }
        //Đọc file nhị phân
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            while (dis.available() > 0) {
                System.out.println(dis.readInt()); // Đọc đúng kiểu int
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
