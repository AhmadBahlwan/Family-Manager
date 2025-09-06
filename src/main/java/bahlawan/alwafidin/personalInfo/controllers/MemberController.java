package bahlawan.alwafidin.personalInfo.controllers;

import bahlawan.alwafidin.personalInfo.entities.Member;
import bahlawan.alwafidin.personalInfo.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/save")
    public String saveUser(Member member, RedirectAttributes redirectAttributes) {
        Member savedMember = memberService.save(member);
        redirectAttributes.addFlashAttribute("message", "The person has been saved successfully.");

        return "redirect:/persons/page/1?sortField=id&sortDirection=asc&keyword=" + savedMember.getNationalNumber();
    }
}
