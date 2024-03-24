import app.CDRGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CDRGeneratorTest {

    private static final String CDR_DIRECTORY = "./CDR";

    @AfterEach
    void cleanup() {
        // �������� ����� �����
        File directory = new File(CDR_DIRECTORY);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        directory.delete();
    }

    @Test
    void generateCDRFilesShouldCreateFiles() {
        // ��������� CDR ������ ��� ������ ��������� ������
        CDRGenerator.generateCDRFiles(Collections.singletonList("79123456789"));

        // ��������, ��� � ���������� CDR ���� �����
        File directory = new File(CDR_DIRECTORY);
        assertTrue(directory.exists() && directory.isDirectory(), "���������� CDR �� ����������");
        assertNotNull(directory.listFiles(), "� ���������� CDR ��� ������");
        assertTrue(Objects.requireNonNull(directory.listFiles()).length > 0, "� ���������� CDR ������ ���� ������� �����");
    }
}