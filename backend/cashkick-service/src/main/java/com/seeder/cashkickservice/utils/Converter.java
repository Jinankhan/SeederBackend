package com.seeder.cashkickservice.utils;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.request.PostContract;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import com.seeder.cashkickservice.dto.response.GetContract;
import com.seeder.cashkickservice.entity.Cashkick;
import com.seeder.cashkickservice.entity.Contract;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Converter {

  private ModelMapper modelMapper;

  Converter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public Contract postContractToEntity(PostContract postContract) {
    return modelMapper.map(postContract, Contract.class);
  }

  public GetContract entityToGetContract(Contract contract) {
    return modelMapper.map(contract, GetContract.class);
  }

  public Cashkick postCashkickToEntity(PostCashkick postCashkick) {
    return modelMapper.map(postCashkick, Cashkick.class);
  }

  public GetCashkick entityToGetCashkick(Cashkick cashkick) {
    return modelMapper.map(cashkick, GetCashkick.class);
  }
}
