package com.News.NewsPaper.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.News.NewsPaper.Model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostid(Long postid);

}