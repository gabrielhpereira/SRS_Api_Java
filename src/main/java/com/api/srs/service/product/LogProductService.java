package com.api.srs.service.product;

import com.api.srs.dto.product.LogProductDto;
import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.LogProductRepository;
import com.api.srs.shared.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.api.srs.shared.GenericLog.*;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogProductService {

  private final LogProductRepository logProductRepository;

  public List<LogProductDto> listAllLogProduct(BigInteger idProduct) {
    return this.logProductRepository.listAllLogProduct(idProduct);
  }

  protected void saveLogUpdateProduct(ProductEntity newProduct, ProductEntity oldProduct) {
    StringBuilder sb = new StringBuilder();

    sb.append(updateEntity(newProduct.getName(), oldProduct.getName(), "Name"));
    sb.append(updateEntity(newProduct.getPrice().setScale(2, RoundingMode.CEILING),
                           oldProduct.getPrice().setScale(2, RoundingMode.CEILING), "Price"));
    sb.append(updateEntity(newProduct.getAmount(), oldProduct.getAmount(), "Amount"));

    if (!sb.toString().isEmpty())
      this.logProductRepository.save(
          LogProductEntity
              .builder()
              .productId(newProduct.getId())
              .description("Product " + newProduct.getId() + " : \n\n" + sb)
              .date(DateTime.nowDate())
              .build());
  }

  protected void saveLogNewProduct(ProductEntity product) {
    this.logProductRepository.save(
        LogProductEntity
            .builder()
            .productId(product.getId())
            .description("Product " + product.getId() + " - " + product.getName() + " has been created!")
            .date(DateTime.nowDate())
            .build());
  }

  protected void saveLogDeleteProduct(ProductEntity product) {
    this.logProductRepository.save(
        LogProductEntity
            .builder()
            .productId(product.getId())
            .description("Product " + product.getId() + " - " + product.getName() + " has been deleted!")
            .date(DateTime.nowDate())
            .build());
  }
}
