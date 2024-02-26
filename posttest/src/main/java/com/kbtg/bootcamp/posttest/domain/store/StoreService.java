package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.exception.LotteryBadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<Lottery> ticketList() {
        log.info("listing all ticket");
        return storeRepository.findAllStockedLotteries();
    }

    public Lottery findByTicketId(String ticketId) {
        log.info("find lottery by ticket id: {}", ticketId);
        return storeRepository.findByTicket(ticketId)
                .orElseThrow(() -> new NotFoundException("Lottery not found"));
    }

    public Lottery addLottery(Lottery lottery) {
        log.info("Add new lottery ticket: {}", lottery.getTicket());
        return storeRepository.save(lottery);
    }

    public Lottery deductLottery(Lottery lottery) {
        log.info("Deduct lottery ticket: {}", lottery.getTicket());
        return Optional.of(lottery)
                .map(Lottery::getAmount)
                .flatMap(LotteryHelper::deductAmountByOne)
                .map(lottery::setAmountFor)
                .map(storeRepository::save)
                .orElseThrow(() -> new LotteryBadRequestException("Lottery is out of stock"));
    }
}

