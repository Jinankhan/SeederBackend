package com.seederuserservice.entity;

import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Builder
@Getter
@Setter
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(
    name = "uuid-hibernate-generator",
    strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(name = "credit_amount")
  private double creditAmount;

  private String name;
  private String password;
  private String email;
}
