public class MVCPatternTest {
    public static void main(String[] args) {
        Student model = retrieveStudentFromDatabase();

        StudentView view = new StudentView();

        StudentController controller = new StudentController(model, view);

        System.out.println("Initial Student Details:");
        controller.updateView();

        controller.setStudentGrade("A+");
        controller.setStudentName("John Doe");

        System.out.println("\nUpdated Student Details:");
        controller.updateView();
    }

    private static Student retrieveStudentFromDatabase() {
        Student student = new Student();
        student.setName("Jane Doe");
        student.setId("S12345");
        student.setGrade("A");
        return student;
    }
}
