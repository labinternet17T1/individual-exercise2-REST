package cat.tecnocampus.rooms.application;

import cat.tecnocampus.rooms.application.dtos.AllocationDTO;
import cat.tecnocampus.rooms.application.dtos.ClassroomDTO;
import cat.tecnocampus.rooms.application.dtos.StudentDTO;
import cat.tecnocampus.rooms.application.exceptions.ClassroomDoesNotExistException;
import cat.tecnocampus.rooms.application.exceptions.StudentDoesNotExistException;
import cat.tecnocampus.rooms.domain.Allocation;
import cat.tecnocampus.rooms.domain.Classroom;
import cat.tecnocampus.rooms.domain.Student;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomController {
    private HashMap<String, Student> students;
    private HashMap<String, Classroom> classrooms;

    public void setStudents(HashMap<String, Student> students) {
        this.students = students;
    }

    public void setClassrooms(HashMap<String, Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public List<StudentDTO> getStudents() {
        return students.values().stream().map(this::student2StudentDTO).collect(Collectors.toList());
    }

    public StudentDTO getStudent(String id) {
        if (students.containsKey(id)) {
            return student2StudentDTO(students.get(id));
        }
        throw new StudentDoesNotExistException("Student " + id + " doesn't exist");
    }

    public List<ClassroomDTO> getClassrooms() {
        return classrooms.values().stream().map(this::classroom2classroomDTO).collect(Collectors.toList());
    }

    public ClassroomDTO getClassroom(String name) {
        if (classrooms.containsKey(name)) {
            return classroom2classroomDTO(classrooms.get(name));
        }
        throw new ClassroomDoesNotExistException(name);
    }

    public List<ClassroomDTO> getClassroomsNoAllocations() {
        return classrooms.values().stream().map(this::classroom2classroomDTOnoAllocations).collect(Collectors.toList());
    }

    public List<ClassroomDTO> getFullyOccupiedClassrooms(String dayOfWeek) {
        return classrooms.values().stream()
                .filter(c -> c.isFull(DayOfWeek.valueOf(dayOfWeek.toUpperCase())))
                .map(this::classroom2classroomDTO)
                .collect(Collectors.toList());
    }

    public List<ClassroomDTO> getNotFullyOccupiedClassrooms(String dayOfWeek) {
        return classrooms.values().stream()
                .filter(c -> !c.isFull(DayOfWeek.valueOf(dayOfWeek.toUpperCase())))
                .map(this::classroom2classroomDTO)
                .collect(Collectors.toList());
    }

    /********************************************************************
     * Translations between DTOs and domain objects
     ********************************************************************/

    private StudentDTO student2StudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setName(student.getName());
        studentDTO.setSecondName(student.getSecondName());

        return studentDTO;
    }

    private Student studentDTO2Student(StudentDTO studentDTO) {
        Student student = new Student();
        student.setId(studentDTO.getId());
        student.setEmail(studentDTO.getEmail());
        student.setName(studentDTO.getName());
        student.setSecondName(studentDTO.getSecondName());

        return student;
    }

    private ClassroomDTO classroom2classroomDTOnoAllocations(Classroom classroom) {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setName(classroom.getName());
        classroomDTO.setCapacity(classroom.getCapacity());
        classroomDTO.setOrientation(classroom.getOrientation());
        classroomDTO.setPlugs(classroom.isPlugs());

        return classroomDTO;
    }

    private ClassroomDTO classroom2classroomDTO(Classroom classroom) {
        ClassroomDTO classroomDTO = classroom2classroomDTOnoAllocations(classroom);
        classroomDTO.setAllocations(classroomDTOgetAllocationsDTO(classroom));

        return classroomDTO;
    }

    private List<AllocationDTO> classroomDTOgetAllocationsDTO(Classroom classroom) {
        return classroom.getAllocations().stream().map(this::allocation2AllocationDTO).collect(Collectors.toList());
    }

    private AllocationDTO allocation2AllocationDTO(Allocation allocation) {
        AllocationDTO allocationDTO = new AllocationDTO();
        allocationDTO.setStudent(student2StudentDTO(allocation.getStudent()));
        allocationDTO.setDayOfWeek(allocation.getDayOfWeek().name());

        return allocationDTO;
    }
}
