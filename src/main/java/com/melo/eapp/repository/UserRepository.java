package com.melo.eapp.repository;

import org.springframework.stereotype.Repository;

import com.melo.eapp.Enum.UserRole;
import com.melo.eapp.entity.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long>{

	Optional<Users> findFirstByEmail(String email);

	Users findByRole(UserRole userRole);

}
