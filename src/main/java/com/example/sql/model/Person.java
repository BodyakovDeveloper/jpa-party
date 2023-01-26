package com.example.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Table(name = "persons")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq_generator")
    @SequenceGenerator
            (
                    name = "person_id_seq_generator",
                    sequenceName = "person_id_seq",
                    allocationSize = 1
            )
    private Long id;

    @Column(unique = true, length = 32, nullable = false)
    private String username;

    @Column(name = "first_name", length = 32, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 32, nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "person")
    @ToString.Exclude
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    @ToString.Exclude
    private List<Channel> channels = new ArrayList<>();

    @OneToMany(mappedBy = "person")
    @ToString.Exclude
    private List<ChannelRoleJunction> channelRoleJunctions = new ArrayList<>();

    public void addChannel(Channel channel) {
        channel.setPerson(this);
        channels.add(channel);
    }

    public void addComment(Comment comment) {
        comment.setPerson(this);
        comments.add(comment);
    }

    public void addPost(Post post) {
        post.setPerson(this);
        posts.add(post);
    }

    public void setChannelRoleJunctions(ChannelRoleJunction channelRoleJunction) {
        this.channelRoleJunctions.add(channelRoleJunction);
        channelRoleJunction.setPerson(this);
    }
}