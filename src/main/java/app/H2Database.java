package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс для взаимодействия с базой данных H2. Используется для хранения и управления данными абонентов и транзакций.
 */
public class H2Database {

    private static final String JDBC_URL = "jdbc:h2:./data/subscribers";

    /**
     * Получает соединение с базой данных H2.
     * @return Соединение с базой данных.
     * @throws Exception если не удается установить соединение.
     */
    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(JDBC_URL, "sa", "");
    }

    /**
     * Инициализирует базу данных. Создает таблицы, если их нет, заполняет таблицу subscribers.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS subscribers (id INT AUTO_INCREMENT, number VARCHAR(20), PRIMARY KEY(id))");
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (id INT AUTO_INCREMENT, msisdn VARCHAR(20), callType INT, startTime LONG, endTime LONG, PRIMARY KEY(id))");

            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM subscribers");
            if (rs.next() && rs.getInt("count") == 0) {
                for (int i = 0; i < 10; i++) {
                    String number = "79" + (100000000 + new Random().nextInt(900000000));
                    stmt.execute(String.format("INSERT INTO subscribers (number) VALUES ('%s')", number));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Получает список всех абонентов из базы данных.
     * @return Список номеров абонентов.
     */
    public static List<String> getSubscribers() {
        List<String> subscribers = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT number FROM subscribers")) {
            while (rs.next()) {
                subscribers.add(rs.getString("number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscribers;
    }

    /**
     * Вставляет данные транзакции в таблицу transactions.
     * @param msisdn Номер абонента.
     * @param callType Тип звонка.
     * @param startTime Время начала звонка.
     * @param endTime Время окончания звонка.
     */
    public static void insertTransaction(String msisdn, int callType, long startTime, long endTime) {
        String sql = "INSERT INTO transactions (msisdn, callType, startTime, endTime) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, msisdn);
            pstmt.setInt(2, callType);
            pstmt.setLong(3, startTime);
            pstmt.setLong(4, endTime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
