package academic.driver;

import academic.model.Controller;
import java.sql.*;
import java.util.Scanner;


/**
 * @author 12S22011 Wilson Eksaudi Sihombing
 * @author 
 */

public class Driver1 {

    public static void main(String[] _args) {
        // Informasi koneksi database
        String url = "jdbc:mysql://localhost:3306/Academic_Simulator";
        String username = "----";
        String password = "-------";
    
        // Melakukan koneksi ke database
        Connection connection = null;
        try {
            // Memuat driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            // Membuat koneksi ke database
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi ke database MySQL berhasil!");

            Controller controller = new Controller(url, username, password);

            Scanner sc = new Scanner(System.in);
            while(true){
                String str = sc.nextLine();
                if(str.equals("---")){
                    break;
                }

                String[] tokens = str.split("#");
                String command = tokens[0];
                switch (command){
                    case "student-add":
                        controller.addStudent(tokens);
                        break;
                    case "course-add":
                        controller.addCourse(tokens);
                        break;
                    case "enrollment-add":
                        controller.addEnrollment(tokens);
                        break;
                    case "lecturer-add":
                        controller.addLecture(tokens);
                        break;
                    case "enrollment-grade":
                        controller.Add_EnrGrade(tokens);
                        break;
                    case "student-details":
                        controller.Students_detail(tokens);
                        break;
                    case "enrollment-remedial":
                        controller.Enrollment_remedial(tokens);
                        break;
                    case "course-open":
                        controller.Course_open(tokens);
                        break;
                    case "course-history":
                        controller.Course_history(tokens);
                        break;
                    case "student-transcript":
                        controller.delete_OldTranscript(tokens);
                        controller.Students_detail(tokens);
                        controller.print_transcript(tokens);
                        break;
                    default:
                        System.out.println("Invalid command");
                        break;

            }
        }

        controller.use_printEverythingDetails();
        //System.out.println(controller);
        sc.close();
        controller.shutdown();

       } catch (ClassNotFoundException e) {
            System.out.println("Tidak dapat menemukan driver JDBC MySQL!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal!");
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Koneksi ke database MySQL ditutup.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }

}