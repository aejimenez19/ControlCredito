package com.aejimenez19.ControlCredito.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonBackReference
    private Cliente cliente;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @Column(name = "tasa_interes", nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "fecha_desembolso", nullable = false)
    private LocalDate fechaDesembolso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "dia_cobro", nullable = false)
    private int diaCobro;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "saldo_restante", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoRestante;

    @Column(name = "interes_restante", precision = 15, scale = 2)
    private BigDecimal interesRestante;

    @OneToMany(mappedBy = "prestamo")
    @JsonManagedReference
    private List<Pago> pagos;
}
