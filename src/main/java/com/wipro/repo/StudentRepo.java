package com.wipro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.entity.Student;
import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepo extends JpaRepository<Student, Long>{
		Optional<Student> findByUsername(String username);
}
