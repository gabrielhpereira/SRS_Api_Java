package com.api.srs.service.product;

import com.api.srs.dto.product.LogProductDto;
import com.api.srs.entity.product.LogProductEntity;
import com.api.srs.entity.product.ProductEntity;
import com.api.srs.repository.product.LogProductRepository;
import com.api.srs.shared.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogProductService {

  private final LogProductRepository logProductRepository;

  public List<LogProductDto> listAllLogProduct(BigInteger idProduct) {
    return this.logProductRepository.listAllLogProduct(idProduct);
  }

  public void saveLogUpdateProduct(ProductEntity newProduct, ProductEntity oldProduct) {
    StringBuilder sb = new StringBuilder();

    if (!newProduct.getName().equalsIgnoreCase(oldProduct.getName()))
      sb.append("Name changed from ")
          .append(oldProduct.getName())
          .append(" to ").append(newProduct.getName())
          .append("\n");

    if (newProduct.getPrice().compareTo(oldProduct.getPrice()) != 0)
      sb.append("Price changed from ")
          .append(oldProduct.getPrice()).append(" to ")
          .append(newProduct.getPrice())
          .append("\n");

    if (!newProduct.getAmount().equals(oldProduct.getAmount()))
      sb.append("Amount changed from ")
          .append(oldProduct.getAmount())
          .append(" to ")
          .append(newProduct.getAmount());

    if (!sb.toString().isEmpty())
      this.logProductRepository.save(
          LogProductEntity
              .builder()
              .productId(newProduct.getId())
              .description("Product " + newProduct.getId() + " : \n\n" + sb.toString())
              .date(DateTime.nowDate())
              .build());
  }

  public void saveLogNewProduct(ProductEntity product) {
    this.logProductRepository.save(
        LogProductEntity
            .builder()
            .productId(product.getId())
            .description("Product " + product.getId() + " - " + product.getName() + " has been created!")
            .date(DateTime.nowDate())
            .build());
  }

  public void saveLogDeleteProduct(ProductEntity product) {
    this.logProductRepository.save(
        LogProductEntity
            .builder()
            .productId(product.getId())
            .description("Product " + product.getId() + " - " + product.getName() + " has been deleted!")
            .date(DateTime.nowDate())
            .build());
  }
}
