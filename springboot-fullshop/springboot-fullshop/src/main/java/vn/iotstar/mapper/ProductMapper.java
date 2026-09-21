package vn.iotstar.mapper;

import org.mapstruct.*;
import vn.iotstar.dto.ProductDTO;
import vn.iotstar.dto.ProductFormDTO;
import vn.iotstar.entity.Product;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "ownerUsername", source = "user.username")
    ProductDTO toDto(Product entity);

    @Mapping(target = "currentImage", source = "image")
    ProductFormDTO toFormDto(Product entity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromForm(ProductFormDTO dto, @MappingTarget Product entity);
}
