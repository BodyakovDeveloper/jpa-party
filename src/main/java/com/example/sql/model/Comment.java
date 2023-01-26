package com.example.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Setter
@Getter
@Table(name = "comments")
@ToString(exclude = {"person", "post"})
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_seq_generator")
    @SequenceGenerator(name = "comment_id_seq_generator", sequenceName = "comment_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "body")
    private String body;

    @Column(name = "date_of_creation", updatable = false)
    @CreationTimestamp
    private LocalDate dateOfCreation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "post_fk"))
    private Post post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "person_fk"))
    private Person person;

    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void setPerson(Person person) {
        this.person = person;
        person.getComments().add(this);
    }
}
