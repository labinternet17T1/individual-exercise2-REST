package cat.tecnocampus.rooms;

import cat.tecnocampus.rooms.application.RoomController;
import cat.tecnocampus.rooms.application.dtos.StudentDTO;
import cat.tecnocampus.rooms.domain.Classroom;
import cat.tecnocampus.rooms.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.DayOfWeek;
import java.util.HashMap;

@SpringBootApplication
public class RoomsApplication implements CommandLineRunner {
    private RoomController roomController;

    public static void main(String[] args) {
        SpringApplication.run(RoomsApplication.class, args);
    }

    @Autowired
    public void setRoomController(RoomController roomController) {
        this.roomController = roomController;
    }

    @Override
    public void run(String... strings) throws Exception {
        HashMap<String, Student> students = new HashMap<>();
        HashMap<String, Classroom> classrooms = new HashMap<>();

        var student = new Student("Pepe", "Pino", "pepe@tecnocampus.cat");
        var pepeId = student.getId();
        students.put(pepeId, student);
        student = new Student("Pepa", "Pino", "pepa@tecnocampus.cat");
        var pepaId = student.getId();
        students.put(pepaId, student);
        student = new Student("Maria", "Fontaneda", "maria@tecnocampus.cat");
        students.put(student.getId(), student);
        student = new Student("Mario", "Fontaneda", "mario@tecnocampus.cat");
        students.put(student.getId(), student);

        classrooms.put("104", new Classroom.ClassroomBuilder().capacity(4).name("104").orientation("S").plugs(true).build());
        classrooms.put("105", new Classroom.ClassroomBuilder().capacity(2).name("105").orientation("SW").plugs(true).build());
        classrooms.put("106", new Classroom.ClassroomBuilder().capacity(3).name("106").orientation("N").plugs(true).build());
        classrooms.put("107", new Classroom.ClassroomBuilder().capacity(2).name("107").orientation("W").plugs(true).build());

        classrooms.get("104").allocate(students.get(pepeId), DayOfWeek.valueOf("MONDAY"));
        classrooms.get("104").allocate(students.get(pepeId), DayOfWeek.valueOf("TUESDAY"));
        classrooms.get("104").allocate(students.get(pepeId), DayOfWeek.valueOf("WEDNESDAY"));
        classrooms.get("105").allocate(students.get(pepeId), DayOfWeek.valueOf("THURSDAY"));
        classrooms.get("105").allocate(students.get(pepeId), DayOfWeek.valueOf("FRIDAY"));
        classrooms.get("105").allocate(students.get(pepaId), DayOfWeek.valueOf("FRIDAY"));

        roomController.setStudents(students);
        roomController.setClassrooms(classrooms);

        roomController.getFullyOccupiedClassrooms("friday").forEach(System.out::println);
        roomController.getNotFullyOccupiedClassrooms("friday").forEach(System.out::println);

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("Hola");
        studentDTO.setSecondName("Student");
        studentDTO.setEmail("hola@gmail.com");
        roomController.setNewStudent(studentDTO);

        roomController.allocateStudentInClassroom(studentDTO, "104", "THURSDAY");
    }
}
