package servertraining.docker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import servertraining.docker.dao.StudentRepository;
import servertraining.docker.exception.StudentNotFoundException;
import servertraining.docker.model.Student;
import servertraining.docker.service.SequenceGeneratorService;
import servertraining.docker.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public List<Student> retrieveAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student saveStudentDetails(Student student) {
		student.setId(sequenceGeneratorService.generateSequence(Student.SEQUENCE_NAME));
		return studentRepository.insert(student);
	}

	@Override
	public List<Student> saveAllStudentDetails(List<Student> studentList) {
		studentList.forEach(student -> student.setId(sequenceGeneratorService.generateSequence(Student.SEQUENCE_NAME)));
		return studentRepository.insert(studentList);
	}

	@Override
	public Student findStudentById(long studentId) {
		return studentRepository.findById(studentId).orElse(null);
	}

	@Override
	public Student updateStudentDetails(Student student) throws StudentNotFoundException {
		Student studentDb = studentRepository.findById(student.getId()).orElse(null);
		if(ObjectUtils.isEmpty(studentDb)) {
			throw new StudentNotFoundException("Student Not Found with ID : " + student.getId());
		}
		return studentRepository.save(student);
	}

	@Override
	public List<Student> findStudentByDivision(String division) {
		return studentRepository.findAllStudentByDivision(division);
	}

	@Override
	public void deleteStudentById(long id) {
		studentRepository.deleteById(id);
	}

	@Override
	public void deleteAllByIds(List<Long> ids) {
		studentRepository.deleteAllById(ids);
	}
	
}
