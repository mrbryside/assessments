package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import com.kbtg.bootcamp.posttest.domain.store.LotteryHelper;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class TicketListResponse {
    private List<String> tickets;
    private Number count;
    private Number cost;

    public static TicketListResponse fromLotteries(Set<Lottery> lotteries) {
        return TicketListResponse.builder()
                .tickets(LotteryHelper.ticketStringList(lotteries))
                .count(LotteryHelper.totalLotteryCount(lotteries))
                .cost(LotteryHelper.totalLotteryCost(lotteries))
                .build();
    }

}
