package com.example.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel_roles")
@Setter
@Getter
@ToString
public class ChannelRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channels_roles_id_seq_generator")
    @SequenceGenerator(name = "channels_roles_id_seq_generator", sequenceName = "channel_roles_id_seq")
    private Long id;

    @Column(nullable = false, length = 32, unique = true)
    private String role;

    @OneToMany(mappedBy = "channelRole")
    @ToString.Exclude
    private List<ChannelRoleJunction> channelRoleJunctions = new ArrayList<>();

    public void setChannelRoleJunctions(ChannelRoleJunction channelRoleJunction) {
        this.channelRoleJunctions.add(channelRoleJunction);
        channelRoleJunction.setChannelRole(this);
    }
}