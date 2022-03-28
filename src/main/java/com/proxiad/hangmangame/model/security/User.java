package com.proxiad.hangmangame.model.security;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString.Exclude;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class User implements Serializable {

  @Id
  @Column(name = "user_id")
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String username;

  @NotNull private String password;

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
  @Exclude
  private Set<Role> roles;

  @Override
  public int hashCode() {
    return Objects.hash(firstName, id, lastName, password, username);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    User other = (User) obj;
    return Objects.equals(firstName, other.firstName)
        && Objects.equals(id, other.id)
        && Objects.equals(lastName, other.lastName)
        && Objects.equals(password, other.password)
        && Objects.equals(username, other.username);
  }
}
