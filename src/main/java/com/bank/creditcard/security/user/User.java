package com.bank.creditcard.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false)
  private String firstname;

  @Column(nullable = false)
  private String lastname;

  @Column(nullable = false)
  private Date dateOfBirth;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String password;

  @Column(unique = true)
  private String aadharNumber;

  @Column(unique = true, nullable = false)
  private String panNumber;

  @Column(nullable = false)
  private String gender;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @Override
  @JsonIgnore
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
