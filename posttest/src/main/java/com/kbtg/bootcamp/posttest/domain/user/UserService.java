package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import com.kbtg.bootcamp.posttest.domain.store.StoreService;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.shared.composer.Composer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final StoreService storeService;
    private final UserRepository userRepository;

    public Set<Lottery> lotteriesFor(Long userId) {
        log.info("get lottery for user: {}", userId);
        return Composer.of(userId)
                .bind(this::userFromId)
                .bind(User::getTickets)
                .get();
    }

    @Transactional
    public String buyLotteryFor(Long userId, String ticketId) {
        log.info("buy lottery ticket {} for user: {}", ticketId, userId);
        return Composer.of(userId)
                .bind(this::userFromId)
                .bind((u) -> addTicketFor(u, ticketId))
                .get();
    }

    public User userFromId(Long id) {
        log.info("find user by id: {}", id);
        return Optional.of(id)
                .flatMap(userRepository::findById)
                .orElseThrow(() -> new NotFoundException("user not found"));
    }

    @Transactional
    public String addTicketFor(User user, String ticketId) {
        log.info("adding ticket for userId: {}", user.getId());
        return Composer.of(ticketId)
                .bind(storeService::findByTicketId)
                .bind(storeService::deductLottery)
                .bind(user::addTicket)
                .bind(userRepository::save)
                .bind(u -> ticketId)
                .get();
    }

    public String sellTicketFrom(Long userId, String ticketId) {
        log.info("remove ticket for user: {}", userId);
        return Composer.of(userId)
                .bind(this::userFromId)
                .bind(user -> removeTicketFromUser(user, ticketId))
                .bind(userRepository::save)
                .bind(user -> ticketId)
                .get();
    }

    public Lottery findTicketFromUser(User user, String ticketId) {
        return user.findTicketFrom(ticketId)
                .orElseThrow(() -> new NotFoundException("ticket not found"));
    }

    public User removeTicketFromUser(User user, String ticketId) {
        return Composer.of(user)
                .bind(u -> findTicketFromUser(u, ticketId))
                .bind(user::removeTicket)
                .get();
    }
}
