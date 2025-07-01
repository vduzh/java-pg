package by.vduzh.pg.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Validated
public class Address {
    private String city;
    private String street;
    private String house;
    private String zip;
}
