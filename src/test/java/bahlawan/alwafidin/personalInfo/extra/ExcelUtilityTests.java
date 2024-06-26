package bahlawan.alwafidin.personalInfo.extra;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


public class ExcelUtilityTests {

    private static String PATH;
    private  static final int SHEET_INDEX = 0;
    private static final int ROW_INDEX = 2;

    @BeforeAll
    static void beforeAll() {
        Path resourceDir = Paths.get("src\\test\\resources\\");
        PATH = resourceDir.toFile().getAbsolutePath() + "\\file.xlsx";
    }

    @Test
    public void readPersonInfoFromExcelRowTest() {

        try(FileInputStream file = new FileInputStream(PATH)) {
            Workbook workbook =  new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(2);
            Map<String, Object> parent = ExcelUtility.getParentInfoFromExcelRow(row, 0);
            System.out.println(parent);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void readMemberInfoFromExcelRowTest() {
        try(FileInputStream file = new FileInputStream(PATH)) {
            Workbook workbook =  new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Map<String, Object> member = ExcelUtility.getMemberInfoFromExcelRow(row, 12);
            System.out.println(member.toString());
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Test
//    public void readAllMembersInfoFromExcelRowTest() {
//        int i = 12;
//        try(FileInputStream file = new FileInputStream(PATH)) {
//            Workbook workbook =  new XSSFWorkbook(file);
//            Sheet sheet = workbook.getSheetAt(0);
//            Row row = sheet.getRow(8);
//            Set<Member> familyMembers = new HashSet<>();
//            boolean hasMembers = StringUtil.isNotBlank(row.getCell(i).getStringCellValue());
//            while (hasMembers) {
//                Member member = createMemberFromExcelRow(row, i);
//                familyMembers.add(member);
//                i += 3;
//                hasMembers = row.getCell(i) != null;
//                System.out.println(member.toString());
//            }
//            assertThat(familyMembers.isEmpty()).isFalse();
//            assertThat(familyMembers.size()).isEqualTo(4);
//        }catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
