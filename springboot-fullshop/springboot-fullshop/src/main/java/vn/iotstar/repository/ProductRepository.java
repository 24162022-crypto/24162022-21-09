package vn.iotstar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.iotstar.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByUser_IdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);

    Page<Product> findByUser_Id(Long userId, Pageable pageable);

    long countByUser_Id(Long userId);
}
