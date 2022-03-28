package com.proxiad.hangmangame.model.security;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
public class Role implements Serializable {

  private static final long serialVersionUID = -3842988150039021360L;

  @Id
  @Column(name = "role_id")
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @NotNull private String name;

  @NotNull private String description;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Permission> permissions;
}
