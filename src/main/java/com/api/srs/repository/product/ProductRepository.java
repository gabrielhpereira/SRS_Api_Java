package com.api.srs.repository.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, BigInteger> {
    String SELECT = "SELECT new com.api.srs.dto.product.ProductDto(p.id, p.name, p.price, p.amount) FROM ProductEntity p";

    @Query(SELECT)
    public List<ProductDto> listAllProducts();

    @Query(SELECT
            + " WHERE 1 = 1"
            + "     AND (:name IS NULL OR UPPER(p.name) LIKE CONCAT('%', :name, '%'))"
            + "     AND (:price = 0 OR p.price LIKE CONCAT('%', :price, '%'))"
            + "     AND (:amount = 0 OR p.amount LIKE CONCAT('%', :amount, '%'))")
    public List<ProductDto> listProductByFilters(
            @Param("name") String name,
            @Param("price") BigDecimal price,
            @Param("amount") Integer amount
    );
}
