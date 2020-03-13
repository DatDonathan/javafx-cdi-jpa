package at.htl.bhif17.demo;

import at.ac.htl.util.Observer;
import at.ac.htl.util.ObserverManager;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Dependent
@Default
public class DBPersonDao implements PersonDao{
    @Inject EntityManager em;
    private ObserverManager<PersonDao> manager = new ObserverManager<>();

    public List<Person> getAll() {
        return em.createQuery("select p from Person p order by p.lastName, p.firstName, p.matNr", Person.class).getResultList();
    }

    @Override
    @Transactional
    public Person save(Person person) {
        Person p = em.merge(person);
        em.flush();
        manager.notifyObservers(this);
        return p;
    }

    @Override
    @Transactional
    public void delete(Person person) {
        em.remove(person);
        em.flush();
        manager.notifyObservers(this);
    }

    @Override
    public void addListener(Observer<PersonDao> listener) {
        manager.addListener(listener);
    }

    @Override
    public void removeListener(Observer<PersonDao> listener) {
        manager.removeListener(listener);
    }
}
