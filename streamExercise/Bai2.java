package streamExercise;

import java.io.*;

public class Bai2 {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            FileWriter writer = new FileWriter("output2.txt");
            System.out.println("nhập nội dung hoặc gõ exit để thoát");
            String line;
            while(!(line = reader.readLine()).equalsIgnoreCase("exit")){
                writer.write(line+"\n");

                writer.flush();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
