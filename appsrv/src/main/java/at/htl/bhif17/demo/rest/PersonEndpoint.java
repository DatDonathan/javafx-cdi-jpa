package at.htl.bhif17.demo.rest;

import at.htl.bhif17.demo.dao.PersonDao;
import at.htl.bhif17.demo.model.Person;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class PersonEndpoint {
    @Inject PersonDao personDao;

    @GET
    public List<Person> all() {
        return personDao.getAll();
    }
    @Path("/{id:[0-9]+}")
    @GET
    public Person getPerson(@PathParam("id") int id) {
        return personDao.findById(id);
    }

    @PUT
    public Person putPerson(Person person) {
        return personDao.save(person);
    }
}
