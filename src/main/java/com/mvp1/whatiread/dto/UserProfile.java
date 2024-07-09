package com.mvp1.whatiread.dto;

import com.mvp1.whatiread.entity.user.Address;
import com.mvp1.whatiread.entity.user.Company;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

  private Long id;
  private String username;
  private String firstName;
  private String lastName;
  private Instant joinedAt;
  private String email;
  private Address address;
  private String phone;
  private String website;
  private Company company;
}
