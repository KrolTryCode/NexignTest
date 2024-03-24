import app.CallRecord;
import app.ReportGenerator;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {

    @Test
    void generateReportShouldPrintCorrectOutput() {

        List<CallRecord> callRecords = List.of(
                new CallRecord("79699673776", "1", 1609459200, 1609462800), // ��������� ������
                new CallRecord("79699673776", "2", 1609459200, 1609462799)  // �������� ������
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // ��������� ������
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport();

        System.setOut(originalOut);

        // ��������� �����
        String expectedOutput = "����� �� ���� ������:" + System.lineSeparator() +
                "�������: 79699673776" + System.lineSeparator() +
                "�����: 01" + System.lineSeparator() +
                "�������� ������: 00:59:59" + System.lineSeparator() +
                "��������� ������: 01:00:00" + System.lineSeparator() + System.lineSeparator();

        // �������� ������
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }

    @Test
    void generateReportForSpecificSubscriberShouldPrintCorrectOutput() {

        List<CallRecord> callRecords = List.of(
                new CallRecord("79699673775", "1", 1609459200, 1609459201), // ��������� ������
                new CallRecord("79699673791", "2", 1609459200, 1609462799)  // �������� ������ ��� ������� ������
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // ��������� ������ ��� ������������� ��������
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport("79699673775");

        System.setOut(originalOut);

        // ��������� ����� ��� �������� 79699673775
        String expectedOutput = "����� ��� ��������: 79699673775" + System.lineSeparator() +
                "�����: 01" + System.lineSeparator() +
                "�������� ������: 00:00:00" + System.lineSeparator() +
                "��������� ������: 00:00:01" + System.lineSeparator();

        // �������� ������
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }


    @Test
    void generateReportForSpecificSubscriberAndMonthShouldPrintCorrectOutput() {
        // ���������� ������ ��� ������������
        List<CallRecord> callRecords = List.of(
                new CallRecord("79699673722", "1", 1609459200, 1609459222), // ��������� ������ � ������
                new CallRecord("79699673722", "2", 1612137600, 1612137600 + 3599)  // �������� ������ � �������
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // ��������� ������ ��� ������������� �������� � ������
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport("79699673722", "01");

        System.setOut(originalOut);

        // ��������� ����� ��� �������� 79699673722 � ������
        String expectedOutput = "����� ��� ��������: 79699673722 �� �����: 01" + System.lineSeparator() +
                "�������� ������: 00:00:00" + System.lineSeparator() +
                "��������� ������: 00:00:22" + System.lineSeparator();

        // �������� ������
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }

}
