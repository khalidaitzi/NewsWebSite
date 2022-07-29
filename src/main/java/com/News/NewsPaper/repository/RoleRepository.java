package com.News.NewsPaper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.News.NewsPaper.Model.ERole;
import com.News.NewsPaper.Model.Role;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	//Java Optional as Return Type
	Optional<Role> findByName(ERole name);
}