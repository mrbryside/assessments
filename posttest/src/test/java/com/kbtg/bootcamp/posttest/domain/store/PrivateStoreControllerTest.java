package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.shared.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrivateStoreControllerTest {
    @Mock
    private StoreService storeService;
    @InjectMocks
    private PrivateStoreController underTest;

    @Test
    void testCreateLotteryShouldReturnLotteryCorrectly() {
        // Arrange
        Lottery lottery = Lottery.builder()
                .id(1L)
                .ticket("1234567890")
                .price(100.0)
                .amount(1)
                .build();
        when(storeService.addLottery(Mockito.any())).thenReturn(lottery);

        // Act
        ResponseEntity<LotteryResponse> response = underTest.createLottery(lottery);

        ResponseEntity<LotteryResponse> expected = Response.createdWithBody(LotteryResponse.builder()
                .ticket("1234567890")
                .build());

        // Assert
        assertEquals(expected, response);
    }
}

