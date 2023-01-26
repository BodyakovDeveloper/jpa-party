package com.example.sql.model;

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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channels")
@ToString(exclude = {"person", "channelRoleJunctions"})
@Getter
@Setter
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_id_seq_generator")
    @SequenceGenerator(name = "channel_id_seq_generator", sequenceName = "channel_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "channel_tag", nullable = false, unique = true)
    private String channelTag;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "date_of_creation")
    @CreationTimestamp
    private LocalDate dateOfCreation;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_person_id", foreignKey = @ForeignKey(name = "channels_persons_fk"))
    private Person person;

    @OneToMany(mappedBy = "channel")
    private List<ChannelRoleJunction> channelRoleJunctions = new ArrayList<>();

    public void setPerson(Person person) {
        this.person = person;
        person.getChannels().add(this);
    }

    public void setChannelRoleJunctions(ChannelRoleJunction channelRoleJunction) {
        this.channelRoleJunctions.add(channelRoleJunction);
        channelRoleJunction.setChannel(this);
    }
}