package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.dto.UserFormDTO;

public interface UserService {

    Page<UserDTO> search(String keyword, Pageable pageable);

    UserDTO findById(Long id);

    UserFormDTO findFormById(Long id);

    void create(UserFormDTO dto);

    void update(UserFormDTO dto);

    void delete(Long id);

    long countAll();
}
