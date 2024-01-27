package bahlawan.alwafidin.personalInfo.entities;

//import bahlawan.alwafidin.personalInfo.DateDeserializer;
//import bahlawan.alwafidin.personalInfo.DateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@NoArgsConstructor
public abstract class Person extends AbstractAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "national_number")
    private String nationalNumber;

    @Column
    @JsonFormat(pattern = "MM/dd/yyyy")
    private Date birthdate;

    public Person(String firstName, String middleName, String lastName, String nationalNumber, Date birthdate) {
        super(firstName, middleName, lastName);
        this.nationalNumber = nationalNumber;
        this.birthdate = birthdate;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Transient
    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}
