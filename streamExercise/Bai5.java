package streamExercise;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Bai5 {
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB
    private static final String OUTPUT_FILE = "output.txt";
    private static final Object lock = new Object(); // Đồng bộ hóa ghi file

    public static void main(String[] args) {
        // Danh sách file đầu vào
        String[] inputFiles = {"input1.txt", "input2.txt"}; // Thêm file nếu cần
        String keyword = "Java"; // Từ khóa cần tìm

        // Tạo ExecutorService để quản lý các luồng
        ExecutorService executor = Executors.newFixedThreadPool(inputFiles.length);
        List<Future<Integer>> futures = new ArrayList<>();

        // Xóa file output nếu đã tồn tại
        new File(OUTPUT_FILE).delete();

        // Xử lý từng file trong một luồng riêng
        for (String inputFile : inputFiles) {
            Callable<Integer> task = () -> processFile(inputFile, keyword);
            futures.add(executor.submit(task));
        }

        // Đợi tất cả luồng hoàn thành và lấy kết quả
        int totalOccurrences = 0;
        for (Future<Integer> future : futures) {
            try {
                totalOccurrences += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Tắt ExecutorService
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Tổng số lần xuất hiện của từ khóa '" + keyword + "': " + totalOccurrences);
    }

    private static int processFile(String inputFile, String keyword) {
        int keywordCount = 0;
        try (RandomAccessFile raf = new RandomAccessFile(inputFile, "r")) {
            long fileLength = raf.length();
            long position = 0;

            while (position < fileLength) {
                raf.seek(position);
                byte[] buffer = new byte[(int) Math.min(CHUNK_SIZE, fileLength - position)];
                int bytesRead = raf.read(buffer);
                if (bytesRead == -1) break;

                // Chuyển buffer thành String để xử lý
                String chunk = new String(buffer, 0, bytesRead);

                // Đếm số lần xuất hiện của từ khóa trong đoạn
                keywordCount += countOccurrences(chunk, keyword);

                // Ghi đoạn dữ liệu vào file output (đồng bộ hóa)
                synchronized (lock) {
                    try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE, true)) {
                        fos.write(chunk.getBytes());
                    }
                }

                position += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keywordCount;
    }

    private static int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }
        return count;
    }
}
