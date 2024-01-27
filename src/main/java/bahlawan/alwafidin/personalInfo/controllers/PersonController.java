package bahlawan.alwafidin.personalInfo.controllers;

import bahlawan.alwafidin.personalInfo.entities.Member;
import bahlawan.alwafidin.personalInfo.entities.Parent;
import bahlawan.alwafidin.personalInfo.entities.Person;
import bahlawan.alwafidin.personalInfo.exceptions.PersonNotFoundException;
import bahlawan.alwafidin.personalInfo.repositories.ParentRepository;
import bahlawan.alwafidin.personalInfo.services.MemberService;
import bahlawan.alwafidin.personalInfo.services.ParentService;
import bahlawan.alwafidin.personalInfo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class PersonController {

    @Autowired
    PersonService personService;
    @Autowired
    ParentService parentService;
    @Autowired
    MemberService memberService;

    @GetMapping("/persons")
    public String listFirstPage(@RequestParam(defaultValue = "asc") String sortDirection, Model model) {
        return listByPage(1, "firstName", sortDirection, null, model);
    }

    @GetMapping("/persons/page/{pageNumber}")
    public String listByPage(@PathVariable(name = "pageNumber") Integer pageNumber, @RequestParam String sortField,
                             @RequestParam String sortDirection, @RequestParam(required = false) String keyword, Model model) {
        Page<Person> page = personService.listByPage(pageNumber, sortField, sortDirection, keyword);

        int pageSize = page.getSize();

        long startCount = (long) (pageNumber - 1) * pageSize + 1;
        long endCount = startCount + pageSize - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        String reversedSortDir = sortDirection.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("listPersons", page.getContent());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reversedSortDir", reversedSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("moduleURL", "/persons");

        return "persons";
    }

    @GetMapping("/persons/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Member member = memberService.get(id);
            model.addAttribute("person", member);
            model.addAttribute("pageTitle", "(ID: " + member.getId() + ")");
            return "person_form";
        } catch (PersonNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "persons";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try{
            personService.delete(id);
            redirectAttributes.addFlashAttribute("message", "The person with ID "
                    + id + "has been deleted successfully");
        }catch (PersonNotFoundException ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "persons";
    }
}
