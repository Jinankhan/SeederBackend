package com.seeder.cashkickservice.service.contract;

import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetContract;
import com.seeder.cashkickservice.entity.Contract;
import com.seeder.cashkickservice.exception.ResourceNotFoundException;
import com.seeder.cashkickservice.repository.cashkick.CashkickRepository;
import com.seeder.cashkickservice.repository.contract.ContractRepository;
import com.seeder.cashkickservice.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImp implements  IContractService {
    private final ContractRepository contractRepository;

    private final Converter converter;

    private final CashkickRepository cashkickRepository;
    @Autowired
    ContractServiceImp(ContractRepository contractRepository ,Converter converter ,CashkickRepository cashkickRepository)
    {
        this.converter=converter;
        this.contractRepository=contractRepository;
        this.cashkickRepository=cashkickRepository;
    }
    @Override
    public GetContract saveContract(PostContract postContract) {
        Contract contract = converter.postContractToEntity(postContract);

        return updateContractDto(contractRepository.save(contract)) ;

    }

    public GetContract  updateContractDto(Contract contract)
    {
        GetContract getContract = converter.entityToGetContract(contract);
        getContract.setTermLength(contract.getTermLength()+"%");
        return  getContract;
    }
    @Override
    public List<GetContract> getAllContracts(int pageSize ,int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber ,pageSize);
        Page<Contract> page = contractRepository.findAll(pageable);
        List<Contract> contracts =page.getContent();
        return contracts.stream().map(this:: updateContractDto).toList();
    }

    @Override
    public List<GetContract> getUserContracts(UUID id) {

        try{

            return  cashkickRepository.findAll()
                    .stream()
                    .flatMap(cashkick -> cashkick.getContracts().stream())
                    .distinct()
                    .toList().stream().map(this::updateContractDto).toList();

        }
        catch (Exception e)
        {
            throw new ResourceNotFoundException("Contracts not found");
        }


    }
}
