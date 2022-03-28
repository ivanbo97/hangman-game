package com.proxiad.hangmangame.model.security;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "role_permission")
public class Permission implements Serializable {

  private static final long serialVersionUID = 8001058063353571671L;

  @Id
  @Column(name = "role_permission_id")
  @GeneratedValue(generator = "uuid-string")
  @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
  private String id;

  @NotNull private String permission;

  @ManyToOne
  @JoinColumn(name = "role_id")
  @EqualsAndHashCode.Exclude
  private Role role;
}
