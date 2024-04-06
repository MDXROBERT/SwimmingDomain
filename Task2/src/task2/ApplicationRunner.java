/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task2;

/**
 *
 * @author iarin
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class ApplicationRunner {

    private static List<SwimStudent> students = new ArrayList<>();
    private static List<Instructor> instructors = new ArrayList<>();
    private static List<SwimLesson> lessons = new ArrayList<>();
    private static WaitingList waitingList = new WaitingList();
    private static final int MAX_STUDENTS_PER_LESSON = 4;

    public static void main(String[] args) {
        // Pre-load some dummy data
        preloadData();

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("\nWelcome to the Swim School System");
            System.out.println("1. View Swim Student Information");
            System.out.println("2. View Swim Lesson Details");
            System.out.println("3. View Instructor Schedule");
            System.out.println("4. Add New Swim Student");
            System.out.println("5. Award Swim Qualification");
            System.out.println("6. Move Swim Student from Waiting List");
            System.out.println("0. Quit");
            System.out.print("Please enter an option: ");

            int option = scanner.nextInt();
           

            switch (option) {
                case 1:
                    viewSwimStudentInformation(scanner);
                    break;
                case 2:
                    viewSwimLessonDetails(scanner);
                    break;
                case 3:
                    viewInstructorSchedule(scanner);
                    break;
                case 4:
                    addNewSwimStudent(scanner);
                    break;

                case 5:
                    awardSwimQualification(scanner);
                    break;
                case 6:
                    moveSwimFromWaitingList(scanner);
                    break;

                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }

        scanner.close();
    }

    private static void viewSwimStudentInformation(Scanner scanner) {
        System.out.println("\nSelect a swim student from the list:");
        students.sort(Comparator.comparing(SwimStudent::getName));
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() + " (" + students.get(i).getLevel() + ")");
        }
        System.out.print("Enter the number of the student: ");
        int studentNumber = scanner.nextInt();
        if (studentNumber < 1 || studentNumber > students.size()) {
            System.out.println("Invalid student number.");
            return;
        }

        scanner.nextLine(); // Consume newline left after number input.
        SwimStudent student = students.get(studentNumber - 1);
        System.out.println("\nStudent Information:");
        System.out.println("Name: " + student.getName());
        System.out.println("Level: " + student.getLevel());

        // Check if the student is on the waiting list
        if (waitingList.getStudents().contains(student)) {
            System.out.println(student.getName() + " is on the waiting list.");
        } else {
            for (SwimLesson lesson : lessons) {
                if (lesson.getAttendees().contains(student)) {
                    System.out.println("Class Day: " + lesson.getDay());
                    System.out.println("Class Time: " + lesson.getStartTime());
                    System.out.println("Instructor: " + (lesson.getTakenBy() != null ? lesson.getTakenBy().getName() : "No instructor assigned"));
                    break;
                }
            }
        }

        System.out.println("Qualifications:");
        for (Qualification qualification : student.getQualifications()) {
            if (qualification instanceof DistanceSwim) {
                DistanceSwim distanceSwim = (DistanceSwim) qualification;
                System.out.println("Distance Swim: " + distanceSwim.getDistance() + " meters awarded by " + distanceSwim.getAwardedBy());
            } else if (qualification instanceof PersonalSurvival) {
                PersonalSurvival personalSurvival = (PersonalSurvival) qualification;
                System.out.println("Personal Survival Level: " + personalSurvival.getLevel() + " awarded by " + personalSurvival.getAwardedBy());
            }
        }
    }

    private static void viewSwimLessonDetails(Scanner scanner) {
        System.out.println("\nSelect a swim lesson from the list:");
        for (int i = 0; i < lessons.size(); i++) {
            SwimLesson lesson = lessons.get(i);
            System.out.println((i + 1) + ". " + lesson.getDay() + " at " + lesson.getStartTime() + " (" + lesson.getLevel() + ")");
        }
        System.out.print("Enter the number of the lesson: ");
        int lessonNumber = scanner.nextInt();
        if (lessonNumber < 1 || lessonNumber > lessons.size()) {
            System.out.println("Invalid lesson number.");
            return;
        }

        SwimLesson selectedLesson = lessons.get(lessonNumber - 1);
        System.out.println("\nLesson Details:");
        System.out.println("Day: " + selectedLesson.getDay());
        System.out.println("Time: " + selectedLesson.getStartTime());
        System.out.println("Level: " + selectedLesson.getLevel());
        Instructor instructor = selectedLesson.getTakenBy();
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "No instructor assigned"));

        if (selectedLesson.getAttendees().isEmpty()) {
            System.out.println("No students are enrolled in this class.");
        } else {
            System.out.println("Enrolled students:");
            for (SwimStudent student : selectedLesson.getAttendees()) {
                System.out.println("- " + student.getName() + " (" + student.getLevel() + ")");
            }
        }
    }

    private static void viewInstructorSchedule(Scanner scanner) {
        System.out.println("\nSelect an instructor from the list:");
        // Create a temporary list to sort the instructors without altering the original list
        List<Instructor> sortedInstructors = new ArrayList<>(instructors);
        sortedInstructors.sort(Comparator.comparing(Instructor::getName));

        for (int i = 0; i < sortedInstructors.size(); i++) {
            System.out.println((i + 1) + ". " + sortedInstructors.get(i).getName());
        }

        System.out.print("Enter the number of the instructor: ");
        scanner.nextLine(); 
        int instructorNumber = Integer.parseInt(scanner.nextLine()); 
        if (instructorNumber < 1 || instructorNumber > sortedInstructors.size()) {
            System.out.println("Invalid instructor number.");
            return;
        }

        Instructor selectedInstructor = sortedInstructors.get(instructorNumber - 1);
        System.out.println("\nSchedule for Instructor: " + selectedInstructor.getName());
        boolean hasClasses = false;

        for (SwimLesson lesson : lessons) {
            if (lesson.getTakenBy().equals(selectedInstructor)) {
                System.out.println("Day: " + lesson.getDay() + ", Time: " + lesson.getStartTime() + ", Level: " + lesson.getLevel());
                System.out.print("Enrolled Students: ");
                if (lesson.getAttendees().isEmpty()) {
                    System.out.println("None");
                } else {
                    lesson.getAttendees().forEach(student -> System.out.print(student.getName() + " (" + student.getLevel() + "), "));
                    System.out.println(); 
                }
                hasClasses = true;
            }
        }

        if (!hasClasses) {
            System.out.println("This instructor has no scheduled classes.");
        }
    }

    private static void addNewSwimStudent(Scanner scanner) {
        System.out.println("\nEnter the level of the new swim student (Novice/Improver/Advanced): ");
        scanner.nextLine(); 
        String level = scanner.nextLine().trim(); 
        System.out.println("Would you like to add the student directly to the waiting list? (yes/no): ");
        String directToWaitingList = scanner.nextLine().trim();
        if ("yes".equalsIgnoreCase(directToWaitingList)) {
            addStudentToWaitingList(scanner, level); 
            return; 
        }

        // Display available classes for the chosen level
        System.out.println("Available " + level + " classes:");
        List<SwimLesson> availableLessons = new ArrayList<>();
        for (SwimLesson lesson : lessons) {
            if (lesson.getLevel().equalsIgnoreCase(level)) {
                System.out.println((availableLessons.size() + 1) + ". " + lesson.getDay() + " at " + lesson.getStartTime() + " - "
                        + lesson.getAttendees().size() + " enrolled (Instructor: "
                        + (lesson.getTakenBy() != null ? lesson.getTakenBy().getName() : "No instructor assigned") + ")");
                availableLessons.add(lesson);
            }
        }

        if (availableLessons.isEmpty()) {
            System.out.println("No available classes for this level.");
           
            return; // Exit the method if no classes are available
        }

        // Let the user select a class
        System.out.print("Select a class by number: ");
        int classSelection = scanner.nextInt();
        scanner.nextLine(); 

        if (classSelection < 1 || classSelection > availableLessons.size()) {
            System.out.println("Invalid class selection.");
            return; 
        }

        // Get the selected class
        SwimLesson selectedLesson = availableLessons.get(classSelection - 1);

        if (selectedLesson.getAttendees().size() >= MAX_STUDENTS_PER_LESSON) {
            System.out.println("Selected class is full. Would you like to add the student to the waiting list instead? (yes/no): ");
            String response = scanner.nextLine().trim();
            if ("yes".equalsIgnoreCase(response)) {
                addStudentToWaitingList(scanner, level);
                return;
            } else {
                System.out.println("No action taken. Please try selecting a different class or check back later.");
                return;
            }
        }

        // Proceed to add the new student to the class since it's not full
        System.out.print("Enter the name of the new swim student: ");
        String name = scanner.nextLine();

        SwimStudent newStudent = new SwimStudent(name, level);
        selectedLesson.addAttendee(newStudent);
        newStudent.addLesson(selectedLesson);
        System.out.println(name + " has been added to the " + level + " lesson on " + selectedLesson.getDay() + " at " + selectedLesson.getStartTime());

        
        students.add(newStudent);
    }


    private static void addStudentToWaitingList(Scanner scanner, String level) {
        System.out.print("Enter the name of the new swim student for the waiting list: ");
        String name = scanner.nextLine();
        SwimStudent newStudent = new SwimStudent(name, level);
        waitingList.addStudent(newStudent);
        System.out.println(name + " has been added to the waiting list.");
        students.add(newStudent);
    }

    private static boolean hasQualification(SwimStudent student, String qualificationType, String detail) {
        for (Qualification qualification : student.getQualifications()) {
            if (qualification instanceof DistanceSwim && "DistanceSwim".equals(qualificationType)) {
                DistanceSwim distanceSwim = (DistanceSwim) qualification;
                if (String.valueOf(distanceSwim.getDistance()).equals(detail)) {
                    return true;
                }
            } else if (qualification instanceof PersonalSurvival && "PersonalSurvival".equals(qualificationType)) {
                PersonalSurvival personalSurvival = (PersonalSurvival) qualification;
                if (personalSurvival.getLevel().equals(detail)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Instructor selectInstructor(Scanner scanner) {
        System.out.println("Select an instructor:");
        instructors.sort(Comparator.comparing(Instructor::getName));
        for (int i = 0; i < instructors.size(); i++) {
            System.out.println((i + 1) + ". " + instructors.get(i).getName());
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return instructors.get(choice - 1);
    }

    private static SwimStudent selectSwimStudent(Scanner scanner) {
        System.out.println("\nSelect a swim student from the list:");
        students.sort(Comparator.comparing(SwimStudent::getName)); 
        for (int i = 0; i < students.size(); i++) {
            // Display each student's name along with their level
            SwimStudent student = students.get(i);
            System.out.println((i + 1) + ". " + student.getName() + " - Level: " + student.getLevel());
        }
        System.out.print("Enter the number of the student: ");
        int choice = Integer.parseInt(scanner.nextLine()) - 1; /
        if (choice >= 0 && choice < students.size()) {
            return students.get(choice);
        } else {
            System.out.println("Invalid selection, please try again.");
            return selectSwimStudent(scanner); 
        }
    }

    private static void awardSwimQualification(Scanner scanner) {
        Instructor selectedInstructor = selectInstructor(scanner);
        SwimStudent selectedStudent = selectSwimStudent(scanner);

        if ("Advanced".equalsIgnoreCase(selectedStudent.getLevel())) {
            System.out.println("1. Distance Swim\n2. Personal Survival");
            System.out.print("Select the type of qualification to award: ");
            int type = scanner.nextInt();
            scanner.nextLine();  
            if (type == 1) {
                System.out.print("Enter distance (e.g., 100): ");
                int distance = scanner.nextInt();
                scanner.nextLine();  // Consume newline.
                if (!hasQualification(selectedStudent, "DistanceSwim", String.valueOf(distance))) {
                    selectedStudent.addQualification(new DistanceSwim(selectedInstructor.getName(), distance));
                    System.out.println("Awarded Distance Swim qualification.");
                } else {
                    System.out.println("Student already has this qualification.");
                }
            } else if (type == 2) {
                System.out.print("Enter level (e.g., Bronze, Silver, Gold): ");
                String level = scanner.nextLine();
                if (!hasQualification(selectedStudent, "PersonalSurvival", level)) {
                    selectedStudent.addQualification(new PersonalSurvival(selectedInstructor.getName(), level));
                    System.out.println("Awarded Personal Survival qualification.");
                } else {
                    System.out.println("Student already has this qualification.");
                }
            }
        } else {
            
            System.out.print("Enter distance (e.g., 100): ");
            int distance = scanner.nextInt();
            scanner.nextLine();  // Consume newline.
            if (!hasQualification(selectedStudent, "DistanceSwim", String.valueOf(distance))) {
                selectedStudent.addQualification(new DistanceSwim(selectedInstructor.getName(), distance));
                System.out.println("Awarded Distance Swim qualification.");
                // Automatically update level and possibly add to waiting list based on criteria.
                updateLevelAndAddToWaitingList(selectedStudent, distance);
            } else {
                System.out.println("Student already has this qualification.");
            }
        }
    }

    private static void updateLevelAndAddToWaitingList(SwimStudent student, int distance) {
        boolean levelUp = false;
        String currentLevel = student.getLevel();

        // Update level based on the distance qualification for Novice and Improver levels
        if ("Novice".equals(currentLevel)) {
            if (distance == 20) {
                student.setLevel("Improver");
                levelUp = true;
            }
        } else if ("Improver".equals(currentLevel)) {
            if (distance == 400) {
                student.setLevel("Advanced");
                levelUp = true;
            }
        }

        // Handle level up and waiting list addition
        if (levelUp) {
            waitingList.addStudent(student);
            System.out.println(student.getName() + " has been upgraded to " + student.getLevel() + " and added to the waiting list for level transfer.");
        }
    }

    private static SwimStudent selectStudentFromWaitingList(Scanner scanner) {
        while (true) {
            System.out.println("Select a student from the waiting list:");
            List<SwimStudent> waitingStudents = waitingList.getStudents();
            if (waitingStudents.isEmpty()) {
                System.out.println("No students currently on the waiting list.");
                return null; 
            }
            for (int i = 0; i < waitingStudents.size(); i++) {
                System.out.println((i + 1) + ". " + waitingStudents.get(i).getName() + " - Level: " + waitingStudents.get(i).getLevel());
            }
            System.out.print("Enter your choice: ");

            String input = scanner.next(); // Directly use the input
            try {
                int choice = Integer.parseInt(input.trim()) - 1; 
                if (choice >= 0 && choice < waitingStudents.size()) {
                    return waitingStudents.get(choice);
                } else {
                    System.out.println("Invalid choice. Please enter a number from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private static SwimLesson selectNewClassForStudent(Scanner scanner, SwimStudent student) {
        while (true) {
            System.out.println("Select a new class for " + student.getName() + " (Level: " + student.getLevel() + "):");
            List<SwimLesson> availableClasses = new ArrayList<>();
            for (SwimLesson lesson : lessons) {
                if (lesson.getLevel().equalsIgnoreCase(student.getLevel()) && lesson.getAttendees().size() < MAX_STUDENTS_PER_LESSON) {
                    availableClasses.add(lesson);
                }
            }

            for (int i = 0; i < availableClasses.size(); i++) {
                SwimLesson lesson = availableClasses.get(i);
                System.out.println((i + 1) + ". " + lesson.getDay() + " at " + lesson.getStartTime() + " - " + lesson.getLevel());
            }

            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (choice >= 0 && choice < availableClasses.size()) {
                    return availableClasses.get(choice);
                } else {
                    System.out.println("Invalid choice. Please enter a number from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void moveSwimFromWaitingList(Scanner scanner) {
        if (waitingList.getStudents().isEmpty()) {
            System.out.println("The waiting list is currently empty.");
            return;
        }

        SwimStudent student = selectStudentFromWaitingList(scanner);
        if (student == null) {
            // Handle case where no valid selection was made
            return;
        }

        SwimLesson newClass = selectNewClassForStudent(scanner, student);
        if (newClass == null) {
            // Handle case where no valid class selection was made
            return;
        }

        
        waitingList.removeStudent(student);
        newClass.addAttendee(student);

        // Assuming student has a method to update their lesson list
        student.addLesson(newClass);

        System.out.println(student.getName() + " has been moved to the " + newClass.getLevel() + " class on " + newClass.getDay() + " at " + newClass.getStartTime() + ".");
    }

    private static void preloadData() {
        // Adding instructors
        Instructor instructor1 = new Instructor("Jane Bober Smith");
        Instructor instructor2 = new Instructor("John Iezick Jrackov");
        Instructor instructor3 = new Instructor("Alice Waters");
        Instructor instructor4 = new Instructor("Mark Reno");
        Instructor instructor5 = new Instructor("Ella Fitzgerald");
        Instructor instructor6 = new Instructor("Luis Armstrong");
        instructors.addAll(Arrays.asList(instructor1, instructor2, instructor3, instructor4, instructor5, instructor6));

        // Lesson times, days, and levels
        String[] times = {"17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
        String[] days = {"Monday", "Wednesday", "Friday"};
        String[] levels = {"Novice", "Improver", "Advanced"};

        // Use a round-robin approach to assign instructors to each class
        int instructorIndex = 0;
        for (String day : days) {
            for (String time : times) {
                for (String level : levels) {
                    SwimLesson lesson = new SwimLesson(day, time, level);
                    // Assign instructors in a round-robin fashion
                    Instructor assignedInstructor = instructors.get(instructorIndex % instructors.size());
                    lesson.setTakenBy(assignedInstructor);
                    lessons.add(lesson);
                    instructorIndex++;
                }
            }
        }

        String[] studentNames = {"Alice Johnson", "Bob Brown", "Charlie Davis", "Diana Smith", "Evan Wright", "Fiona Glen"};
        String[] studentLevels = {"Novice", "Improver", "Advanced", "Novice", "Improver", "Advanced"};
        for (int i = 0; i < studentNames.length; i++) {
            SwimStudent student = new SwimStudent(studentNames[i], studentLevels[i]);
            // Attempt to place each student in the first available lesson of their level
            for (SwimLesson lesson : lessons) {
                if (lesson.getLevel().equals(student.getLevel()) && lesson.getAttendees().size() < MAX_STUDENTS_PER_LESSON) {
                    lesson.addAttendee(student);
                    break; // Stop looking once the student is placed
                }
            }
            students.add(student);
        }
    }

}
