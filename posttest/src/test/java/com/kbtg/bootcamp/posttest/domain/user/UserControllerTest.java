package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import com.kbtg.bootcamp.posttest.shared.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController underTest;

    @Test
    void testBuyLotteryShouldReturnTicketCorrectly() {
        // Arrange
        String userId = "1";
        String ticketId = "1234567890";

        when(userService.buyLotteryFor(1L, ticketId)).thenReturn(ticketId);

        ResponseEntity<UserTicketResponse> expected = Response.createdWithBody(UserTicketResponse.fromTicketId(ticketId));

        // Act
        ResponseEntity<UserTicketResponse> response = underTest.buyLottery(userId, ticketId);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testListLotteriesShouldReturnTicketListCorrectly() {
        // Arrange
        Long userId = 1L;
        String userIdRequest = "1";
        Lottery ticket = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(1)
                .build();

        HashSet<Lottery> tickets = new HashSet<>();
        tickets.add(ticket);

        when(userService.lotteriesFor(userId)).thenReturn(tickets);

        ResponseEntity<TicketListResponse> expected = ResponseEntity.ok(TicketListResponse.fromLotteries(tickets));

        // Act
        ResponseEntity<TicketListResponse> response = underTest.listLotteries(userIdRequest);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testSellTicketShouldReturnTicketIdCorrectly() {
        // Arrange
        String userId = "1";
        String ticketId = "1234567890";

        when(userService.sellTicketFrom(1L, ticketId)).thenReturn(ticketId);

        ResponseEntity<UserTicketResponse> expected = ResponseEntity.ok(UserTicketResponse.fromTicketId(ticketId));

        // Act
        ResponseEntity<UserTicketResponse> response = underTest.sellTicket(userId, ticketId);

        // Assert
        assertEquals(expected, response);
    }
}