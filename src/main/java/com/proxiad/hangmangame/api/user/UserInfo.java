package com.proxiad.hangmangame.api.user;

import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfo extends RepresentationModel<UserInfo> {

  private String id;
  private String firstName;
  private String lastName;
  private String username;
}
