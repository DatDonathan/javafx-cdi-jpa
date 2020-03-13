package at.htl.bhif17.demo;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String matNr;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Person person;
        private Builder() {
            this.person = new Person();
        }

        public Builder id(Integer id) {
            person.id = id;
            return this;
        }
        public Builder firstName(String first) {
            person.firstName = first;
            return this;
        }
        public Builder lastName(String last) {
            person.lastName = last;
            return this;
        }
        public Builder matNr(String mat) {
            person.matNr = mat;
            return this;
        }
        public Person build() {
            return this.person;
        }
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMatNr() {
        return matNr;
    }
    public void setMatNr(String matNr) {
        this.matNr = matNr;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", matNr='" + matNr + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(matNr, person.matNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, matNr);
    }
}
