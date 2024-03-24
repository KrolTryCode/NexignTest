package app;

import java.util.List;

/**
 * ������� ����� ����������, �������������� ������ �  ���������� ������ �� CDR ������.
 * �������������� ���� ������, ���������� CDR �����, ������ �� � ������� ������ � ���������� ����� � �������.
 */
public class Main {
    public static void main(String[] args) {
        H2Database.initializeDatabase();

        // ��������� ������ ���������
        List<String> subscribers = H2Database.getSubscribers();

        // ��������� CDR ������
        CDRGenerator.generateCDRFiles(subscribers);

        // ������ CDR ������ � �������� ������
        List<CallRecord> callRecords = CDRReader.readCDRFiles();
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport();
    }
}
