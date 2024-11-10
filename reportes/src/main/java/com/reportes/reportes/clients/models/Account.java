package com.reportes.reportes.clients.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor

public class Account {
   private Long id;
   private LocalDate fechaAlta;
   public Account() {}
}
