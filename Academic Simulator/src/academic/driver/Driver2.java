package academic.driver;
import academic.model.*;
import java.sql.*;


public class Driver2 {
    public static void main(String[] args) {
        // Informasi koneksi database
        String url = "jdbc:mysql://localhost:3306/PBO_Database";
        String username = "root";
        String password = "wilson17";

        // Melakukan koneksi ke database
        Connection connection = null;
        try {
            // Memuat driver JDBC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi ke database MySQL berhasil!");

            ContactDatabase connections = new ContactDatabase(url, username, password);

            connections.printAllContacts();
            connections.printAllContactsByGroup();
            connections.printAllContactsWithEmailContains("hero");
            //connections.shutdown();

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