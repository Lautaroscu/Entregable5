package com.monopatines.monopatines.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public class ScooterInputDTO implements Serializable {

    private String location;
    private String model;

}
