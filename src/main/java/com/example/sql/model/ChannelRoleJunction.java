package com.example.sql.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "channel_user_role_junction")
@Entity
@Getter
@Setter
public class ChannelRoleJunction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_role_junction_id_sequence")
    @SequenceGenerator(name = "channel_role_junction_id_sequence", sequenceName = "channel_role_junction_id_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(name = "channel_fk"))
    private Channel channel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "channel_role_id", foreignKey = @ForeignKey(name = "channel_role_name_fk"))
    private ChannelRole channelRole;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "person_fk"))
    private Person person;

    public void setPerson(Person person) {
        this.person = person;
        person.getChannelRoleJunctions().add(this);
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
        channel.getChannelRoleJunctions().add(this);
    }

    public void setChannelRole(ChannelRole channelRole) {
        this.channelRole = channelRole;
        channelRole.getChannelRoleJunctions().add(this);
    }
}