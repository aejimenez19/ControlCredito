package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.constant.ConstantExpetion;
import com.aejimenez19.ControlCredito.exception.LoanProcessingException;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInterestSchedulerService {
    private final PrestamoRepository prestamoRepository;
    private static final int BATCH_SIZE = 500;

    /**
     * Scheduled task that runs daily at midnight to process overdue loans.
     * Updates the remaining balance, interest, and due date for all overdue loans.
     * Processes loans in batches to manage memory efficiently.
     *
     * @throws LoanProcessingException if there's an error during the loan update process
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateOverdueLoans() {
        try {
            List<Prestamo> overdueLoans;

            do {
                overdueLoans = prestamoRepository.findOverdueLoans(PageRequest.of(0, BATCH_SIZE));
                processOverdueLoans(overdueLoans);
            } while (!overdueLoans.isEmpty());

        } catch (Exception e) {
            throw new LoanProcessingException(ConstantExpetion.OVERDUE_LOAN_PROCESSING_ERROR, e);
        }
    }

    /**
     * Processes a batch of overdue loans, updating their details and saving changes.
     *
     * @param loans List of overdue loans to process
     * @return Number of successfully processed loans
     */
    private void processOverdueLoans(List<Prestamo> loans) {
        for (Prestamo loan : loans) {
            try {
                if (isValidLoanForUpdate(loan)) {
                    updateLoanDetails(loan);
                }
            } catch (Exception e) {
                throw new LoanProcessingException(String.format(ConstantExpetion.LOAN_PROCESSING_ERROR, loan.getId()), e);
            }
        }

        prestamoRepository.saveAll(loans);
    }

    /**
     * Validates if a loan is eligible for update by checking its remaining interest.
     *
     * @param loan The loan to validate
     * @return true if the loan is valid for update, false otherwise
     */
    private boolean isValidLoanForUpdate(Prestamo loan) {
        if (loan.getInteresRestante().compareTo(BigDecimal.ZERO) < 0) {
            throw new LoanProcessingException(ConstantExpetion.REMAINING_INTEREST_CANNOT_BE_NEGATIVE);
        }
        return true;
    }

    /**
     * Updates the details of a single loan including:
     * - Adding remaining interest to the balance
     * - Resetting and recalculating interest
     * - Updating the due date
     *
     * @param loan The loan to update
     */
    private void updateLoanDetails(Prestamo loan) {
        // Add current interest to remaining balance
        BigDecimal newBalance = loan.getSaldoRestante().add(loan.getInteresRestante());
        loan.setSaldoRestante(newBalance);

        // Reset and recalculate interest based on new balance
        loan.setInteresRestante(BigDecimal.ZERO);
        BigDecimal newInterest = loan.getSaldoRestante().multiply(loan.getTasaInteres());
        loan.setInteresRestante(newInterest);

        // Update due date to next month while maintaining payment day
        LocalDate newDueDate = loan.getFechaVencimiento()
                .plusMonths(1)
                .withDayOfMonth(loan.getDiaCobro());
        loan.setFechaVencimiento(newDueDate);

    }
}
