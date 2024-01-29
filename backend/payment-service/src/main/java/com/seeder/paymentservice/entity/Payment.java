package com.seeder.paymentservice.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Entity
@Getter
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(
    strategy = "org.hibernate.id.UUIDGenerator",
    name = "uuid-hibernate-generator"
  )
  private UUID id;

  @Column(name = "due_date")
  private Date dueDate;

  @Column(name = "expected_amount")
  private double expectedAmount;

  @Column(name = "outstanding_amount")
  private double outstandingAmount;

  private String status;

  @Column(name = "user_id")
  private UUID userId;
}
