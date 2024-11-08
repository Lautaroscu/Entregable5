package com.viajes.viajes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
@Data
@AllArgsConstructor

public class Account {
   private Long id;
   private LocalDate fechaAlta;
   public Account() {}
}
