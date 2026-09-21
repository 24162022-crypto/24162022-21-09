// File: src/main/java/vn/iotstar/mapper/UserMapper.java
package vn.iotstar.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import vn.iotstar.dto.UserDTO;
import vn.iotstar.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /** Entity -> DTO: lấy roleId, roleName từ role của user. */
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> users);

    /** DTO -> Entity: role và password xử lý riêng ở tầng service nên bỏ qua ở đây. */
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserDTO dto);
}
