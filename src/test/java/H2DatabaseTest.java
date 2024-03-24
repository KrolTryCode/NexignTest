import app.H2Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class H2DatabaseTest {

    @BeforeAll
    static void setup() {
        // инициализация базы данных для тестов
        H2Database.initializeDatabase();
    }

    @Test
    void getSubscribersShouldReturnNotEmptyList() {
        List<String> subscribers = H2Database.getSubscribers();
        assertFalse(subscribers.isEmpty(), "Список абонентов не должен быть пустым");
    }

    @Test
    void getSubscribersShouldReturnExactNumberOfSubscribers() {
        List<String> subscribers = H2Database.getSubscribers();
        assertEquals(10, subscribers.size(), "Список абонентов должен содержать 10 записей");
    }
}
