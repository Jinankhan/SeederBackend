package com.seeder.cashkickservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "contract")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contract {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(
    name = "uuid-hibernate-generator",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(name = "per_payment")
  private double perPayment;

  @Column(name = "payment_amount")
  private double paymentAmount;

  @Column(name = "term_length")
  private double termLength;

  private  String type;
  private String name;

  @ManyToMany(mappedBy = "contracts")
  @JsonIgnore
  private  List<Cashkick> cashkicks = new ArrayList<>();
}
