# Individual Exercise 2: REST, validation & error handling

Your are provided with a domain and its application controller already implemented. It consists on Students, Classrooms and allocations
of these students in a classroom in a given day of the week. Basically it controls that the classrooms are not overoccupied which
is very important in these weird days. It also takes care not to allocate students twice in a given classroom on the same
day of the week.

Note also that the RoomsApplication.java file implements a CommandLineRunner in order to create some students, classrooms,
and allocations. In this way the application is populated for you to be able to play with them.

### REST application
You need to implement the following REST calls
* GET all the students: /students
* GET one student: /students/{id}
* GET all the classrooms: /classrooms
* GET one classroom: /classroom/{name}
* GET all the classrooms WITHOUT their allocations: /classrooms?allocations=none
* GET all the classrooms that are already full on a given day of the week: /classrooms?full=true&day={day}
* GET all the classrooms that are not fully occupied on a given day of the week: /classrooms?full=false&day={day}

* POST a new student: /students  (the body of the http call should contain the student)
* POST a new allocation of a student in a given classroom, and a given day of the week: classroom/{name}/allocations?student={id}&dayOfWeek={dayName}

You are not allowed to modify any class or file under the domain package. You already have the DTOs objects in the application layer.

### Validation
The front-end should make sure that the received objects are valid. Concretely, it should enforce the following rules:
* The student's name and secondName must begin in capital letters and must be at least 3 characters long
* The email should follow the pattern xxx@xxx.xxx (a word followed by @ followed by at least two words separated by a period ".")
* The day of the week must be one of the following {monday, tuesday, ...} in an ignore case fashion.
Recall that validation errors throw exceptions that you want to translate for the end-user.

### Error handling
The front-end needs to translate domain and application level exceptions to http errors giving information to the end user. Also the validation
errors should be sent in a proper format to the end user. You may want to have exceptions at the front-end layer.

