import java.util.*;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private int enrolledStudents;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean registerStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean removeStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public String getDetails() {
        return courseCode + " - " + title + "\nDescription: " + description + "\nSchedule: " + schedule +
               "\nAvailable Slots: " + getAvailableSlots() + "\n";
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.registerStudent()) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
            return true;
        }
        return false;
    }
}

public class CourseRegistrationSystem {
    private static Map<String, Course> courseDatabase = new HashMap<>();
    private static Map<String, Student> studentDatabase = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeCourses();
        initializeStudents();

        System.out.println("🎓 Welcome to the Student Course Registration System 🎓");

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    displayCourses();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    viewRegisteredCourses();
                    break;
                case 5:
                    System.out.println("👋 Goodbye! Thank you for using the system.");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courseDatabase.put("CS101", new Course("CS101", "Introduction to Programming", "Learn the basics of programming.", 30, "Mon-Wed 10:00 AM - 11:30 AM"));
        courseDatabase.put("CS102", new Course("CS102", "Data Structures", "Explore data organization techniques.", 25, "Tue-Thu 12:00 PM - 1:30 PM"));
        courseDatabase.put("CS103", new Course("CS103", "Database Systems", "Understand database design and querying.", 20, "Fri 2:00 PM - 4:00 PM"));
    }

    private static void initializeStudents() {
        studentDatabase.put("S123", new Student("S123", "John Doe"));
        studentDatabase.put("S124", new Student("S124", "Jane Smith"));
    }

    private static void displayCourses() {
        System.out.println("\n===== Available Courses =====");
        for (Course course : courseDatabase.values()) {
            System.out.println(course.getDetails());
        }
    }

    private static void registerForCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("❌ Invalid Student ID.");
            return;
        }

        System.out.print("Enter the Course Code to register: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null) {
            System.out.println("❌ Invalid Course Code.");
            return;
        }

        if (student.registerCourse(course)) {
            System.out.println("✅ Successfully registered for the course: " + course.getTitle());
        } else {
            System.out.println("❌ Registration failed. Either the course is full or you are already registered.");
        }
    }

    private static void dropCourse() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("❌ Invalid Student ID.");
            return;
        }

        System.out.print("Enter the Course Code to drop: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);

        if (course == null) {
            System.out.println("❌ Invalid Course Code.");
            return;
        }

        if (student.dropCourse(course)) {
            System.out.println("✅ Successfully dropped the course: " + course.getTitle());
        } else {
            System.out.println("❌ Drop failed. You are not registered for this course.");
        }
    }

    private static void viewRegisteredCourses() {
        System.out.print("\nEnter your Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);

        if (student == null) {
            System.out.println("❌ Invalid Student ID.");
            return;
        }

        System.out.println("\n===== Registered Courses for " + student.getName() + " =====");
        List<Course> courses = student.getRegisteredCourses();
        if (courses.isEmpty()) {
            System.out.println("You have not registered for any courses.");
        } else {
            for (Course course : courses) {
                System.out.println(course.getDetails());
            }
        }
    }
}
