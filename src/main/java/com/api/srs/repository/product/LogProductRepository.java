package com.api.srs.repository.product;

import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.vo.product.LogProductVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface LogProductRepository extends JpaRepository<LogProductEntity, Integer> {

    @Query("SELECT new com.api.srs.vo.product.LogProductVo(l.id, l.productId, l.description, l.date)"
            + "     FROM LogProductEntity l"
            + " WHERE l.productId = :productId")
    public List<LogProductVo> listAllLogProduct(
            @Param("productId") BigInteger productId
    );
}