package com.kbtg.bootcamp.posttest.domain.store;


import com.kbtg.bootcamp.posttest.exception.LotteryBadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService underTest;

    @Test
    void testGetAllLotteryShouldReturnTicketListCorrectly() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(10)
                .build();
        when(storeRepository.findAllStockedLotteries()).thenReturn(
                List.of(lottery)
        );
        // Act
        List<Lottery> results = underTest.ticketList();
        // Assert
        assertEquals(1, results.size());
        assertEquals(lottery.getTicket(), results.get(0).getTicket());
    }

    @Test
    void testGetTicketByIdshouldReturnTicketCorrectly() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(10)
                .build();
        when(storeRepository.findByTicket(Mockito.any())).thenReturn(
                Optional.of(lottery)
        );
        // Act
        Lottery result = underTest.findByTicketId("1234567890");
        // Assert
        assertEquals(lottery.getTicket(), result.getTicket());
    }

    @Test
    void testGetTicketByIdShouldReturnTicketNotFound() {
        // Arrange
        when(storeRepository.findByTicket(Mockito.any())).thenReturn(
                Optional.empty()
        );
        // Act && Assert
        assertThrows(NotFoundException.class, () -> underTest.findByTicketId("1234567890"));
    }

    @Test
    void testAddTicketShouldReturnTicketCorrectly() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(10)
                .build();
        when(storeRepository.save(Mockito.any())).thenReturn(
                lottery
        );

        // Act
        Lottery result = underTest.addLottery(lottery);
        // Assert
        assertEquals(lottery.getTicket(), result.getTicket());
    }

    @Test
    void testDeductLotteryShouldReturnLotteryWithDedudctedAmount() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(10)
                .build();
        when(storeRepository.save(Mockito.any())).thenReturn(
                lottery
        );

        // Act
        Lottery result = underTest.deductLottery(lottery);
        // Assert
        assertEquals(lottery.getTicket(), result.getTicket());
        assertEquals(9, result.getAmount());
    }

    @Test
    void testDeductLotteryShouldReturnErrorWithLotteryOutOfStock() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(0)
                .build();
        // Act && Assert
        assertThrows(LotteryBadRequestException.class, () -> underTest.deductLottery(lottery));
    }
}