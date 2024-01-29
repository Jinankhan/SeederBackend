package com.seeder.paymentservice.utils;

import com.seeder.paymentservice.dto.request.PostPayment;
import com.seeder.paymentservice.dto.response.SavePayment;
import com.seeder.paymentservice.entity.Payment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {

  private ModelMapper modelMapper;

  @Autowired
  Converter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public Payment postPaymentToEntity(PostPayment postPayment) {
    return modelMapper.map(postPayment, Payment.class);
  }

  public SavePayment entityToSavePayment(Payment payment) {
    return modelMapper.map(payment, SavePayment.class);
  }
}
