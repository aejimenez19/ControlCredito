package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.constant.ConstantExpetion;
import com.aejimenez19.ControlCredito.exception.PaymentValidationException;
import com.aejimenez19.ControlCredito.model.Pago;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.repository.PagoRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PagoService {
    private final PagoRepository pagoRepository;
    private final PrestamoService prestamoService;

    /**
     * Retrieves a list of payments (Pagos) associated with a given loan (Prestamo).
     *
     * @param prestamo the loan whose payments are to be retrieved
     * @return a list of payments associated with the specified loan
     */
    public List<Pago> getPagoByPrestamo(Prestamo prestamo) {
        return pagoRepository.findByPrestamo(prestamo);
    }

    /**
     * Saves a payment (Pago) to the repository.
     *
     * @param pago the payment to be saved
     */
    @Transactional
    public Pago savePago(Pago pago) {
        try {
            validatePayment(pago);
            return savePaymentAndUpdateLoan(pago);
        } catch (Exception e) {
            throw new PersistenceException(ConstantExpetion.FAILED_PAYMENT_PROCESSING, e);
        }
    }

    /**
     * Validates the payment data before processing
     *  @param pago the payment to be validated
     */
    private void validatePayment(Pago pago) {
        if (pago == null) {
            throw new PaymentValidationException(ConstantExpetion.PAYMENT_CANNOT_BE_NULL);
        }
        if (pago.getMontoPagado() == null || pago.getMontoPagado().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentValidationException(ConstantExpetion.INVALID_PAYMENT_AMOUNT);
        }
        if (pago.getPrestamo() == null) {
            throw new PaymentValidationException(ConstantExpetion.LOAN_REFERENCE_CANNOT_BE_NULL);
        }
    }

    /**
     * Builds a new payment (Pago) object with initialized values.
     *
     * @param pago The source payment object containing initial payment information
     * @return A new Pago object with all fields properly set
     */
    private Pago buildPago(Pago pago) {
        return Pago
                .builder()
                .prestamo(pago.getPrestamo())
                .fechaPago(new Date())
                .montoPagado(pago.getMontoPagado())
                .tipoPago(pago.getTipoPago())
                .observaciones(pago.getObservaciones())
                .build();
    }

    /**
     * Processes the payment and updates the loan
     *  @param pago the payment to be processed
     */
    private Pago savePaymentAndUpdateLoan(Pago pago) {
        try {
            Pago builtPago = buildPago(pago);
            Pago savedPago = pagoRepository.save(builtPago);
            updateLoanBalance(savedPago);
            return savedPago;
        } catch (Exception e) {
            throw new PersistenceException(ConstantExpetion.FAILED_PAYMENT_PROCESSING, e);
        }
    }

    /**
     * Updates the loan balance after payment
     *  @param savedPago the payment to be updated
     */
    private void updateLoanBalance(Pago savedPago) {
        try {
            UUID loanId = savedPago.getPrestamo().getId();
            BigDecimal paymentAmount = savedPago.getMontoPagado();
            prestamoService.updateBalances(loanId, paymentAmount);
        } catch (Exception e) {
            throw new PersistenceException(ConstantExpetion.FAIL_UPDATE_LOAN, e);
        }
    }


}
