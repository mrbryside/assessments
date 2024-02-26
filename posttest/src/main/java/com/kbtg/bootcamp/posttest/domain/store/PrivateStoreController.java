package com.kbtg.bootcamp.posttest.domain.store;

import com.kbtg.bootcamp.posttest.shared.composer.Composer;
import com.kbtg.bootcamp.posttest.shared.response.Response;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class PrivateStoreController {
    private final StoreService storeService;

    @PostMapping("/lotteries")
    public ResponseEntity<LotteryResponse> createLottery(@Valid @RequestBody Lottery lottery) {
        return Composer.of(lottery)
                .bind(storeService::addLottery)
                .bind(LotteryResponse::fromLottery)
                .bind(Response::createdWithBody)
                .get();
    }
}
