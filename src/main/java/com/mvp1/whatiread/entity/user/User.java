package com.mvp1.whatiread.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvp1.whatiread.entity.Friend;
import com.mvp1.whatiread.entity.Shelf;
import com.mvp1.whatiread.entity.audit.DateAudit;
import com.mvp1.whatiread.entity.role.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

@Entity
@Getter
@Setter
@Table(name = "USERS", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),
    @UniqueConstraint(columnNames = {"email"})}
)
@NoArgsConstructor
public class User extends DateAudit {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 40)
  private String firstName;

  @NotBlank
  @Size(max = 40)
  private String lastName;

  @NotBlank
  @Size(max = 15)
  private String username;

  @NotBlank
  @NaturalId
  @Size(max = 40)
  @Email
  @Column(nullable = false)
  private String email;

  @NotBlank
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Size(max = 100)
  @Column(nullable = false)
  private String password;

  private String phone;

  private String website;

  @OneToMany(mappedBy = "user")
  private Set<Shelf> shelves = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private Set<Friend> friends = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<Role> roles;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  private Address address;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "company_id")
  private Company company;

  public User(String firstName, String lastName, String username, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
