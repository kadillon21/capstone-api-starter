package org.yearup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yearup.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    @Query("SELECT name FROM categories")
    List<Category> findAllCategories();
}
