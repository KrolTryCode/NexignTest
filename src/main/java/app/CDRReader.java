package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ����� ��� ���������� CDR ������.
 * ��� ������ ������� ������� �� ������ � �������������� �� � ������� {@link CallRecord}.
 */
public class CDRReader {
    private static final String CDR_DIRECTORY = "./CDR";

    /**
     * ������ CDR ����� �� ���������������� ���������� � ����������� �� � ������ �������� {@link CallRecord}.
     *
     * @return ������ �������� {@link CallRecord}, �������������� ������ �������.
     */
    public static List<CallRecord> readCDRFiles() {
        List<CallRecord> callRecords = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            String fileName = CDR_DIRECTORY + "/cdr_" + i + ".txt";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String callType = parts[0];
                        String phoneNumber = parts[1];
                        long startTime = Long.parseLong(parts[2]);
                        long endTime = Long.parseLong(parts[3]);
                        callRecords.add(new CallRecord(phoneNumber, callType, startTime, endTime));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return callRecords;
    }
}
