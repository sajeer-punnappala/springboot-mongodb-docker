package servertraining.docker.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import servertraining.docker.model.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, Long> {
	
	List<Student> findAllStudentByDivision(String division);
	
}