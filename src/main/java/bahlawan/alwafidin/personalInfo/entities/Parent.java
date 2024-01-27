package bahlawan.alwafidin.personalInfo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Parent extends Person{

    @Column(name = "phone_number",  columnDefinition = "varchar(15) default ''")
    protected String phoneNumber;

    @Column(name = "alternate_phone_number", columnDefinition = "varchar(15) default ''")
    protected String alternatePhoneNumber;

    @Column(name = "family_book_id", nullable = false, columnDefinition = "varchar(15) default ''")
    protected String familyBookId;

    @Column(name = "address", columnDefinition = "varchar(128) default ''")
    protected String address;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Member> familyMembers = new HashSet<>();

    @Transient
    protected Boolean isParent = true;

    @Builder
    public Parent(String firstName, String middleName, String lastName, String nationalNumber, Date birthdate, String phoneNumber, String alternatePhoneNumber, String familyBookId, String address, Set<Member> familyMembers) {
        super(firstName, middleName, lastName, nationalNumber, birthdate);
        this.phoneNumber = phoneNumber;
        this.alternatePhoneNumber = alternatePhoneNumber;
        this.familyBookId = familyBookId;
        this.address = address;
        if (familyMembers != null) {
            this.familyMembers.addAll(familyMembers);
        }
        this.isParent = true;
    }

    public void addFamilyMember(Member member) {
        this.familyMembers.add(member);
    }
}
