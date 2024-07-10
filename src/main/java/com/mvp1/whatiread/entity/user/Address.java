package com.mvp1.whatiread.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "street")
  private String street;

  @Column(name = "suite")
  private String suite;

  @Column(name = "city")
  private String city;

  @Column(name = "zipcode")
  private String zipcode;

  @OneToOne(mappedBy = "address")
  private User user;

  public Address(String street, String suite, String city, String zipcode) {
    this.street = street;
    this.suite = suite;
    this.city = city;
    this.zipcode = zipcode;
  }

  @JsonIgnore
  public Long getId() {
    return id;
  }
}
