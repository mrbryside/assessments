package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lottery {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @Size(min = 6, max = 6, message = "ticket must be at least 6 number")
    @Pattern(regexp = "^[0-9]+$", message = "Ticket must contain only numbers (6 characters)")
    private String ticket;
    private Double price;
    private Integer amount;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "user_ticket",
            joinColumns = @JoinColumn(name = "lottery_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Lottery setAmountFor(Integer amount) {
        this.setAmount(amount);
        return this;
    }

}
