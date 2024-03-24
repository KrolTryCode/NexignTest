import app.H2Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class H2DatabaseTest {

    @BeforeAll
    static void setup() {
        // ������������� ���� ������ ��� ������
        H2Database.initializeDatabase();
    }

    @Test
    void getSubscribersShouldReturnNotEmptyList() {
        List<String> subscribers = H2Database.getSubscribers();
        assertFalse(subscribers.isEmpty(), "������ ��������� �� ������ ���� ������");
    }

    @Test
    void getSubscribersShouldReturnExactNumberOfSubscribers() {
        List<String> subscribers = H2Database.getSubscribers();
        assertEquals(10, subscribers.size(), "������ ��������� ������ ��������� 10 �������");
    }
}
