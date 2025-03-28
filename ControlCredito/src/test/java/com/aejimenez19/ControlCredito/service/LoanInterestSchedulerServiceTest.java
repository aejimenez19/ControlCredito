package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.exception.LoanProcessingException;
import com.aejimenez19.ControlCredito.model.Cliente;
import com.aejimenez19.ControlCredito.model.Prestamo;
import com.aejimenez19.ControlCredito.repository.PrestamoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LoanInterestSchedulerServiceTest {

    @InjectMocks
    private LoanInterestSchedulerService loanInterestSchedulerService;

    @Mock
    private PrestamoRepository prestamoRepository;




}
