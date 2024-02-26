package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.shared.composer.Composer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TicketResponse {
    private List<String> tickets;


    public static TicketResponse fromLotteries(List<Lottery> lotteries) {
        return Composer.of(lotteries)
                .bind(LotteryResponse::toTicketsString)
                .bind(TicketResponse::fromTicketsString)
                .get();
    }

    public static TicketResponse fromTicketsString(List<String> tickets) {
        return TicketResponse.builder()
                .tickets(tickets)
                .build();
    }

}
