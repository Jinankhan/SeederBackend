package com.seeder.cashkickservice.service.contract;

import com.seeder.cashkickservice.entity.Cashkick;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetContract;
import com.seeder.cashkickservice.entity.Contract;
import com.seeder.cashkickservice.repository.cashkick.CashkickRepository;
import com.seeder.cashkickservice.repository.contract.ContractRepository;
import com.seeder.cashkickservice.utils.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;

class ContractServiceImpTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private CashkickRepository cashkickRepository;

    @Mock
    private Converter converter;

    @InjectMocks
    private ContractServiceImp contractService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveContract_shouldReturnGetContract() {
        // Arrange
        PostContract postContract = new PostContract();
        Contract savedContract = new Contract();
        GetContract expectedGetContract = new GetContract();

        when(converter.postContractToEntity(postContract)).thenReturn(savedContract);
        when(contractRepository.save(savedContract)).thenReturn(savedContract);
        when(converter.entityToGetContract(savedContract)).thenReturn(expectedGetContract);

        // Act
        GetContract result = contractService.saveContract(postContract);

        assertNotNull(result);
        assertEquals(expectedGetContract, result);
        verify(contractRepository, times(1)).save(savedContract);
        verify(converter, times(1)).entityToGetContract(savedContract);
    }

    @Test
    void getAllContracts_shouldReturnListOfGetContracts() {

        int pageSize = 5;
        int pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Contract> contractPage = mock(Page.class);
        List<Contract> contracts = List.of(new Contract(), new Contract());
        List<GetContract> expectedGetContracts = List.of(new GetContract(), new GetContract());

        when(contractRepository.findAll(pageable)).thenReturn(contractPage);
        when(contractPage.getContent()).thenReturn(contracts);
        when(converter.entityToGetContract(any(Contract.class))).thenReturn(expectedGetContracts.get(0), expectedGetContracts.get(1));

        List<GetContract> result = contractService.getAllContracts(pageSize, pageNumber);

        assertNotNull(result);
        assertEquals(expectedGetContracts, result);
        verify(contractRepository, times(1)).findAll(pageable);
        verify(converter, times(2)).entityToGetContract(any(Contract.class));
    }

    @Test
    void getUserContracts_shouldReturnListOfGetContracts() {
        UUID userId = UUID.randomUUID();
        List<Contract> userContracts = List.of(new Contract(), new Contract());
        List<GetContract> expectedGetContracts = List.of(new GetContract(), new GetContract());
        Cashkick cashkick = new Cashkick();
        cashkick.setContracts(userContracts);

        when(cashkickRepository.findAll()).thenReturn(List.of(cashkick));
        when(converter.entityToGetContract(any(Contract.class))).thenReturn(expectedGetContracts.get(0), expectedGetContracts.get(1));
        List<GetContract> result = contractService.getUserContracts(userId);

        assertNotNull(result);
        assertEquals(expectedGetContracts, result);
        verify(cashkickRepository, times(1)).findAll();
        verify(converter, times(2)).entityToGetContract(any(Contract.class));
    }


}
