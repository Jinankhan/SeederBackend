package com.seeder.cashkickservice.service.cashkick;

import com.seeder.cashkickservice.dto.request.PostCashkick;
import com.seeder.cashkickservice.dto.response.GetCashkick;
import java.util.List;
import java.util.UUID;

public interface ICashkickService {
  List<GetCashkick> getUserCashkicks(UUID id, int pageSize, int pageNumber);

  GetCashkick postCashkick(PostCashkick postCashkick);
}
