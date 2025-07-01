package by.vduzh.pg.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    //@Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    private Address address;
}
