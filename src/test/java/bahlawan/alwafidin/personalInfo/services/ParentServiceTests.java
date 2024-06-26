package bahlawan.alwafidin.personalInfo.services;

import bahlawan.alwafidin.personalInfo.repositories.ParentRepository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ParentServiceTests {

    @MockBean
    ParentRepository repo;

    @InjectMocks
    ParentService service;

    private static String PATH;


    @BeforeAll
    static void beforeAll() {
        Path resourceDir = Paths.get("src\\test\\resources\\");
        PATH = resourceDir.toFile().getAbsolutePath() + "\\file.xlsx";
    }

    @Test
    public void readParentsInfoFroExcelFileTest() {

        try (FileInputStream file = new FileInputStream(PATH)) {
            service.createParentFromExcelFile(file);
            // Verify that saveAll was called with a list of parents
            verify(repo).saveAll(anyList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
