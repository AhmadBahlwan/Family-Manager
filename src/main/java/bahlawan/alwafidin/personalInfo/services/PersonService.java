package bahlawan.alwafidin.personalInfo.services;

import bahlawan.alwafidin.personalInfo.entities.Person;
import bahlawan.alwafidin.personalInfo.exceptions.PersonNotFoundException;
import bahlawan.alwafidin.personalInfo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository repository;

    private static final int PERSONS_BY_PAGE = 20;

    private static final int SEARCH_RESULT_BY_PAGE = 10;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Page<Person> listByPage(Integer pageNumber, String sortField, String sortDir, String keyword) {
        Pageable pageable = createPageable(pageNumber, PERSONS_BY_PAGE, sortField, sortDir);
        return keyword != null ? repository.findAll(keyword, pageable)
                : repository.findAll(pageable);
    }

    public Page<Person> search(String keyword, Integer pageNumber, String sortField, String sortDir) {
        Pageable pageable = createPageable(pageNumber, SEARCH_RESULT_BY_PAGE, sortField, sortDir);
        return repository.findAll(keyword, pageable);
    }

    private Pageable createPageable(int pageNum, int size, String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        return PageRequest.of(pageNum - 1, size, sort);
    }

    public Person get(int id) throws PersonNotFoundException {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException("Could not find person with this id: " + id ));
    }

    public boolean isNationalNumberUnique(Integer id, String nationalNumber) {
        Person person = repository.findByNationalNumber(nationalNumber);

        if (id == null) {
            return person == null;
        }

        return person == null || person.getId().equals(id);
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public void delete(Integer id) throws PersonNotFoundException{
        Long personCount = repository.countById(id);
        if (personCount == null || personCount == 0)
            throw new PersonNotFoundException("Could not find any user with id "+ id);

        repository.deleteById(id);
    }
}
