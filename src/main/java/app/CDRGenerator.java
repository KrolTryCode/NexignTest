package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Класс для генерации CDR (Call Detail Record) файлов.
 */
public class CDRGenerator {
    private static final int NUM_OF_MONTHS = 12;
    private static final String CDR_DIRECTORY = "./CDR";
    private static final int SECONDS_PER_MONTH = 2678400;
    private static final int SECONDS_PER_YEAR = SECONDS_PER_MONTH * 12;

    /**
     * Генерирует CDR файлы для списка абонентов.
     * @param subscribers Список номеров абонентов.
     */
    public static void generateCDRFiles(List<String> subscribers) {
        Random random = new Random();
        File directory = new File(CDR_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }

        for (int i = 1; i <= NUM_OF_MONTHS; i++) {
            try (FileWriter writer = new FileWriter(CDR_DIRECTORY + "/cdr_" + i + ".txt")) {
                for (String phoneNumber : subscribers) {
                    int callsCount = random.nextInt(10) + 1;
                    for (int j = 0; j < callsCount; j++) {
                        int callType = random.nextInt(2) + 1;
                        long startTime = System.currentTimeMillis() / 1000 - random.nextInt(SECONDS_PER_YEAR);
                        long endTime = startTime + random.nextInt(3600);
                        writer.write(callType + "," + phoneNumber + "," + startTime + "," + endTime + "\n");
                        H2Database.insertTransaction(phoneNumber, callType, startTime, endTime);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
