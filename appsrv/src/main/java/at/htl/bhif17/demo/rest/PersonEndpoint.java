package at.htl.bhif17.demo.rest;

import at.htl.bhif17.demo.dao.PersonDao;
import at.htl.bhif17.demo.model.Person;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response putPerson(Person person) {
        person = personDao.save(person);
        return Response.ok(person).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    public Response deletePerson(@PathParam("id") int id) {
        Person person = personDao.findById(id);
        personDao.delete(person);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
