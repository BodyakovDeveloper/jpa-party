package com.example.sql;

import com.example.sql.model.Channel;
import com.example.sql.model.ChannelRole;
import com.example.sql.model.ChannelRoleJunction;
import com.example.sql.model.Comment;
import com.example.sql.model.Person;
import com.example.sql.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.example.sql.model.Role.ADMIN;
import static com.example.sql.model.Role.OWNER;
import static java.time.Month.SEPTEMBER;

public class Main {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwordsPersistenceUnit");

    public static void main(String[] args) {

 //     n + 1
//      em.unwrap(Session.class).setDefaultReadOnly(true); dirty check off
        doInSession(em -> {

            Person owner = new Person();
            owner.setFirstName("Bohdan");
            owner.setLastName("Koval");
            owner.setUsername("bodyak0v");
            owner.setBirthday(LocalDate.of(2002, SEPTEMBER, 23));

            em.persist(owner);
            Person admin = new Person();
            admin.setFirstName("Bohdan");
            admin.setLastName("Koval");
            admin.setUsername("secondUsername");
            admin.setBirthday(LocalDate.of(2002, SEPTEMBER, 23));
            em.persist(admin);

            ChannelRole ownerRole = new ChannelRole();
            ChannelRole adminRole = new ChannelRole();
            ownerRole.setRole(OWNER.getValue());
            adminRole.setRole(ADMIN.getValue());
            em.persist(ownerRole);
            em.persist(adminRole);

            Channel channel1 = new Channel();
            channel1.setChannelTag("firstChannel");
            channel1.setName("Weather in telegram");
            channel1.setPerson(owner);
            em.persist(channel1);

            Channel channel2 = new Channel();
            channel2.setChannelTag("secondChannel");
            channel2.setName("My second channel");
            channel2.setPerson(owner);
            em.persist(channel2);

            ChannelRoleJunction channelRoleJunction = new ChannelRoleJunction();
            channelRoleJunction.setChannel(channel1);
            channelRoleJunction.setChannelRole(adminRole);
            channelRoleJunction.setPerson(admin);
            em.persist(channelRoleJunction);

            ChannelRoleJunction channelRoleJunction1 = new ChannelRoleJunction();
            channelRoleJunction1.setChannel(channel1);
            channelRoleJunction1.setChannelRole(ownerRole);
            channelRoleJunction1.setPerson(owner);
            em.persist(channelRoleJunction1);


            Person user1 = new Person();
            user1.setFirstName("Bohdan");
            user1.setLastName("Koval");
            user1.setUsername("thiedUsername");
            user1.setBirthday(LocalDate.of(2002, SEPTEMBER, 23));
            em.persist(user1);

            Post post = new Post();
            post.setPerson(user1);
            post.setBody("Hello it's my first post here =)");

            Comment comment = new Comment();
            comment.setPerson(user1);
            comment.setBody("It's my first comment");
            comment.setPost(post);
            // persist comment using cascade (ALL (PERSIST) ) in post

            em.persist(post);
        });

    }

    public static void doInSession(Consumer<EntityManager> entityManagerConsumer) {
        var em = emf.createEntityManager();
        Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);
        try {
            em.getTransaction().begin();
            entityManagerConsumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static <T> T doInSessionReturning(Function<EntityManager, T> entityManagerTFunction) {
        var em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            T data = entityManagerTFunction.apply(em);
            em.getTransaction().commit();
            return data;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}