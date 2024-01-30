package com.seeder.cashkickservice.service.contract;

import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetContract;

import java.util.List;
import java.util.UUID;

public interface IContractService {

    GetContract saveContract(PostContract postContract);

    List<GetContract> getAllContracts(int pageSize ,int pageNumber);


    List<GetContract> getUserContracts(UUID id);
}
