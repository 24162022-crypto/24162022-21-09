package vn.iotstar.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.dto.ProductFormDTO;
import vn.iotstar.entity.Product;
import vn.iotstar.entity.User;
import vn.iotstar.mapper.ProductMapper;
import vn.iotstar.repository.ProductRepository;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.CloudinaryService;
import vn.iotstar.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public Page<ProductDTO> search(String keyword, Long currentUserId, boolean isAdmin, Pageable pageable) {
        String kw = keyword == null ? "" : keyword.trim();
        Page<Product> page = isAdmin
                ? productRepository.findByNameContainingIgnoreCase(kw, pageable)
                : productRepository.findByUser_IdAndNameContainingIgnoreCase(currentUserId, kw, pageable);
        return page.map(productMapper::toDto);
    }

    @Override
    public ProductDTO findById(Long id) {
        return productMapper.toDto(getOrThrow(id));
    }

    @Override
    public ProductFormDTO findFormById(Long id) {
        return productMapper.toFormDto(getOrThrow(id));
    }

    @Override
    @Transactional
    public void create(ProductFormDTO dto, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalStateException("Khong tim thay user"));

        String imageUrl = cloudinaryService.upload(dto.getImageFile(), "products");

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .image(imageUrl)
                .user(owner)
                .build();
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void update(ProductFormDTO dto, Long currentUserId, boolean isAdmin) {
        Product product = getOrThrow(dto.getId());
        assertOwnerOrAdmin(product, currentUserId, isAdmin);

        productMapper.updateEntityFromForm(dto, product);

        if (dto.getImageFile() != null && !dto.getImageFile().isEmpty()) {
            String imageUrl = cloudinaryService.upload(dto.getImageFile(), "products");
            product.setImage(imageUrl);
        }
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id, Long currentUserId, boolean isAdmin) {
        Product product = getOrThrow(id);
        assertOwnerOrAdmin(product, currentUserId, isAdmin);
        productRepository.delete(product);
    }

    @Override
    public long countAll() {
        return productRepository.count();
    }

    @Override
    public long countByUser(Long userId) {
        return productRepository.countByUser_Id(userId);
    }

    private Product getOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Khong tim thay san pham"));
    }

    private void assertOwnerOrAdmin(Product product, Long currentUserId, boolean isAdmin) {
        if (!isAdmin && !product.getUser().getId().equals(currentUserId)) {
            throw new SecurityException("Ban khong co quyen thao tac tren san pham nay");
        }
    }
}
