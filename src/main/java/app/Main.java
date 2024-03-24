package app;

import java.util.List;

/**
 * Главный класс приложения, инициализирует данные и  генерирует отчеты по CDR файлам.
 * Инициализирует базу данных, генерирует CDR файлы, читает их и создает отчеты и производит вывод в консоль.
 */
public class Main {
    public static void main(String[] args) {
        H2Database.initializeDatabase();

        // Получение списка абонентов
        List<String> subscribers = H2Database.getSubscribers();

        // Генерация CDR файлов
        CDRGenerator.generateCDRFiles(subscribers);

        // Чтение CDR файлов и создание отчета
        List<CallRecord> callRecords = CDRReader.readCDRFiles();
        ReportGenerator reportGenerator = new ReportGenerator(callRecords);
        reportGenerator.generateReport();
    }
}
