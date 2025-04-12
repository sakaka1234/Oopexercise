package streamExer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Bai3 {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            FileWriter writer =new FileWriter("output3.txt");
            System.out.println("nhập nội dung hoặc exit để thoát :");
            String line;
            int cnt = 0 ;
            while (!(line = reader.readLine()).equalsIgnoreCase("exit")){
                writer.write(line+"\n");
                cnt++;
                writer.flush();
            }
            System.out.println("Số dòng hiện có là: " + cnt);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
