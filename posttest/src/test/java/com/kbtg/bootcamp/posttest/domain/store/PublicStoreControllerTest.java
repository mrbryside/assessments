package com.kbtg.bootcamp.posttest.domain.store;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublicStoreControllerTest {
    @Mock
    private StoreService storeService;
    @InjectMocks
    private PublicStoreController underTest;

    @Test
    void testGetTicketListShouldReturnTicketListCorrectly() {
        // Arrange
        Lottery ticket = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(1)
                .build();
        List<Lottery> tickets = List.of(ticket);
        when(storeService.ticketList()).thenReturn(tickets);
        TicketResponse ticketResponse = TicketResponse.fromLotteries(tickets);
        ResponseEntity<TicketResponse> expected = ResponseEntity.ok(ticketResponse);

        // Act
        ResponseEntity<TicketResponse> response = underTest.ticketList();

        // Assert
        assertEquals(expected, response);
    }
}