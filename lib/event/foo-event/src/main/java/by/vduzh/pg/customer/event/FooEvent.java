package by.vduzh.pg.customer.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FooEvent {
    String id;
    String action;
    //String userId;
    //String email;
//    Instant timestamp;
}
