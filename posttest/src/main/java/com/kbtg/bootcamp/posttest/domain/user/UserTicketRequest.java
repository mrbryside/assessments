package com.kbtg.bootcamp.posttest.domain.user;

import com.kbtg.bootcamp.posttest.domain.store.Lottery;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserTicketRequest {
    private User user;
    private Lottery lottery;

    public UserTicketRequest setLottery(Lottery lottery) {
        return UserTicketRequest.builder()
                .user(this.user)
                .lottery(lottery)
                .build();
    }
}
