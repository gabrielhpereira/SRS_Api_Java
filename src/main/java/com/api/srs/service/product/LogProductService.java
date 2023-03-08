package com.api.srs.service.product;

import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.LogProductRepository;
import com.api.srs.vo.product.LogProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class LogProductService {

    @Autowired
    private LogProductRepository logProductRepository;

    public List<LogProductVo> listAllLogProduct(BigInteger idProduct) {
        return this.logProductRepository.listAllLogProduct(idProduct);
    }

    public void saveLogUpdateProduct(){

    }

    public void saveLogNewProduct(ProductEntity product) {
        this.logProductRepository.save(
                new LogProductEntity
                        .Builder()
                        .product(product)
                        .description("Product " + product.getId() + " - " + product.getName().trim() + " has been created!")
                        .date(nowDate())
                        .build());
    }

    public void saveLogDeleteProduct(String product){
        this.logProductRepository.save(
                new LogProductEntity
                        .Builder()
                        .date(nowDate())
                        .description("Product " + product + " has been deleted!")
                        .build());
    }

    private static Date nowDate(){
        return new Date();
    }
}
