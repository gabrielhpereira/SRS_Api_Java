package com.api.srs.service.product;

import com.api.srs.dto.product.ProductDto;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.enums.MessageGenericEnum;
import com.api.srs.repository.product.ProductRepository;

import static com.api.srs.shared.Validator.*;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final LogProductService logProductService;

  public List<ProductDto> listAllProducts() {
    List<ProductDto> listProduct = this.productRepository.listAllProducts();

    if (listProduct.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listProduct;
  }

  public List<ProductDto> listProductByFilters(ProductDto productDto) {
    List<ProductDto> listProduct = this.productRepository.listProductByFilters(
        validateStringNullOrEmpty(productDto.name()),
        validateBigDecimalNullOrLessEqualZero(productDto.price()),
        validateIntegerNullOrLessEqualZero(productDto.amount())
    );

    if (listProduct.isEmpty()) throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());

    return listProduct;
  }

  @Transactional
  public void saveOrUpdateProduct(ProductDto productDto) {
    validateProductDto(productDto);

    if (validateBigIntegerNullOrZero(productDto.id()) == null)
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
      throw new ValidationException(MessageGenericEnum.NOT_FOUND.getMessage());
    }
  }

  private static void validateProductDto(ProductDto productDto) {
    if (validateStringNullOrEmpty(productDto.name()) == null)
      throw new ValidationException(MessageGenericEnum.NAME_NULL_OR_EMPTY.getMessage());

    if (validateIntegerNullOrLessEqualZero(productDto.amount()) == null)
      throw new ValidationException(MessageGenericEnum.AMOUNT_NULL_OR_LESS_THAN_ZERO.getMessage());

    if (validateBigDecimalNullOrLessEqualZero(productDto.price()) == null)
      throw new ValidationException(MessageGenericEnum.PRICE_NULL_OR_LESS_THAN_ZERO.getMessage());
  }
}
