package com.reportes.reportes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteCantidadMonopatines {
    private long cantidadMonopatinesActivos;
    private long cantidadMonopatinesEnMantenimiento;
}
