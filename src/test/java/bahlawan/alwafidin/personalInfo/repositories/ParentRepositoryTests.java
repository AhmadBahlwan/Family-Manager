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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ParentRepositoryTests {
    @Autowired
    private ParentRepository parentRepo;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void createOnePersonTest() {
            Parent parent = Parent.builder()
                    .firstName("Ahmad")
                    .lastName("Bahlawan")
                    .nationalNumber("20210012131")
                    .phoneNumber("+963994844")
                    .familyBookId("1546462")
                    .address("مخيم الوافدين")
                    .build();

        Member member = Member.builder()
                .firstName("حسن")
                .middleName("محمد")
                .lastName("fsfs")
                .nationalNumber("12313132")
                .build();
        member.setParent(parent);
        parent.addFamilyMember(member);

            Parent parentDb = parentRepo.save(parent);
            assertThat(parentDb.getId()).isGreaterThan(0);
    }
}
