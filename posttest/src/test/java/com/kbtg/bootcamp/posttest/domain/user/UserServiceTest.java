package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import com.kbtg.bootcamp.posttest.domain.store.StoreService;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    StoreService storeService;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService underTest;

    @Test
    void testLotteriesForShouldReturnUserLotteryCorrectly() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .tickets(Set.of(Lottery
                        .builder()
                        .id(1L)
                        .ticket("0123456789")
                        .build()
                )).build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // Act
        Set<Lottery> result = underTest.lotteriesFor(userId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testLotteriesForShouldReturnUserNotFoundException() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .tickets(Set.of(Lottery
                        .builder()
                        .id(1L)
                        .ticket("0123456789")
                        .build()
                )).build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        // Act && Assert
        assertThrows(NotFoundException.class, () -> underTest.lotteriesFor(userId));
    }

    @Test
    void testBuyLotteryFor() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket(ticketId)
                .users(new HashSet<>())
                .amount(10)
                .build();
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(storeService.findByTicketId(ticketId)).thenReturn(lottery);
        when(storeService.deductLottery(Mockito.any())).thenReturn(lottery.setAmountFor(9));
        when(userRepository.save(user)).thenReturn(user);


        // Act
        String result = underTest.buyLotteryFor(userId, ticketId);

        // Assert
        assertEquals(ticketId, result);
        assertEquals(1, user.getTickets().size());
    }

    @Test
    void testUserFromIdShouldReturnUserCorrectly() {
        // Arrange
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        // Act
        User result = underTest.userFromId(userId);

        // Assert
        assertEquals(userId, result.getId());
    }

    @Test
    void testAddTicketForShouldReturnTicketIdCorrectly() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket(ticketId)
                .users(new HashSet<>())
                .amount(10)
                .build();
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        when(storeService.findByTicketId(ticketId)).thenReturn(lottery);
        when(storeService.deductLottery(Mockito.any())).thenReturn(lottery.setAmountFor(9));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        String result = underTest.addTicketFor(user, ticketId);

        // Assert
        assertEquals(ticketId, result);
        assertEquals(1, user.getTickets().size());
    }

    @Test
    void testSellTicketFromShouldReturnTicketIdCorrectly() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket(ticketId)
                .users(new HashSet<>())
                .amount(10)
                .build();
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        user.addTicket(lottery);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act
        String result = underTest.sellTicketFrom(userId, ticketId);

        // Assert
        assertEquals(ticketId, result);
        assertEquals(0, user.getTickets().size());
    }

    @Test
    void testSellTicketFromShouldReturnNotfoundTicketException() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act && Assert
        assertThrows(NotFoundException.class, () -> underTest.sellTicketFrom(userId, ticketId));
    }

    @Test
    void testFindTicketFromUserShouldReturnTicketCorrectly() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket(ticketId)
                .users(new HashSet<>())
                .amount(10)
                .build();
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        user.addTicket(lottery);
        // Act
        Lottery result = underTest.findTicketFromUser(user, ticketId);

        // Assert
        assertEquals(ticketId, result.getTicket());
    }

    @Test
    void testFindTicketFromUserShouldReturnNotFoundException() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        // Act && Assert
        assertThrows(NotFoundException.class, () -> underTest.findTicketFromUser(user, ticketId));

    }

    @Test
    void testRemoveTicketFromUserShouldUpdateTicketFromUserCorrectly() {
        // Arrange
        Long userId = 1L;
        String ticketId = "0123456789";
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket(ticketId)
                .users(new HashSet<>())
                .amount(10)
                .build();
        User user = User.builder()
                .id(userId)
                .tickets(new HashSet<>())
                .build();
        user.addTicket(lottery);
        // Act
        User result = underTest.removeTicketFromUser(user, ticketId);

        // Assert
        assertEquals(0, result.getTickets().size());
    }
}