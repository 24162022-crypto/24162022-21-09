package vn.iotstar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.dto.ProductFormDTO;

public interface ProductService {

    /** currentUserId = null va isAdmin = true -> xem tat ca; nguoc lai chi xem cua minh. */
    Page<ProductDTO> search(String keyword, Long currentUserId, boolean isAdmin, Pageable pageable);

    ProductDTO findById(Long id);

    ProductFormDTO findFormById(Long id);

    void create(ProductFormDTO dto, Long ownerId);

    void update(ProductFormDTO dto, Long currentUserId, boolean isAdmin);

    void delete(Long id, Long currentUserId, boolean isAdmin);

    long countAll();

    long countByUser(Long userId);
}
