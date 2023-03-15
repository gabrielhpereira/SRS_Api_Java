package com.api.srs.repository.product;

import com.api.srs.entity.product.ProductEntity;
import com.api.srs.vo.product.ProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, BigInteger> {
    String SELECT = "SELECT new com.api.srs.vo.product.ProductVo(p.id, p.name, p.price, p.amount) FROM ProductEntity p";

    @Query(SELECT)
    public List<ProductVo> listAllProducts();

    @Query(SELECT
            + " WHERE 1 = 1"
            + "     AND :id = 0 OR p.id = :id"
            + "     AND :name IS NULL OR p.name LIKE CONCAT('%', :name, '%')"
            + "     AND :price = 0 OR p.price = :price"
            + "     AND :amount = 0 OR p.amount = :amount")
    public List<ProductVo> listProductByFilters(
            @Param("id") BigInteger id,
            @Param("name") String name,
            @Param("price") BigDecimal price,
            @Param("amount") Integer amount
    );
}
