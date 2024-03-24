package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для генерации и обработки отчетов UDR
 * Агрегирует данные о звонках из записей CDR и генерирует отчеты
 */
public class UDRGenerator {
    private static final String INCOMING = "incomingCall";
    private static final String OUTGOING = "outcomingCall";

    // данные о звонках: msisdn -> month -> callType -> totalTime
    private static final Map<String, Map<String, Map<String, Long>>> udrData = new HashMap<>();

    /**
     * Генерирует UDR, агрегируя данные из предоставленных записей CDR.
     * @param callRecords Список записей о звонках для обработки.
     */
    public static void generateUDR(List<CallRecord> callRecords) {

        for (CallRecord record : callRecords) {
            String msisdn = record.msisdn();
            String month = record.getMonth();
            long duration = record.getDuration();
            String callType = record.callType().equals("1") ? OUTGOING : INCOMING;

            udrData.putIfAbsent(msisdn, new HashMap<>());
            Map<String, Map<String, Long>> monthData = udrData.get(msisdn);
            monthData.putIfAbsent(month, new HashMap<>());
            Map<String, Long> callTypeData = monthData.get(month);
            callTypeData.put(callType, callTypeData.getOrDefault(callType, 0L) + duration);
        }

        File directory = new File("reports");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        udrData.forEach((msisdn, monthData) -> monthData.forEach((month, callTypeData) -> {
            String fileName = String.format("reports/%s_%s.json", msisdn, month);
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("{\n");
                writer.write("    \"msisdn\": \"" + msisdn + "\",\n");
                writer.write("    \"" + INCOMING + "\": {\n");
                writer.write("        \"totalTime\": \"" + formatDuration(callTypeData.getOrDefault(INCOMING, 0L)) + "\"\n");
                writer.write("    },\n");
                writer.write("    \"" + OUTGOING + "\": {\n");
                writer.write("        \"totalTime\": \"" + formatDuration(callTypeData.getOrDefault(OUTGOING, 0L)) + "\"\n");
                writer.write("    }\n");
                writer.write("}\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * Выводит в консоль отчет за весь период по всем абонентам.
     */
    public static void generateReport() {
        System.out.println("Отчет за весь период:");
        udrData.forEach((msisdn, monthData) -> {
            System.out.println("Абонент: " + msisdn);
            monthData.forEach((month, callTypeData) -> {
                System.out.println("Месяц: " + month);
                System.out.println("Входящие звонки: " + formatDuration(callTypeData.getOrDefault(INCOMING, 0L)));
                System.out.println("исходящие звонки: " + formatDuration(callTypeData.getOrDefault(OUTGOING, 0L)));
            });
            System.out.println();
        });
    }

    /**
     * Выводит в консоль отчет за весь период для указанного абонента.
     * @param msisdn Номер абонента, для которого нужен отчет.
     */
    public static void generateReport(String msisdn) {
        System.out.println("Отчет для абонента: " + msisdn);
        Map<String, Map<String, Long>> monthData = udrData.get(msisdn);
        if (monthData != null) {
            monthData.forEach((month, callTypeData) -> {
                System.out.println("Месяц: " + month);
                System.out.println("Входящие звонки: " + formatDuration(callTypeData.getOrDefault(INCOMING, 0L)));
                System.out.println("исходящие звонки: " + formatDuration(callTypeData.getOrDefault(OUTGOING, 0L)));
            });
        } else {
            System.out.println("Данные не найдены.");
        }
    }

    /**
     * Выводит в консоль отчет для указанного абонента и месяца.
     * @param msisdn Номер абонента, для которого нужен отчет.
     * @param month Месяц, для которого нужен отчет.
     */
    public static void generateReport(String msisdn, String month) {
        System.out.println("Отчет для абонента: " + msisdn + " за месяц: " + month);
        Map<String, Map<String, Long>> monthData = udrData.get(msisdn);
        if (monthData != null) {
            Map<String, Long> callTypeData = monthData.get(month);
            if (callTypeData != null) {
                System.out.println("Входящие звонки: " + formatDuration(callTypeData.getOrDefault(INCOMING, 0L)));
                System.out.println("исходящие звонки: " + formatDuration(callTypeData.getOrDefault(OUTGOING, 0L)));
            } else {
                System.out.println("Данные за месяц не найдены.");
            }
        } else {
            System.out.println("Данные для абонента не найдены.");
        }
    }

    /**
     * Форматирует длительность звонка в читаемый формат (чч:мм:сс).
     * @param duration Длительность звонка в секундах.
     * @return Строковое представление длительности.
     */
    private static String formatDuration(long duration) {
        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = duration % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
