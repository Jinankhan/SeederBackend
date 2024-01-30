package com.seeder.cashkickservice.service.cashkick;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import com.seeder.cashkickservice.entity.Cashkick;
import com.seeder.cashkickservice.entity.Contract;
import com.seeder.cashkickservice.exception.ResourceNotFoundException;
import com.seeder.cashkickservice.repository.cashkick.CashkickRepository;
import com.seeder.cashkickservice.repository.contract.ContractRepository;
import com.seeder.cashkickservice.utils.Converter;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CashkickServiceImp implements ICashkickService {

  private final  CashkickRepository cashkickRepository;
  private final Converter converter;

  private final ContractRepository contractRepository;

  @Autowired
  CashkickServiceImp(
    CashkickRepository cashkickRepository,
    Converter converter,
    ContractRepository contractRepository
  ) {
    this.cashkickRepository = cashkickRepository;
    this.converter = converter;
    this.contractRepository = contractRepository;
  }

  @Override
  public List<GetCashkick> getUserCashkicks(
    UUID id,
    int pageSize,
    int pageNumber
  ) {
    try{
      Pageable pageable = PageRequest.of(pageNumber, pageSize);
      Page<Cashkick> pages = cashkickRepository.findAll(pageable);
      return pages
              .getContent()
              .stream()
              .filter(cashkick -> cashkick.getUserID().equals(id))
              .map(this::updateCashkickDto)
              .toList();
    }

    catch(Exception e){
      throw new ResourceNotFoundException("Cashkick not found");
    }

  }

  @Override
  public GetCashkick postCashkick(PostCashkick postCashkick) {

    List<Contract> selectedContracts = contractRepository.findByIdIn(
      postCashkick.getContractIds()
    );
    Cashkick cashkick = converter.postCashkickToEntity(postCashkick);
    cashkick.setContracts(selectedContracts);
    cashkick.setMaturity(new Date());

    return updateCashkickDto(cashkickRepository.save(cashkick));
  }

  public GetCashkick updateCashkickDto(Cashkick cashkick) {
    GetCashkick getCashkick = converter.entityToGetCashkick(cashkick);
    getCashkick.setContractIds(
      cashkick.getContracts().stream().map(Contract::getId).toList()
    );
    return getCashkick;
  }
}
