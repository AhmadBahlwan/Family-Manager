package bahlawan.alwafidin.personalInfo.controllers;

import bahlawan.alwafidin.personalInfo.entities.Parent;
import bahlawan.alwafidin.personalInfo.entities.Person;
import bahlawan.alwafidin.personalInfo.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ParentController {
    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping("/parents/save")
    public String saveUser(Parent person, RedirectAttributes redirectAttributes) {
        Person savedPerson = parentService.save(person);
        redirectAttributes.addFlashAttribute("message", "The person has been saved successfully.");

        String fullName = savedPerson.getFullName();
        return "redirect:/persons/page/1?sortField=id&sortDirection=asc&keyword=" + fullName;
    }
}
