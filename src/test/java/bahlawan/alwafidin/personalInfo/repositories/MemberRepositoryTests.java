package bahlawan.alwafidin.personalInfo.repositories;

import bahlawan.alwafidin.personalInfo.entities.Member;
import bahlawan.alwafidin.personalInfo.entities.Parent;
import bahlawan.alwafidin.personalInfo.extra.ExcelUtility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository repo;

    @Test
    public void createOnePersonTest() {
        Member member = Member.builder()
                .firstName("حسن")
                .middleName("محمد")
                .lastName("fsfs")
                .nationalNumber("12313132")
                .parent(new Parent())
                .build();

        Member memberDb = repo.save(member);
        assertThat(memberDb.getId()).isGreaterThan(0);
    }
}
