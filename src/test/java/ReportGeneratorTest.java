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
                new CallRecord("79699673776", "1", 1609459200, 1609462800), // исходящий звонок
                new CallRecord("79699673776", "2", 1609459200, 1609462799)  // Входящий звонок
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // Генерация отчета
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport();

        System.setOut(originalOut);

        // Ожидаемый вывод
        String expectedOutput = "Отчет за весь период:" + System.lineSeparator() +
                "Абонент: 79699673776" + System.lineSeparator() +
                "Месяц: 01" + System.lineSeparator() +
                "Входящие звонки: 00:59:59" + System.lineSeparator() +
                "исходящие звонки: 01:00:00" + System.lineSeparator() + System.lineSeparator();

        // Проверка вывода
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }

    @Test
    void generateReportForSpecificSubscriberShouldPrintCorrectOutput() {

        List<CallRecord> callRecords = List.of(
                new CallRecord("79699673775", "1", 1609459200, 1609459201), // Исходящий звонок
                new CallRecord("79699673791", "2", 1609459200, 1609462799)  // Входящий звонок для другого номера
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // Генерация отчета для определенного абонента
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport("79699673775");

        System.setOut(originalOut);

        // Ожидаемый вывод для абонента 79699673775
        String expectedOutput = "Отчет для абонента: 79699673775" + System.lineSeparator() +
                "Месяц: 01" + System.lineSeparator() +
                "Входящие звонки: 00:00:00" + System.lineSeparator() +
                "исходящие звонки: 00:00:01" + System.lineSeparator();

        // Проверка вывода
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }


    @Test
    void generateReportForSpecificSubscriberAndMonthShouldPrintCorrectOutput() {
        // Подготовка данных для тестирования
        List<CallRecord> callRecords = List.of(
                new CallRecord("79699673722", "1", 1609459200, 1609459222), // Исходящий звонок в январе
                new CallRecord("79699673722", "2", 1612137600, 1612137600 + 3599)  // Входящий звонок в феврале
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent, true, Charset.forName("windows-1251")));

        // Генерация отчета для определенного абонента и месяца
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport("79699673722", "01");

        System.setOut(originalOut);

        // Ожидаемый вывод для абонента 79699673722 в январе
        String expectedOutput = "Отчет для абонента: 79699673722 за месяц: 01" + System.lineSeparator() +
                "Входящие звонки: 00:00:00" + System.lineSeparator() +
                "исходящие звонки: 00:00:22" + System.lineSeparator();

        // Проверка вывода
        assertEquals(expectedOutput, outContent.toString(StandardCharsets.UTF_8));
    }

}
