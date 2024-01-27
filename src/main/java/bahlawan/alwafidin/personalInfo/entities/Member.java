package bahlawan.alwafidin.personalInfo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Person{

    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent = new Parent();

    @Transient
    protected Boolean isParent = false;

    @Builder
    public Member(String firstName, String middleName, String lastName, String nationalNumber, Date birthdate, Parent parent) {
        super(firstName, middleName, lastName, nationalNumber, birthdate);
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Member{" +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
    @Transient
    public String getAddress() {
        return this.parent.getAddress();
    }
}
