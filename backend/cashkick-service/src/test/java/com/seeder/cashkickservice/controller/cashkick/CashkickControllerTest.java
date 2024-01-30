package com.seeder.cashkickservice.controller.cashkick;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import com.seeder.cashkickservice.service.cashkick.ICashkickService;
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

class CashkickControllerTest {

    @Mock
    private ICashkickService cashkickService;

    @InjectMocks
    private CashkickController cashkickController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserCashkicks_shouldReturnOkStatusAndUserCashkicks() {

        UUID userId = UUID.randomUUID();
        int pageSize = 5;
        int pageNumber = 0;
        List<GetCashkick> userCashkicks = List.of(new GetCashkick(), new GetCashkick());

        when(cashkickService.getUserCashkicks(userId, pageSize, pageNumber)).thenReturn(userCashkicks);
        ResponseEntity<List<GetCashkick>> responseEntity = cashkickController.getUserCashkicks(userId, pageSize, pageNumber);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userCashkicks, responseEntity.getBody());
        verify(cashkickService, times(1)).getUserCashkicks(userId, pageSize, pageNumber);
    }

    @Test
    void postCashkick_shouldReturnOkStatusAndCashkick() {

        PostCashkick postCashkick = new PostCashkick();
        GetCashkick getCashkick = new GetCashkick();

        when(cashkickService.postCashkick(postCashkick)).thenReturn(getCashkick);
        ResponseEntity<GetCashkick> responseEntity = cashkickController.postCashkick(postCashkick);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(getCashkick, responseEntity.getBody());
        verify(cashkickService, times(1)).postCashkick(postCashkick);
    }



}
