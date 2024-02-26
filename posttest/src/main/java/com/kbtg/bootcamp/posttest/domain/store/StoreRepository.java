package com.kbtg.bootcamp.posttest.domain.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Lottery, Long> {
    @Query("SELECT l FROM Lottery l WHERE l.amount > 0")
    List<Lottery> findAllStockedLotteries();

    Optional<Lottery> findByTicket(String ticket);
}
