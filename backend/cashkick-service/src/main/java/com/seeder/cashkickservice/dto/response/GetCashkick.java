package com.seeder.cashkickservice.dto.response;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCashkick {

  private UUID id;
  private String totalReceived;
  private String totalFinanced;
  private Date maturity;
  private String status;
  private String name;
  private List<UUID> contractIds;
}
