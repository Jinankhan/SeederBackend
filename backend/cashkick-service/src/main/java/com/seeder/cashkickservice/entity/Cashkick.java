package com.seeder.cashkickservice.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "cashkick")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cashkick {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(
    name = "uuid-hibernate-generator",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(name = "total_financed")
  private  double totalFinanced;

  @Column(name = "total_received")
  private double totalReceived;

  @Column(name = "user_id")
  private UUID userID;

  private  String status;
  private  String name;
  private  Date maturity;

  @ManyToMany
  @JoinTable(
    name = "cashkick_contract",
    joinColumns = @JoinColumn(name = "cashkick_id"),
    inverseJoinColumns = @JoinColumn(name = "contract_id")
  )
  private List<Contract> contracts = new ArrayList<>();
}
