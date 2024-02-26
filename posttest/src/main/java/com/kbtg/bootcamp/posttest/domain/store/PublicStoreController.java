package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.shared.composer.Composer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@AllArgsConstructor
public class PublicStoreController {

    private final StoreService storeService;

    @GetMapping("/lotteries")
    public ResponseEntity<TicketResponse> ticketList() {
        return Composer.of(storeService.ticketList())
                .bind(TicketResponse::fromLotteries)
                .bind(ResponseEntity::ok)
                .get();
    }
}
