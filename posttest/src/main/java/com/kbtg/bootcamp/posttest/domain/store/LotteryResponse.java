package com.kbtg.bootcamp.posttest.domain.store;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class LotteryResponse {
    private String ticket;

    public static List<LotteryResponse> fromLotteries(List<Lottery> lotteries) {
        return lotteries.stream()
                .map(LotteryResponse::fromLottery)
                .toList();
    }

    public static LotteryResponse fromLottery(Lottery lottery) {
        return LotteryResponse.builder()
                .ticket(lottery.getTicket())
                .build();
    }

    public static List<String> toTicketsString(List<Lottery> lotteries) {
        return lotteries.stream()
                .map(Lottery::getTicket)
                .toList();
    }
}
