package com.viajes.viajes.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripInputDTO implements Serializable {
    private String scooterId;
    private Long accountId;
}
