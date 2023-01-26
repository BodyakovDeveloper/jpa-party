package com.example.sql.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq_generator")
    @SequenceGenerator(name = "post_id_seq_generator", sequenceName = "post_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "body", nullable = false, length = 1024)
    private String body;

    @Column(name = "timestamp", updatable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "post_persons_fk"))
    private Person person;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    public void setPerson(Person person) {
        this.person = person;
        person.getPosts().add(this);
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}