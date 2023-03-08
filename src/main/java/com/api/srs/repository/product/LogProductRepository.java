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

    @Query("SELECT new com.api.srs.vo.product.LogProductVo(l.id, l.description, l.date, l.product)" +
            " FROM LogProductEntity l" +
            " WHERE l.product.id = :idProduct")
    public List<LogProductVo> listAllLogProduct(
            @Param("idProduct") BigInteger idProduct
    );
}