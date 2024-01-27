package bahlawan.alwafidin.personalInfo.controllers;

import bahlawan.alwafidin.personalInfo.entities.Person;
import bahlawan.alwafidin.personalInfo.exceptions.PersonNotFoundException;
import bahlawan.alwafidin.personalInfo.services.ParentService;
import bahlawan.alwafidin.personalInfo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class PersonRestControllerController {

    @Autowired
    ParentService parentService;
    @Autowired
    PersonService personService;

    @PostMapping("/add-info-from-file")
    public ResponseEntity<?> addInfoFromFile(@RequestPart MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        parentService.createParentFromExcelFile(inputStream);

        return ResponseEntity.ok("Done");
    }

    @PostMapping("/persons/check-national-number")
    public String checkDuplicationNationalNumber(Integer id, String nationalNumber) {
        return personService.isNationalNumberUnique(id, nationalNumber) ? "OK" : "Duplicated";
    }

    @GetMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") Integer id) throws PersonNotFoundException {
        return personService.get(id);
    }


}
