package com.chandebois.rest;

import com.chandebois.entities.User;
import com.chandebois.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by nonok on 08/06/2016.
 */
@Path("users")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GET
    @Path(value = "/${idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("idUser") String idUser) {
        User user = userRepository.findOne(idUser);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @GET
    @Path(value = "/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {
        List<User> users = userRepository.findAll();
        if (CollectionUtils.isEmpty(users)) {
            return Response.status(Response.Status.NO_CONTENT).entity(users).build();
        }
        return Response.status(Response.Status.OK).entity(users).build();
    }

    @DELETE
    @Path(value = "/${idUser}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("idUser") String idUser) {
        if (!userRepository.exists(idUser)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userRepository.delete(idUser);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path(value = "/${user}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        userRepository.save(user);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path(value = "/${user}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) {
        if (user.getId() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        userRepository.save(user);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
