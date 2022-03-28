package com.proxiad.hangmangame.api.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import com.proxiad.hangmangame.api.UserApi;
import com.proxiad.hangmangame.model.security.User;

@Component
public class UserInfoAssembler extends RepresentationModelAssemblerSupport<User, UserInfo> {

  private TypeMap<User, UserInfo> userToUserInfoMapper =
      new ModelMapper().createTypeMap(User.class, UserInfo.class);

  public UserInfoAssembler() {
    super(UserApi.class, UserInfo.class);
  }

  @Override
  public UserInfo toModel(User user) {

    UserInfo userInfo = userToUserInfoMapper.map(user);

    return userInfo.add(linkTo(methodOn(UserApi.class).getUser(user.getId())).withSelfRel());
  }
}
