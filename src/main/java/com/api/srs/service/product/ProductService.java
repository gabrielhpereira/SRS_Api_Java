package com.api.srs.service.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.ProductRepository;
import com.api.srs.vo.product.ProductVo;
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
        return this.productRepository.listAllProducts();
    }

    public List<ProductVo> listProductByFilters(ProductDto productDto) {
        return this.productRepository.listProductByFilters(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getAmount()
        );
    }

    @Transactional
    public void saveOrUpdateProduct(ProductDto productDto) {
        validateProductDto(productDto);
        if(productDto.getId().equals(BigInteger.ZERO) || productDto.getId() == null)
            this.saveProduct(productDto);
        else
            this.updateProduct(productDto);
    }

    private void updateProduct(ProductDto productDto){
        ProductEntity product = this.productRepository.getReferenceById(productDto.getId());
        product.setName(productDto.getName().toUpperCase().trim());
        product.setPrice(productDto.getPrice());
        product.setAmount(productDto.getAmount());

        this.productRepository.saveAndFlush(product);

        this.logProductService.saveLogNewProduct(product);
    }

    private void saveProduct(ProductDto productDto){
        ProductEntity product =
                new ProductEntity
                        .Builder()
                        .id(productDto.getId())
                        .name(productDto.getName().trim().toUpperCase())
                        .amount(productDto.getAmount())
                        .price(productDto.getPrice())
                        .build();

        this.productRepository.saveAndFlush(product);

        this.logProductService.saveLogNewProduct(product);
    }

    @Transactional
    public void deleteProductById(BigInteger id) {
        ProductEntity product = this.productRepository.getReferenceById(id);

        this.productRepository.deleteById(id);

        this.logProductService.saveLogDeleteProduct(product.getId() + " - " + product.getName().trim());
    }

    private static void validateProductDto(ProductDto productDto) {
        if (productDto.getName().isBlank() || productDto.getName() == null)
            throw new ValidationException("Name is empty or null");

        if (productDto.getAmount() == null || productDto.getAmount() <= 0)
            throw new ValidationException("Amount is null or less than zero");

        if (productDto.getPrice().compareTo(BigDecimal.ZERO) <= 0 || productDto.getPrice() == null)
            throw new ValidationException("Price is null or less than zero");
    }
}
