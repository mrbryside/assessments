package com.kbtg.bootcamp.posttest.domain.store;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LotteryHelper {
    public static Number totalLotteryCost(Set<Lottery> lotteries) {
        return lotteries.stream()
                .map(Lottery::getPrice)
                .reduce(0.0, Double::sum);
    }

    public static Number totalLotteryCount(Set<Lottery> lotteries) {
        return lotteries.size();
    }

    public static List<String> ticketStringList(Set<Lottery> lotteries) {
        return lotteries.stream()
                .map(Lottery::getTicket)
                .toList();
    }

    public static Optional<Integer> deductAmountByOne(Integer amount) {
        return amount > 0 ? Optional.of(amount - 1) : Optional.empty();
    }
}
