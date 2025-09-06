package bahlawan.alwafidin.personalInfo.services;

import bahlawan.alwafidin.personalInfo.entities.Member;
import bahlawan.alwafidin.personalInfo.exceptions.PersonNotFoundException;
import bahlawan.alwafidin.personalInfo.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Member save(Member member) {
        return repository.save(member);
    }

    public Member get(int id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow();
    }
}
