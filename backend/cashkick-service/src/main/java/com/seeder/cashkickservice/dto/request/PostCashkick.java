package com.seeder.cashkickservice.dto.request;

import com.seeder.cashkickservice.utils.Constants;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCashkick {

  @DecimalMin(value = "1", message = Constants.TOTAL_RECEIVED_ERROR)
 private double totalReceived;

@DecimalMin(value = "1", message = Constants.TOTAL_FINANCED_ERROR)
 private double totalFinanced;

@NotBlank
 @Length(min = 1, max = 10, message = Constants.STATUS_ERROR)
 private String status;

@NotBlank
@Length(min = 1, max = 10, message = Constants.NAME_ERROR)
 private String name;

@NotNull
 private UUID userId;

  @NotNull
 private List<UUID> contractIds;
}
