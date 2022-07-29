package com.News.NewsPaper.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.News.NewsPaper.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}