package by.vduzh.pg.customer.dto;

import java.io.Serializable;

public record CustomerDto(Integer id, String name) implements Serializable {
}
