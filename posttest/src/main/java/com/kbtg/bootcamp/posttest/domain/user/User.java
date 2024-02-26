package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "myuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "users")
    private Set<Lottery> tickets;

    public User addTicket(Lottery ticket) {
        this.tickets.add(ticket);
        ticket.getUsers().add(this);
        return this;
    }

    public Optional<Lottery> findTicketFrom(String ticketId) {
        return this.tickets
                .stream()
                .filter(ticket -> ticket.getTicket().equals(ticketId))
                .findFirst();
    }

    public User removeTicket(Lottery ticket) {
        this.tickets.remove(ticket);
        ticket.getUsers().remove(this);
        return this;
    }
}
