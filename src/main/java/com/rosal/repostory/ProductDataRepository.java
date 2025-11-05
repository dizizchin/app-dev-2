package com.rosal.repostory;

import com.rosal.entity.ProductData;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataRepository extends CrudRepository<ProductData, Integer> {
}
