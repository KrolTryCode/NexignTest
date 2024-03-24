package app;

import java.util.List;

/**
 * Класс для генерации отчетов на основе данных о звонках.
 * Использует {@link UDRGenerator} для создания отчетов.
 */
public class ReportGenerator {
    private final List<CallRecord> callRecords;

    /**
     * Конструктор класса ReportGenerator.
     *
     * @param callRecords Список записей звонков, на основе которых будут генерироваться отчеты.
     */
    public ReportGenerator(List<CallRecord> callRecords) {
        this.callRecords = callRecords;
    }

    /**
     * Генерирует и выводит в консоль общий отчет по всем записям звонков.
     */
    public void generateReport() {
        UDRGenerator.generateUDR(callRecords);
        UDRGenerator.generateReport();
    }

    /**
     * Генерирует и выводит в консоль отчет для конкретного абонента.
     *
     * @param msisdn Номер телефона абонента, для которого нужно сгенерировать отчет.
     */
    public void generateReport(String msisdn) {
        UDRGenerator.generateUDR(callRecords);
        UDRGenerator.generateReport(msisdn);
    }

    /**
     * Генерирует и выводит в консоль отчет для конкретного абонента и месяца.
     *
     * @param msisdn Номер телефона абонента, для которого нужно сгенерировать отчет.
     * @param month Месяц, для которого нужно сгенерировать отчет.
     */
    public void generateReport(String msisdn, String month) {
        int monthNumber = Integer.parseInt(month);
        String formattedMonth = String.format("%02d", monthNumber);
        UDRGenerator.generateUDR(callRecords);
        UDRGenerator.generateReport(msisdn, formattedMonth);
    }
}
