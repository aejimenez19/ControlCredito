package com.aejimenez19.ControlCredito.service;

import com.aejimenez19.ControlCredito.repository.PrestamoRepository;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LoanInterestSchedulerServiceTest {

    @InjectMocks
    private LoanInterestSchedulerService loanInterestSchedulerService;

    @Mock
    private PrestamoRepository prestamoRepository;




}
