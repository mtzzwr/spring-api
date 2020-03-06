package com.mtzzwr.odonto.repository;

import org.springframework.data.repository.CrudRepository;

import com.mtzzwr.odonto.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
