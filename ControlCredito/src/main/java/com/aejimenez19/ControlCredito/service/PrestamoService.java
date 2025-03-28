package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.constant.ConstantEnvironment;
import com.aejimenez19.ControlCredito.constant.ConstantExpetion;
import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;

    /**
     * Retrieves a list of loans (Prestamos) associated with a given client (Cliente).
     *
     * @param cliente the client whose loans are to be retrieved
     * @return a list of loans associated with the specified client
     */
    public List<Prestamo> getPrestamosByIdCliente(Cliente cliente) {
        return prestamoRepository.findByCliente(cliente);
    }

    /**
     * Saves a loan (Prestamo) to the repository.
     *
     * @param prestamo the loan to be saved
     * @return the saved loan
     */
    public Prestamo savePrestamo(Prestamo prestamo) {
        prestamo = BuildPrestamo(prestamo);
        return prestamoRepository.save(prestamo);
    }

    /**
     * Builds a new loan (Prestamo) object with initialized values and calculated fields.
     *
     * @param prestamo The source loan object containing initial loan information
     * @return A new Prestamo object with all fields properly set
     *
     *
     */
    private Prestamo BuildPrestamo(Prestamo prestamo) {
        return Prestamo.builder()
                .cliente(prestamo.getCliente())
                .monto(prestamo.getMonto())
                .tasaInteres(prestamo.getTasaInteres())
                .fechaDesembolso(prestamo.getFechaDesembolso())
                .fechaVencimiento(prestamo.getFechaVencimiento())
                .diaCobro(prestamo.getDiaCobro())
                .estado(ConstantEnvironment.ACTIVE)
                .saldoRestante(prestamo.getMonto())
                .interesRestante(calcularInteres(prestamo.getTasaInteres(), prestamo.getMonto()))
                .build();
    }


    /**
     * Calcula el interés sobre un monto dado.
     *
     * @param tasaInteres la tasa de interés en formato decimal (ej: 0.05 para 5%)
     * @param saldoPendiente el monto base sobre el cual se calculará el interés
     * @return el monto del interés calculado
     * @throws IllegalArgumentException si alguno de los parámetros es null o negativo
     */
    private BigDecimal calcularInteres(final BigDecimal tasaInteres, final BigDecimal saldoPendiente) {

        if (tasaInteres == null || saldoPendiente == null) {
            throw new IllegalArgumentException(ConstantExpetion.INTEREST_AND_AMOUNT_NULL);
        }

        if (tasaInteres.compareTo(BigDecimal.ZERO) < 0 || saldoPendiente.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ConstantExpetion.INTEREST_AND_AMOUNT_NOT_NEGATIVE);
        }

        return saldoPendiente.multiply(tasaInteres)
                .setScale(2, RoundingMode.HALF_EVEN);
    }


    /**
     * Updates loan balances after a payment is made
     * @param prestamoId ID of the loan to update
     * @param montoPagado Amount paid
     * @throws IllegalArgumentException if loan is not found
     */
    @Transactional
    public void updateBalances(Long prestamoId, BigDecimal montoPagado) {
        Prestamo prestamo = findPrestamoById(prestamoId);
        processPayment(prestamo, montoPagado);
        prestamoRepository.save(prestamo);
    }

    /**
     * Finds a loan by ID
     */
    private Prestamo findPrestamoById(Long prestamoId) {
        return prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        ConstantExpetion.LOAN_NOT_FOUND_WITH_ID + prestamoId));
    }

    /**
     * Processes the payment and updates loan balances
     */
    private void processPayment(Prestamo prestamo, BigDecimal montoPagado) {
        if (hasNoRemainingInterest(prestamo)) {
            updatePrincipalBalance(prestamo, montoPagado);
        } else {
            processInterestAndPrincipal(prestamo, montoPagado);
        }
    }

    /**
     * Checks if there is no remaining interest
     */
    private boolean hasNoRemainingInterest(Prestamo prestamo) {
        return prestamo.getInteresRestante().compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Updates the principal balance directly
     */
    private void updatePrincipalBalance(Prestamo prestamo, BigDecimal montoPagado) {
        prestamo.setSaldoRestante(prestamo.getSaldoRestante().subtract(montoPagado));
        updateEstado(prestamo);
    }

    /**
     * Updates the status of a loan based on its remaining balance.
     *
     * This method checks if a loan should be marked as completed by examining
     * its remaining balance. If the remaining balance is less than or equal
     * to zero, the loan's status is set to "Finalizado" (Finished).
     *
     * @param prestamo The loan object to update
     * Must not be null and must have a valid remaining balance (saldoRestante)
     */
    private void updateEstado(Prestamo prestamo) {
        if (prestamo.getSaldoRestante().compareTo(BigDecimal.ZERO) <= 0) {
            prestamo.setEstado(ConstantEnvironment.FINALIZED);
        }
    }

    /**
     * Processes payment for both interest and principal
     */
    private void processInterestAndPrincipal(Prestamo prestamo, BigDecimal montoPagado) {
        BigDecimal interesRestante = prestamo.getInteresRestante();

        if (montoPagado.compareTo(interesRestante) >= 0) {
            prestamo.setInteresRestante(BigDecimal.ZERO);
            BigDecimal remainingAmount = montoPagado.subtract(interesRestante);
            updatePrincipalBalance(prestamo, remainingAmount);
        } else {
            prestamo.setInteresRestante(interesRestante.subtract(montoPagado));
        }
    }
}
