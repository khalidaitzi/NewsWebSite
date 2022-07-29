package com.News.NewsPaper.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.News.NewsPaper.Model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByOwnerContaining(String owner);
}