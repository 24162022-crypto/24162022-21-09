package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.UserDTO;
import vn.iotstar.dto.UserFormDTO;
import vn.iotstar.entity.Role;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.UserMapper;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.RoleRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<UserDTO> search(String keyword, Pageable pageable) {
        String kw = keyword == null ? "" : keyword.trim();
        Page<User> page = userRepository
                .findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                        kw, kw, kw, pageable);
        return page.map(u -> {
            UserDTO dto = userMapper.toDto(u);
            dto.setProductCount(productRepository.countByUser_Id(u.getId()));
            return dto;
        });
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Khong tim thay user"));
        UserDTO dto = userMapper.toDto(user);
        dto.setProductCount(productRepository.countByUser_Id(id));
        return dto;
    }

    @Override
    public UserFormDTO findFormById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Khong tim thay user"));
        return userMapper.toFormDto(user);
    }

    @Override
    @Transactional
    public void create(UserFormDTO dto) {
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new IllegalStateException("Email da duoc su dung");
        }
        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            throw new IllegalStateException("Username da duoc su dung");
        }
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new IllegalStateException("Vai tro khong hop le"));

        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail().toLowerCase())
                .password(passwordEncoder.encode(
                        dto.getPassword() == null || dto.getPassword().isBlank() ? "123456" : dto.getPassword()))
                .fullName(dto.getFullName())
                .role(role)
                .enabled(dto.isEnabled())
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(UserFormDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalStateException("Khong tim thay user"));

        userMapper.updateEntityFromForm(dto, user);

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new IllegalStateException("Vai tro khong hop le"));
        user.setRole(role);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public long countAll() {
        return userRepository.count();
    }
}
