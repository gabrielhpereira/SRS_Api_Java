package com.api.srs.service.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.enums.product.MessageProductEnum;
import com.api.srs.repository.product.ProductRepository;
import com.api.srs.shared.Validator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final LogProductService logProductService;

    public List<ProductDto> listAllProducts() {
        List<ProductDto> listVo = this.productRepository.listAllProducts();

        if (listVo.isEmpty()) throw new ValidationException(MessageProductEnum.NOT_FOUND.getMessage());

        return listVo;
    }

    public List<ProductDto> listProductByFilters(ProductDto productDto) {
        List<ProductDto> listVo = this.productRepository.listProductByFilters(
                Validator.validateStringNullOrEmpty(productDto.name()),
                Validator.validateBigDecimalNullOrLessEqualZero(productDto.price()),
                Validator.validateIntegerNullOrLessEqualZero(productDto.amount())
        );

        if (listVo.isEmpty()) throw new ValidationException(MessageProductEnum.NOT_FOUND.getMessage());

        return listVo;
    }

    @Transactional
    public void saveOrUpdateProduct(ProductDto productDto) {
        validateProductDto(productDto);

        if (productDto.id() == null || productDto.id().equals(BigInteger.ZERO))
            this.saveProduct(productDto);
        else
            this.updateProduct(productDto);
    }

    private void updateProduct(ProductDto productDto) {
        ProductEntity product = this.productRepository.getReferenceById(productDto.id());
        ProductEntity oldProduct = new ProductEntity(product);

        product.setName(productDto.name().trim());
        product.setPrice(productDto.price());
        product.setAmount(productDto.amount());

        this.productRepository.saveAndFlush(product);

        this.logProductService.saveLogUpdateProduct(product, oldProduct);
    }

    private void saveProduct(ProductDto productDto) {
        ProductEntity product =
                ProductEntity
                        .builder()
                        .name(productDto.name().trim())
                        .amount(productDto.amount())
                        .price(productDto.price())
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
            throw new ValidationException(MessageProductEnum.NOT_FOUND.getMessage());
        }
    }

    private static void validateProductDto(ProductDto productDto) {
        if (productDto.name() == null || productDto.name().isBlank())
            throw new ValidationException(MessageProductEnum.NAME_NULL_OR_EMPTY.getMessage());

        if (productDto.amount() == null || productDto.amount() <= 0)
            throw new ValidationException(MessageProductEnum.AMOUNT_NULL_OR_LESS_THAN_ZERO.getMessage());

        if (productDto.price() == null || productDto.price().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException(MessageProductEnum.PRICE_NULL_OR_LESS_THAN_ZERO.getMessage());
    }
}
