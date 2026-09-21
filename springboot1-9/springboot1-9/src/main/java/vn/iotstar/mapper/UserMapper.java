// File: src/main/java/vn/iotstar/mapper/UserMapper.java
package vn.iotstar.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    /** Entity -> DTO: roleName lấy từ role.name. */
    @Mapping(source = "role.name", target = "roleName")
    UserDTO toDto(User user);
}
