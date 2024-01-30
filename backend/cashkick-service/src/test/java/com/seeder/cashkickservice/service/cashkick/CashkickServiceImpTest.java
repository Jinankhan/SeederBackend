package com.seeder.cashkickservice.service.cashkick;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import com.seeder.cashkickservice.entity.Cashkick;
import com.seeder.cashkickservice.entity.Contract;
import com.seeder.cashkickservice.repository.cashkick.CashkickRepository;
import com.seeder.cashkickservice.repository.contract.ContractRepository;
import com.seeder.cashkickservice.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CashkickServiceImpTest {

    @Mock
    private CashkickRepository cashkickRepository;

    @Mock
    private Converter converter;

    @Mock
    private ContractRepository contractRepository;

    @InjectMocks
    private CashkickServiceImp cashkickService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void postCashkick_shouldReturnGetCashkick() {

        PostCashkick postCashkick = new PostCashkick();
        List<Contract> selectedContracts = List.of(new Contract(), new Contract());
        Cashkick cashkick = new Cashkick();
        GetCashkick expectedGetCashkick = new GetCashkick();

        when(converter.postCashkickToEntity(postCashkick)).thenReturn(cashkick);
        when(contractRepository.findByIdIn(postCashkick.getContractIds())).thenReturn(selectedContracts);
        when(converter.entityToGetCashkick(any(Cashkick.class))).thenReturn(expectedGetCashkick);
        when(cashkickRepository.save(cashkick)).thenReturn(cashkick);

        GetCashkick result = cashkickService.postCashkick(postCashkick);

        assertNotNull(result);
        assertEquals(expectedGetCashkick, result);
        verify(contractRepository, times(1)).findByIdIn(postCashkick.getContractIds());
        verify(cashkickRepository, times(1)).save(cashkick);
        verify(converter, times(1)).entityToGetCashkick(cashkick);
    }


}
