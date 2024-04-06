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
            System.out.println("\n======================================");
            System.out.println("|          Swim School System        |");
            System.out.println("======================================");
            System.out.println("1. View Swim Student Information");
            System.out.println("2. View Swim Lesson Details");
            System.out.println("3. View Instructor Schedule");
            System.out.println("4. Add New Swim Student");
            System.out.println("5. Award Swim Qualification");
            System.out.println("6. Move Swim Student from Waiting List");
            System.out.println("0. Quit");
            System.out.print("\nPlease select an option (0-6): ");

            int option = -1;
            while (true) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Consume the invalid input
                    // Prompt again after invalid input
                    System.out.print("Please enter an option: ");
                    continue;
                }
                option = scanner.nextInt();
                scanner.nextLine();
                break; // Exit the loop if input is valid
            }

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
                    System.out.println("Exiting the Swim School System. Goodbye!");
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
                    break;
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

        int studentNumber = 0;
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            studentNumber = scanner.nextInt();
            scanner.nextLine();
            if (studentNumber < 1 || studentNumber > students.size()) {
                System.out.println("Invalid student number. Please try again: ");
            } else {
                break; // break the loop if input is valid
            }
        }

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
                    break; // stop at the first match
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
        // Display the current number of attendees out of the max capacity
        String attendeesInfo = String.format("%d/%d", lesson.getAttendees().size(), MAX_STUDENTS_PER_LESSON);
        System.out.println((i + 1) + ". " + lesson.getDay() + " at " + lesson.getStartTime() + " (" + lesson.getLevel() + ") - " + attendeesInfo);
    }
    System.out.print("Enter the number of the lesson: ");

    int lessonNumber = 0;
    while (true) {
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
            continue;
        }
        lessonNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline
        if (lessonNumber < 1 || lessonNumber > lessons.size()) {
            System.out.println("Invalid lesson number. Please try again: ");
        } else {
            break; // break the loop if input is valid
        }
    }

    SwimLesson selectedLesson = lessons.get(lessonNumber - 1);
    System.out.println("\nLesson Details:");
    System.out.println("Day: " + selectedLesson.getDay());
    System.out.println("Time: " + selectedLesson.getStartTime());
    System.out.println("Level: " + selectedLesson.getLevel());
    Instructor instructor = selectedLesson.getTakenBy();
    System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "No instructor assigned"));
    // Here again, show the count in the detailed view
    String attendeesInfoDetailed = String.format("%d/%d students enrolled", selectedLesson.getAttendees().size(), MAX_STUDENTS_PER_LESSON);
    System.out.println(attendeesInfoDetailed);

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
        int instructorNumber = 0;
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
                continue;
            }
            instructorNumber = scanner.nextInt();
            scanner.nextLine(); // consume the newline character after the number
            if (instructorNumber < 1 || instructorNumber > sortedInstructors.size()) {
                System.out.println("Invalid instructor number. Please try again: ");
            } else {
                break; // valid input received, exit the loop
            }
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
        String level = "Novice"; // Directly setting the student's level to Novice
        System.out.println("\nAdding a new swim student at Novice level.");
        String level1 = scanner.nextLine().trim();
        System.out.println("Would you like to add the student directly to the waiting list? (yes/no): ");
        String directToWaitingList = scanner.nextLine().trim();
        if ("yes".equalsIgnoreCase(directToWaitingList)) {
            addStudentToWaitingList(scanner, level1); // Directly add the student to the waiting list.
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

            return;
        }

        // Let the user select a class
        System.out.print("Select a class by number: ");
        while (!scanner.hasNextInt()) {
            System.out.println("That's not a valid number. Please enter a number.");
            scanner.next();
        }
        int classSelection = scanner.nextInt() - 1;
        scanner.nextLine();

        if (classSelection < 1 || classSelection > availableLessons.size()) {
            System.out.println("Invalid class selection.");
            return; // Exit the method if an invalid selection is made
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
        System.out.print("Enter the name of the new swim student (letters only): ");
        String name = scanner.nextLine().trim();
        // Validate the name to contain only letters. Include spaces to allow for full names.
        while (!name.matches("[a-zA-Z ]+")) {
            System.out.println("Invalid name. Names must only contain letters. Please try again: ");
            name = scanner.nextLine().trim();
        }

        SwimStudent newStudent = new SwimStudent(name, level);
        selectedLesson.addAttendee(newStudent);
        newStudent.addLesson(selectedLesson);
        System.out.println(name + " has been added to the " + level + " lesson on " + selectedLesson.getDay() + " at " + selectedLesson.getStartTime());

        // Also, add the student to the global list of students
        students.add(newStudent);
    }

    private static void addStudentToWaitingList(Scanner scanner, String level) {
        String name = "";
        boolean isValidName = false;
        System.out.print("Enter the name of the new swim student for the waiting list: ");

        while (!isValidName) {
            name = scanner.nextLine().trim();

            if (name.matches("[a-zA-Z ]+")) {
                isValidName = true;
            } else {
                System.out.println("Invalid name. Please enter a name using letters only.");
                System.out.print("Enter the name of the new swim student for the waiting list: ");
            }
        }

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

        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a number.");
            scanner.next(); // Discard the invalid input
        }
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume the remaining newline character

        if (choice >= 0 && choice < instructors.size()) {
            return instructors.get(choice);
        } else {
            System.out.println("Invalid instructor selection. Please select a valid number.");
            return selectInstructor(scanner);
        }
    }

    private static SwimStudent selectSwimStudent(Scanner scanner) {
        System.out.println("\nSelect a swim student from the list:");
        students.sort(Comparator.comparing(SwimStudent::getName));
        for (int i = 0; i < students.size(); i++) {
            // Display each student's name along with their level
            System.out.println((i + 1) + ". " + students.get(i).getName() + " - Level: " + students.get(i).getLevel());
        }

        while (!scanner.hasNextInt()) {
            System.out.println("That's not a number. Please enter a number.");
            scanner.next(); // Discard the invalid input
        }
        int choice = scanner.nextInt() - 1;
        scanner.nextLine(); // Consume the remaining newline character

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
                while (!scanner.hasNextInt()) { 
                    scanner.next(); 
                    System.out.println("Invalid input. Please enter a valid number for distance.");
                    System.out.print("Enter distance (e.g., 100): ");
                }
                int distance = scanner.nextInt();
                scanner.nextLine();
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
            System.out.print("Enter distance (e.g., 20, 400): ");
            while (!scanner.hasNextInt()) {
                scanner.next(); // Consume the non-integer input
                System.out.println("Invalid input. Please enter a number for distance.");
                System.out.print("Enter distance (e.g., 20, 400): ");
            }
            int distance = scanner.nextInt();
            scanner.nextLine();  // Consume newline.
            if (!hasQualification(selectedStudent, "DistanceSwim", String.valueOf(distance))) {
                selectedStudent.addQualification(new DistanceSwim(selectedInstructor.getName(), distance));
                System.out.println("Awarded Distance Swim qualification.");

                updateLevelAndAddToWaitingList(selectedStudent, distance);
            } else {
                System.out.println("Student already has this qualification.");
            }
        }
    }

    private static void updateLevelAndAddToWaitingList(SwimStudent student, int distance) {
        // Assume novice students can only receive a 20-meter qualification for level advancement
        if ("Novice".equals(student.getLevel()) && distance == 20) {
            // Upgrade the student to "Improver" level
            student.setLevel("Improver");
            // Add the student to the waiting list for the next level
            waitingList.addStudent(student);
            System.out.println(student.getName() + " has been upgraded to Improver and added to the waiting list for level transfer.");
        } else if ("Improver".equals(student.getLevel()) && distance == 400) {
            // Upgrade the student to "Advanced" level
            student.setLevel("Advanced");
            // Add the student to the waiting list for the next level
            waitingList.addStudent(student);
            System.out.println(student.getName() + " has been upgraded to Advanced and added to the waiting list for level transfer.");
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

            String input = scanner.next();
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
            String input = scanner.nextLine();
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

        student.addLesson(newClass);

        System.out.println(student.getName() + " has been moved to the " + newClass.getLevel() + " class on " + newClass.getDay() + " at " + newClass.getStartTime() + ".");
    }

    private static void preloadData() {
        // Adding instructors
        Instructor instructor1 = new Instructor("Jane Bober Smith");
        Instructor instructor2 = new Instructor("John Iezick Jrackov");
        Instructor instructor3 = new Instructor("Alice Waters Bober");
        Instructor instructor4 = new Instructor("Mark Reno Hobbus");
        Instructor instructor5 = new Instructor("Ella Fitzgerald Predus");
        Instructor instructor6 = new Instructor("Luis Mongus");
        instructors.addAll(Arrays.asList(instructor1, instructor2, instructor3, instructor4, instructor5, instructor6));

        // Lesson times, days, and levels
        String[] times = {"17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
        String[] days = {"Monday", "Wednesday", "Friday"};
        String[] levels = {"Novice", "Improver", "Advanced"};

        // Algotithm to assign instructors to each class 
        int instructorIndex = 0;
        for (String day : days) {
            for (String time : times) {
                for (String level : levels) {
                    SwimLesson lesson = new SwimLesson(day, time, level);

                    Instructor assignedInstructor = instructors.get(instructorIndex % instructors.size());
                    lesson.setTakenBy(assignedInstructor);
                    lessons.add(lesson);
                    instructorIndex++;
                }
            }
        }

        String[] studentNames = {"Alice Johnson Joke", "Bob Brown Miles", "Charlie Davis Bird", "Diana Smith Liandry", "Evan Wright Frud", "Fiona Glen Openhur"};
        String[] studentLevels = {"Novice", "Improver", "Advanced", "Novice", "Improver", "Advanced"};
        for (int i = 0; i < studentNames.length; i++) {
            SwimStudent student = new SwimStudent(studentNames[i], studentLevels[i]);
            // Attempt to place each student in the first available lesson of their level
            for (SwimLesson lesson : lessons) {
                if (lesson.getLevel().equals(student.getLevel()) && lesson.getAttendees().size() < MAX_STUDENTS_PER_LESSON) {
                    lesson.addAttendee(student);
                    break;
                }
            }
            students.add(student);
        }
    }

}
