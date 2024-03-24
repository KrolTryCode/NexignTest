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
        // удаление после теста
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
        // Генерация CDR файлов для одного тестового номера
        CDRGenerator.generateCDRFiles(Collections.singletonList("79123456789"));

        // Проверка, что в директории CDR есть файлы
        File directory = new File(CDR_DIRECTORY);
        assertTrue(directory.exists() && directory.isDirectory(), "Директория CDR не существует");
        assertNotNull(directory.listFiles(), "В директории CDR нет файлов");
        assertTrue(Objects.requireNonNull(directory.listFiles()).length > 0, "В директории CDR должны быть созданы файлы");
    }
}