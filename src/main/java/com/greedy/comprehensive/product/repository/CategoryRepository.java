package com.greedy.comprehensive.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.greedy.comprehensive.product.entity.Category;

public interface CategoryRepository extends JpaRepository <Category, Long> {

}
