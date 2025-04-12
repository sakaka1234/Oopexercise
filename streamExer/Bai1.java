package streamExer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Bai1 {
    public static void main(String[] args) {
        String sourceFile = "source.txt";
        String destinationFile = "destination.txt";
        try(FileInputStream start = new FileInputStream(sourceFile);
            FileOutputStream end = new FileOutputStream(destinationFile)){
            int byteData;
            while((byteData = start.read()) != -1){
                end.write(byteData);
            }
            System.out.println("sao chép file thành công");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
