package com.kbtg.bootcamp.posttest.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserTicketResponse {
    private String id;

    public static UserTicketResponse fromTicketId(String ticketId) {
        return new UserTicketResponse(ticketId);
    }
}
