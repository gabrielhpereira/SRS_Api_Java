package com.api.srs.service.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.ProductRepository;
import com.api.srs.vo.product.ProductVo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LogProductService logProductService;

    public List<ProductVo> listAllProducts() {
        List<ProductVo> listVo = this.productRepository.listAllProducts();

        if(listVo.isEmpty()) throw new ValidationException("Product not found!");

        return listVo;
    }

    public List<ProductVo> listProductByFilters(ProductDto productDto) {
        List<ProductVo> listVo =  this.productRepository.listProductByFilters(
                productDto.getName() == null || productDto.getName().isBlank() ? null : productDto.getName().trim(),
                productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.ZERO) == 0 ? null : productDto.getPrice(),
                productDto.getAmount() == null || productDto.getAmount().equals(0) ? null : productDto.getAmount()
        );

        if(listVo.isEmpty()) throw new ValidationException("Product not found!");

        return listVo;
    }

    @Transactional
    public void saveOrUpdateProduct(ProductDto productDto) {
        validateProductDto(productDto);

        if (productDto.getId() == null || productDto.getId().equals(BigInteger.ZERO))
            this.saveProduct(productDto);
        else
            this.updateProduct(productDto);
    }

    private void updateProduct(ProductDto productDto) {
        ProductEntity product = this.productRepository.getReferenceById(productDto.getId());
        ProductEntity oldProduct =
                new ProductEntity
                        .Builder()
                        .id(product.getId())
                        .name(product.getName())
                        .amount(product.getAmount())
                        .price(product.getPrice())
                        .build();

        product.setName(productDto.getName().trim());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());

        this.productRepository.saveAndFlush(product);

        this.logProductService.saveLogUpdateProduct(product, oldProduct);
    }

    private void saveProduct(ProductDto productDto) {
        ProductEntity product =
                new ProductEntity
                        .Builder()
                        .name(productDto.getName().trim())
                        .amount(productDto.getAmount())
                        .price(productDto.getPrice())
                        .build();

        this.productRepository.saveAndFlush(product);

        this.logProductService.saveLogNewProduct(product);
    }

    @Transactional
    public void deleteProductById(BigInteger id) {
        try {
            ProductEntity product = this.productRepository.getReferenceById(id);

            this.productRepository.deleteById(product.getId());

            this.logProductService.saveLogDeleteProduct(product);
        } catch (EntityNotFoundException e) {
            throw new ValidationException("Product not found!");
        }
    }

    private static void validateProductDto(ProductDto productDto) {
        if (productDto.getName() == null || productDto.getName().isBlank())
            throw new ValidationException("Name is empty or null");

        if (productDto.getAmount() == null || productDto.getAmount() <= 0)
            throw new ValidationException("Amount is null or less than zero");

        if (productDto.getPrice() == null || productDto.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Price is null or less than zero");
    }
}
