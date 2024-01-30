package com.seeder.cashkickservice.controller.contract;
import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetContract;
import com.seeder.cashkickservice.service.contract.ContractServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContractControllerTest {

    @Mock
    private ContractServiceImp contractService;

    @InjectMocks
    private ContractController contractController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveContract_shouldReturnCreatedStatusAndContract() {

        PostContract postContract = new PostContract();
        GetContract getContract = new GetContract();
        when(contractService.saveContract(postContract)).thenReturn(getContract);
        ResponseEntity<GetContract> responseEntity = contractController.saveContract(postContract);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(getContract, responseEntity.getBody());
        verify(contractService, times(1)).saveContract(postContract);
    }

    @Test
    void getAllContracts_shouldReturnCreatedStatusAndContractList() {
        int pageSize = 5;
        int pageNumber = 0;
        List<GetContract> contractList = List.of(new GetContract(), new GetContract());
        when(contractService.getAllContracts(pageSize, pageNumber)).thenReturn(contractList);
        ResponseEntity<List<GetContract>> responseEntity = contractController.getAllContracts(pageSize, pageNumber);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(contractList, responseEntity.getBody());
        verify(contractService, times(1)).getAllContracts(pageSize, pageNumber);
    }

    @Test
    void getUserContracts_shouldReturnCreatedStatusAndUserContracts() {
        UUID userId = UUID.randomUUID();
        List<GetContract> userContracts = List.of(new GetContract(), new GetContract());

        when(contractService.getUserContracts(userId)).thenReturn(userContracts);
        ResponseEntity<List<GetContract>> responseEntity = contractController.getUserContracts(userId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userContracts, responseEntity.getBody());
        verify(contractService, times(1)).getUserContracts(userId);
    }


}
