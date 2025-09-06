package bahlawan.alwafidin.personalInfo.services;

import bahlawan.alwafidin.personalInfo.entities.Member;
import bahlawan.alwafidin.personalInfo.entities.Parent;
import bahlawan.alwafidin.personalInfo.extra.ExcelUtility;
import bahlawan.alwafidin.personalInfo.repositories.MemberRepository;
import bahlawan.alwafidin.personalInfo.repositories.ParentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class ParentService {
    private static final int FIRST_MEMBER_INDEX = 12;

    private static final int PERSONS_BY_PAGE = 20;
    private final ParentRepository repo;


    public ParentService(ParentRepository repo, MemberRepository memberRepo) {
        this.repo = repo;
    }

    public void createParentFromExcelFile(InputStream file) {
        Workbook workbook = null;
        ObjectMapper mapper = new ObjectMapper();
        List<Parent> parents = new ArrayList<>();
        try {
            workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int columnIndex = 0;
            sheet.removeRow(sheet.getRow(0));
            for (Row row : sheet) {
                Map<String, Object> parentInfo = ExcelUtility.getParentInfoFromExcelRow(row, columnIndex);
                
                Parent parent = mapper.convertValue(parentInfo, Parent.class);

                columnIndex = FIRST_MEMBER_INDEX;

                Set<Member> familyMembers = new HashSet<>();
                boolean hasMembers = row.getCell(columnIndex) != null;
                while (hasMembers && columnIndex < 42) {
                    Map<String, Object> memberInfo = ExcelUtility.getMemberInfoFromExcelRow(row, columnIndex);

                    Member member = mapper.convertValue(memberInfo, Member.class);
                    columnIndex += 3;

                    member.setParent(parent);
                    familyMembers.add(member);


                    hasMembers = row.getCell(columnIndex) != null;
                }
                parent.setFamilyMembers(familyMembers);
                parents.add(parent);
                columnIndex = 0;
            }
            repo.saveAll(parents);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                assert workbook != null;
                workbook.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Page<Parent> listByPage(Integer pageNumber, String sortField, String sortDir) {
        Pageable pageable = createPageable(pageNumber, sortField, sortDir);
        return repo.findAll(pageable);
    }

    private Pageable createPageable(int pageNum, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(pageNum - 1, PERSONS_BY_PAGE, sort);
    }

    public Parent save(Parent parent) {
        return repo.save(parent);
    }
}
