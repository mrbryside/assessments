package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.shared.composer.Composer;
import com.kbtg.bootcamp.posttest.shared.response.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users/{user_id}/lotteries/{ticket_id}")
    public ResponseEntity<UserTicketResponse> buyLottery(@PathVariable("user_id") String userId,
                                                         @PathVariable("ticket_id") String ticketId) {
        return Composer.of(userId)
                .bind(Long::valueOf)
                .bind(uId -> userService.buyLotteryFor(uId, ticketId))
                .bind(UserTicketResponse::fromTicketId)
                .bind(Response::createdWithBody)
                .get();
    }

    @GetMapping("/users/{user_id}/lotteries")
    public ResponseEntity<TicketListResponse> listLotteries(@PathVariable("user_id") String userId) {
        return Composer.of(userId)
                .bind(Long::valueOf)
                .bind(userService::lotteriesFor)
                .bind(TicketListResponse::fromLotteries)
                .bind(ResponseEntity::ok)
                .get();
    }

    @DeleteMapping("/users/{user_id}/lotteries/{ticket_id}")
    public ResponseEntity<UserTicketResponse> sellTicket(@PathVariable("user_id") String userId,
                                                         @PathVariable("ticket_id") String ticketId) {
        return Composer.of(userId)
                .bind(Long::valueOf)
                .bind(uId -> userService.sellTicketFrom(uId, ticketId))
                .bind(UserTicketResponse::fromTicketId)
                .bind(ResponseEntity::ok)
                .get();
    }
}
