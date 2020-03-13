package at.htl.bhif17.demo;

import at.ac.htl.util.Observable;

import javax.transaction.Transactional;
import java.util.List;

public interface PersonDao extends Observable<PersonDao> {

    public List<Person> getAll ();

    public Person save (Person person);

    public void delete (Person person);

}
