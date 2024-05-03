package academic.model;

import java.sql.SQLException; // Import kelas SQLException untuk menangani kesalahan yang terjadi saat mengakses database
import java.sql.Statement; // Import kelas Statement untuk mengeksekusi pernyataan SQL
import java.sql.PreparedStatement; // Import kelas PreparedStatement untuk mengeksekusi pernyataan SQL yang telah dikompilasi
import java.sql.Connection; // Import kelas Connection untuk mengelola koneksi ke database
import java.sql.ResultSet;// Import kelas ResultSet untuk menampung hasil kueri SQL

/**
 * @author 12S22011 Wilson Eksaudi Sihombing
 * @author 
 */

public class Controller extends AbstractDb {
    public Controller(String url, String username, String password) throws SQLException{
        super(url, username, password);
    }

    protected void createTables() throws SQLException{
        String StudentDDL = "CREATE TABLE IF NOT EXISTS Students_tbl ("
            + "Nim VARCHAR(30) NOT NULL,"
            + "Name VARCHAR(100) NOT NULL,"
            + "Year VARCHAR(10) NOT NULL,"
            + "Prodi VARCHAR(100) NOT NULL,"
            + "PRIMARY KEY(Nim)"
            + ")";
    
        String CourseDDL = "CREATE TABLE IF NOT EXISTS course_tbl ("
            + "course_id VARCHAR(100) PRIMARY KEY,"
            + "course_name VARCHAR(100) NOT NULL,"
            + "sks INTEGER NOT NULL,"
            + "grade VARCHAR(5) NOT NULL"
            + ")";
    
        String lectureDDL = "CREATE TABLE IF NOT EXISTS lecture_tbl (" 
            + "lecture_id VARCHAR(30),"
            + "name VARCHAR(100) NOT NULL,"
            + "initial VARCHAR(10) NOT NULL,"
            + "email VARCHAR(100) NOT NULL,"
            + "study_program VARCHAR(100) NOT NULL"
            + ")";
    
        String EnrollmentDDL = "CREATE TABLE IF NOT EXISTS Enrollment_tbl(" 
            + "id_student VARCHAR(20),"
            + "id_course VARCHAR(20),"
            + "year VARCHAR(10),"
            + "semester VARCHAR(10),"
            + "status VARCHAR(5),"
            + "grade_before VARCHAR(5),"
            + "status_remedial VARCHAR(50)"
            + ")";

        String EnrollmentDDLH = "CREATE TABLE IF NOT EXISTS EnrollmentHistory_tbl(" 
            + "id_student VARCHAR(20),"
            + "id_course VARCHAR(20),"
            + "year VARCHAR(10),"
            + "semester VARCHAR(10),"
            + "status VARCHAR(5),"
            + "grade_before VARCHAR(5)"
            + ")";

        String CourseOpenDDL = "CREATE TABLE IF NOT EXISTS CourseOpening_tbl(" 
            + "id_course VARCHAR(20),"
            + "year VARCHAR(10),"
            + "semester VARCHAR(10),"
            + "guardian VARCHAR(20)"
            + ")";
    
        // step 3
        Statement statement = this.getConection().createStatement();
    
        statement.execute(StudentDDL);
        statement.execute(CourseDDL);
        statement.execute(lectureDDL);
        statement.execute(EnrollmentDDL);
        statement.execute(EnrollmentDDLH);
        statement.execute(CourseOpenDDL);
    
        // step 5
        statement.close();
    }
    

    protected void seedTables() throws SQLException{
        String cleanUpSQL[] = {
            "DELETE FROM Students_tbl",
            "DELETE FROM course_tbl",
            "DELETE FROM lecture_tbl",
            "DELETE FROM Enrollment_tbl",
            "DELETE FROM EnrollmentHistory_tbl"


        };

        // step 3
        Statement statement = this.getConection().createStatement();

        for(String sql : cleanUpSQL){
            int affected = statement.executeUpdate(sql);
            statement.execute(sql);
        }
    }

    //adding student
    public void addStudent(String[] tokens) {
        String id_student = tokens[1];
        String name_student = tokens[2];
        String year = tokens[3];
        String prodi = tokens[4];
    
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement checkStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL
    
        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
            
            // Buat kueri SQL untuk memeriksa apakah mahasiswa dengan ID tertentu sudah ada dalam database
            String checkStudentSQL = "SELECT * FROM Students_tbl WHERE Nim = ?"; // Kueri SQL untuk memeriksa mahasiswa berdasarkan ID
            checkStatement = connection.prepareStatement(checkStudentSQL);// Persiapkan kueri SQL untuk dieksekusi
            checkStatement.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa yang akan dicek (dari argumen)
            
            // Eksekusi kueri SQL
            resultSet = checkStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            
            // Jika terdapat baris dalam hasil kueri, artinya mahasiswa sudah ada dalam database
            if (resultSet.next()) { // Jika terdapat baris dalam hasil kueri SQL
                System.out.println("Mahasiswa dengan ID " + id_student + " sudah ada dalam database."); // Tampilkan pesan bahwa mahasiswa sudah ada dalam database
            } else {
                // Jika tidak ada baris, tambahkan mahasiswa ke database
                String insertSQL = "INSERT INTO Students_tbl (Nim, Name, Year, Prodi) VALUES (?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data mahasiswa ke database
                PreparedStatement statement = connection.prepareStatement(insertSQL); // Persiapkan kueri SQL untuk dieksekusi
                statement.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
                statement.setString(2, name_student); // Set parameter kueri SQL dengan nama mahasiswa (dari argumen)
                statement.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
                statement.setString(4, prodi); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)
    
                statement.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data mahasiswa ke database
                
                System.out.println("Data mahasiswa berhasil disimpan ke database."); // Tampilkan pesan bahwa data mahasiswa berhasil disimpan ke database
            }
        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database 
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }
    

    //adding course
    public void  addCourse(String[] tokens){
        String id_course = tokens[1];
        String name_course = tokens[2];
        int sks = Integer.parseInt(tokens[3]);
        String grade = tokens[4];

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement checkStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
            
            // Buat kueri SQL untuk memeriksa apakah mata kuliah dengan ID tertentu sudah ada dalam database
            String checkCourseSQL = "SELECT * FROM course_tbl WHERE course_id = ?"; // Kueri SQL untuk memeriksa mata kuliah berdasarkan ID
            checkStatement = connection.prepareStatement(checkCourseSQL); // Persiapkan kueri SQL untuk dieksekusi
            checkStatement.setString(1, id_course); // Set parameter kueri SQL dengan ID mata kuliah yang akan dicek (dari argumen)
            
            // Eksekusi kueri SQL
            resultSet = checkStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            
            // Jika terdapat baris dalam hasil kueri, artinya mata kuliah sudah ada dalam database
            if (resultSet.next()) { // Jika terdapat baris dalam hasil kueri SQL
                System.out.println("Mata kuliah dengan ID " + id_course + " sudah ada dalam database."); // Tampilkan pesan bahwa mata kuliah sudah ada dalam database
            } else {
                // Jika tidak ada baris, tambahkan mata kuliah ke database
                String insertSQL = "INSERT INTO course_tbl (course_id, course_name, sks, grade) VALUES (?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data mata kuliah ke database
                PreparedStatement statement = connection.prepareStatement(insertSQL); // Persiapkan kueri SQL untuk dieksekusi
                statement.setString(1, id_course); // Set parameter kueri SQL dengan ID mata kuliah (dari argumen)
                statement.setString(2, name_course); // Set parameter kueri SQL dengan nama mata kuliah (dari argumen)
                statement.setInt(3, sks); // Set parameter kueri SQL dengan jumlah SKS mata kuliah (dari argumen)
                statement.setString(4, grade); // Set parameter kueri SQL dengan grade mata kuliah (dari argumen)
    
                statement.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data mata kuliah ke database
                
                System.out.println("Data mata kuliah berhasil disimpan ke database."); // T
            }

        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database 
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }

        
    }    

    //adding enrollment
    public void addEnrollment(String[] tokens){
        String id_course = tokens[1];
        String id_student = tokens[2];
        String year = tokens[3];
        String semester = tokens[4];

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement checkStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        PreparedStatement checkStatementHistory = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL
        ResultSet resultSetHistory = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL


        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
            
            // Buat kueri SQL untuk memeriksa apakah mata kuliah dengan ID tertentu sudah ada dalam database
            String checkEnrollmentSQL = "SELECT * FROM Enrollment_tbl WHERE id_student = ? AND id_course = ? AND year = ? AND semester = ?"; // Kueri SQL untuk memeriksa mata kuliah berdasarkan ID
            String checkEnrollmentSQLHistory = "SELECT * FROM EnrollmentHistory_tbl WHERE id_student = ? AND id_course = ? AND year = ? AND semester = ?"; // Kueri SQL untuk memeriksa mata kuliah berdasarkan ID

            checkStatement = connection.prepareStatement(checkEnrollmentSQL); // Persiapkan kueri SQL untuk dieksekusi
            checkStatementHistory = connection.prepareStatement(checkEnrollmentSQLHistory); // Persiapkan kueri SQL untuk dieksekusi

            // untuk memasukkan ke tabel enrollment
            checkStatement.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa yang akan dicek (dari argumen)
            checkStatement.setString(2, id_course); // Set parameter kueri SQL dengan ID mata kuliah yang akan dicek (dari argumen)
            checkStatement.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
            checkStatement.setString(4, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)

            //Memasukkan ke tabel history
            checkStatementHistory.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa yang akan dicek (dari argumen)
            checkStatementHistory.setString(2, id_course); // Set parameter kueri SQL dengan ID mata kuliah yang akan dicek (dari argumen)
            checkStatementHistory.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
            checkStatementHistory.setString(4, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)

            
            // Eksekusi kueri SQL
            resultSet = checkStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            resultSetHistory = checkStatementHistory.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            
            // Jika terdapat baris dalam hasil kueri, artinya mahasiswa sudah ada dalam database
            if (resultSet.next() && resultSetHistory.next()) { // Jika terdapat baris dalam hasil kueri SQL
                System.out.println("Mahasiswa dengan ID " + id_student + " sudah terdaftar pada mata kuliah dengan ID " + id_course + " pada tahun " + year + " semester " + semester + "."); // Tampilkan pesan bahwa mahasiswa sudah terdaftar pada mata kuliah tersebut
            } else {
                // Jika tidak ada baris, tambahkan mahasiswa ke mata kuliah
                String insertSQL = "INSERT INTO Enrollment_tbl (id_student, id_course, year, semester) VALUES (?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data mahasiswa ke database
                String insertSQLHistory = "INSERT INTO EnrollmentHistory_tbl (id_student, id_course, year, semester) VALUES (?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data mahasiswa ke database

                PreparedStatement statement = connection.prepareStatement(insertSQL); // Persiapkan kueri SQL untuk dieksekusi
                PreparedStatement statementHistory = connection.prepareStatement(insertSQLHistory); // Persiapkan kueri SQL untuk dieksekusi

                statement.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
                statement.setString(2, id_course); // Set parameter kueri SQL dengan ID mata kuliah (dari argumen)
                statement.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
                statement.setString(4, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)

                statementHistory.setString(1, id_student); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
                statementHistory.setString(2, id_course); // Set parameter kueri SQL dengan ID mata kuliah (dari argumen)
                statementHistory.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
                statementHistory.setString(4, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)


                statement.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data mahasiswa ke database
                statementHistory.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data mahasiswa ke database

                System.out.println("Data mahasiswa berhasil terdaftar pada mata kuliah."); // Tampilkan pesan bahwa data mahasiswa berhasil disimpan ke database
            }
        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }

    }

    //adding lecture
    public void addLecture(String[] tokens){
        String id = tokens[1];
        String name = tokens[2];
        String initial = tokens[3];
        String email = tokens[4];
        String study_program = tokens[5];

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement checkStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
            
            // Buat kueri SQL untuk memeriksa apakah dosen dengan ID tertentu sudah ada dalam database
            String checkLectureSQL = "SELECT * FROM lecture_tbl WHERE lecture_id = ?"; // Kueri SQL untuk memeriksa dosen berdasarkan ID
            checkStatement = connection.prepareStatement(checkLectureSQL); // Persiapkan kueri SQL untuk dieksekusi
            checkStatement.setString(1, id); // Set parameter kueri SQL dengan ID dosen yang akan dicek (dari argumen)
            
            // Eksekusi kueri SQL
            resultSet = checkStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            
            // Jika terdapat baris dalam hasil kueri, artinya dosen sudah ada dalam database
            if (resultSet.next()) { // Jika terdapat baris dalam hasil kueri SQL
                System.out.println("Dosen dengan ID " + id + " sudah ada dalam database."); // Tampilkan pesan bahwa dosen sudah ada dalam database
            } else {
                // Jika tidak ada baris, tambahkan dosen ke database
                String insertSQL = "INSERT INTO lecture_tbl (lecture_id, name, initial, email, study_program) VALUES (?, ?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data dosen ke database
                PreparedStatement statement = connection.prepareStatement(insertSQL); // Persiapkan kueri SQL untuk dieksekusi

                statement.setString(1, id); // Set parameter kueri SQL dengan ID dosen (dari argumen)
                statement.setString(2, name); // Set parameter kueri SQL dengan nama dosen (dari argumen)
                statement.setString(3, initial); // Set parameter kueri SQL dengan inisial dosen (dari argumen)
                statement.setString(4, email); // Set parameter kueri SQL dengan email dosen (dari argumen)
                statement.setString(5, study_program); // Set parameter kueri SQL dengan program studi dosen (dari argumen)
    
                statement.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data dosen ke database
                
                System.out.println("Data dosen berhasil disimpan ke database."); // Tampilkan pesan bahwa data dosen berhasil disimpan ke database  

            }
            } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
                System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
            }
            
    }

    //adding enrollment grade
    public void Add_EnrGrade(String[] tokens){
        String course_id = tokens[1];
        String std_id = tokens[2];
        String year = tokens[3];
        String semester = tokens[4];
        String grade = tokens[5];
    
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement updateStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        PreparedStatement updateStatementHistory = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
    
        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
    
            // Buat kueri SQL untuk memperbarui nilai grade di tabel Enrollment_tbl
            String updateEnrollmentSQL = "UPDATE Enrollment_tbl SET status = ? WHERE id_course = ? AND id_student = ? AND year = ? AND semester = ?"; // Kueri SQL untuk memperbarui nilai grade mahasiswa
            String updateEnrollmentSQLHistory = "UPDATE EnrollmentHistory_tbl SET status = ? WHERE id_course = ? AND id_student = ? AND year = ? AND semester = ?"; // Kueri SQL untuk memperbarui nilai grade mahasiswa
    
            // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            updateStatement = connection.prepareStatement(updateEnrollmentSQL); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            updateStatementHistory = connection.prepareStatement(updateEnrollmentSQLHistory); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
    
            // Setel nilai parameter PreparedStatement sesuai dengan parameter yang diberikan
            updateStatement.setString(1, grade);
            updateStatement.setString(2, course_id);
            updateStatement.setString(3, std_id);
            updateStatement.setString(4, year);
            updateStatement.setString(5, semester);

            // Setel nilai parameter PreparedStatement sesuai dengan parameter yang diberikan
            updateStatementHistory.setString(1, grade);
            updateStatementHistory.setString(2, course_id);
            updateStatementHistory.setString(3, std_id);
            updateStatementHistory.setString(4, year);
            updateStatementHistory.setString(5, semester);

    
            // Jalankan pernyataan PreparedStatement untuk memperbarui nilai grade di tabel Enrollment_tbl
            int rowsAffected = updateStatement.executeUpdate();
            int rowsAffectedHistory = updateStatementHistory.executeUpdate();
    
            if (rowsAffected > 0 && rowsAffectedHistory > 0) {
                System.out.println("Grade mahasiswa berhasil diperbarui.");
            } else {
                System.out.println("Tidak ada entri yang cocok dalam database untuk memperbarui grade mahasiswa.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
    }
    

    //student details
    public void Students_detail(String[] tokens){
        String id_std = tokens[1];
        int total_sks = 0;

        total_sks = CalculateSks(id_std, total_sks);

        double calculate_result = 0.00;
        calculate_result = CalculateGpa(id_std, total_sks);

        double total_seluruh = calculate_result / total_sks;
        double total_seluruh1 = 0.00;

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil data mahasiswa berdasarkan ID
            String query = "SELECT * FROM Students_tbl WHERE Nim = ?"; // Kueri SQL untuk mengambil data mahasiswa berdasarkan ID
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            statement.setString(1, id_std); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tampilkan data mahasiswa
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                String id = resultSet.getString("Nim"); // Ambil ID mahasiswa
                String name = resultSet.getString("Name"); // Ambil nama mahasiswa
                String year = resultSet.getString("Year"); // Ambil tahun masuk mahasiswa
                String prodi = resultSet.getString("Prodi"); // Ambil program studi mahasiswa

                if(total_sks == 0){
                    System.out.println(id + "|" + name + "|" + year + "|" + prodi + "|" + (String.format("%.2f", total_seluruh1) + "|" + total_sks));
                }else{
                    System.out.println(id + "|" + name + "|" + year + "|" + prodi + "|" + (String.format("%.2f", total_seluruh) + "|" + total_sks));
                }
            }
        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }

    // //calculate sks
    public int CalculateSks(String id_std, int total_sks) {
    
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null;// Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL
    
        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data
    
            // Buat kueri SQL untuk mengambil SKS untuk setiap mata kuliah yang diambil oleh mahasiswa
            String query = "SELECT c.sks FROM Enrollment_tbl e " + 
                           "JOIN Course_tbl c ON e.id_course = c.course_id " +
                           "WHERE e.id_student = ?"; // Kueri SQL untuk mengambil SKS untuk setiap mata kuliah yang diambil oleh mahasiswa
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            statement.setString(1, id_std);// Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
    
            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
    
            // Iterasi melalui hasil set dan tambahkan SKS ke total
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                total_sks += resultSet.getInt("sks"); // Tambahkan SKS ke total SKS
            }
        } catch (SQLException e) { 
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        } 
        return total_sks; // Kembalikan total SKS
    }
    
    //calculate gpa
    public double CalculateGpa(String id_std,int total_sks){
        boolean cek = false;
        double calculate_result = 0.00;
        double result = 0.00;

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try{
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil nilai grade untuk setiap mata kuliah yang diambil oleh mahasiswa
            String query = "SELECT c.sks, e.status FROM Enrollment_tbl e " + 
                           "JOIN Course_tbl c ON e.id_course = c.course_id " +
                           "WHERE e.id_student = ?"; // Kueri SQL untuk mengambil nilai grade untuk setiap mata kuliah yang diambil oleh mahasiswa

            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            statement.setString(1, id_std); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tambahkan nilai grade ke total
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                if(resultSet.getString("status").equals("None")){ // Jika nilai grade adalah "None"
                    if(!cek){ // Jika nilai cek adalah false
                        total_sks = 0; // Set total SKS ke 0
                    }else{ 
                        break;
                    }
                }else{ 
                    String grade_now = resultSet.getString("status"); // Ambil nilai grade
                    int sks_now = 0; // Inisialisasi variabel untuk menyimpan jumlah SKS
                    cek = true; // Set nilai cek menjadi true

                    sks_now = resultSet.getInt("sks"); // Ambil jumlah SKS
                    if(grade_now.equals("A")){ // Jika nilai grade adalah "A"
                        result = 4.00 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("AB")){ // Jika nilai grade adalah "AB"
                        result = 3.50 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("B")){ // Jika nilai grade adalah "B"
                        result = 3 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("BC")){ // Jika nilai grade adalah "BC"
                        result = 2.50 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("C")){ // Jika nilai grade adalah "C"
                        result = 2.00 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("D")){ // Jika nilai grade adalah "D"
                        result = 1.00 * sks_now; // Hitung nilai grade
                    }else if(grade_now.equals("E")){ // Jika nilai grade adalah "E"
                        result = 0.00 * sks_now; // Hitung nilai grade
                    }

                    calculate_result = calculate_result + result; // Tambahkan nilai grade ke total
                }
            }
        }catch (SQLException e){ // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }

        return calculate_result; // Kembalikan total nilai grade

    }

    //enrollment remedial
    public void Enrollment_remedial(String[] tokens){
        String course_id = tokens[1];
        String id_std = tokens[2];
        String year = tokens[3];
        String semester = tokens[4];
        String grade = tokens[5];

        StdRemedial(course_id, id_std, year, semester, grade);
    }

    //student remedial
    public void StdRemedial(String course_id, String id_std,String year, String semester, String grade){     

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement selecStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        PreparedStatement insertStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        PreparedStatement updategrade = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL

        try{
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            String selectgrade = "SELECT status FROM Enrollment_tbl WHERE id_course = ? AND id_student = ? AND year = ? AND semester = ?  AND status != 'None'"; // Kueri SQL untuk memperbarui nilai grade mahasiswa


            selecStatement = connection.prepareStatement(selectgrade); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL


            selecStatement.setString(1, course_id); // Set parameter kueri SQL dengan ID mata kuliah (dari argumen)
            selecStatement.setString(2, id_std); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
            selecStatement.setString(3, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
            selecStatement.setString(4, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)
            


            ResultSet resultSet = selecStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            if(resultSet.next()){
                String grade_before = resultSet.getString("status");
                
                String updateEnrollmentHistory = "UPDATE EnrollmentHistory_tbl SET grade_before = ? , status = ? WHERE id_course = ? AND id_student = ? AND year = ? AND semester = ? AND status != 'None' " ; // Kueri SQL untuk memperbarui nilai grade mahasiswa

                insertStatement = connection.prepareStatement(updateEnrollmentHistory); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
                insertStatement.setString(1, grade_before);
                insertStatement.setString(2, grade);
                insertStatement.setString(3, course_id);
                insertStatement.setString(4, id_std);
                insertStatement.setString(5, year);
                insertStatement.setString(6, semester);

                int rowsAffected = insertStatement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("Grade before mahasiswa berhasil diperbarui.");
                }else{
                    System.out.println("Tidak ada entri yang cocok dalam database untuk memperbarui grade mahasiswa.");
                }


            }

            String grade_before = resultSet.getString("status");
            // Buat kueri SQL untuk memperbarui nilai grade di tabel Enrollment_tbl
            String updateEnrollmentSQL = "UPDATE Enrollment_tbl SET  grade_before = ?, status = ?, status_remedial = ? WHERE id_course = ? AND id_student = ? AND year = ? AND semester = ? AND status != 'None'" ; // Kueri SQL untuk memperbarui nilai grade mahasiswa

            // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            updategrade = connection.prepareStatement(updateEnrollmentSQL); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL

            // Setel nilai parameter PreparedStatement sesuai dengan parameter yang diberikan
            updategrade.setString(1, grade_before);
            updategrade.setString(2, grade);   
            updategrade.setString(3, "Has remedial");  
            updategrade.setString(4, course_id);
            updategrade.setString(5, id_std);
            updategrade.setString(6, year);
            updategrade.setString(7, semester);

            // Jalankan pernyataan PreparedStatement untuk memperbarui nilai grade di tabel Enrollment_tbl
            int rowsAffected = updategrade.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Grade mahasiswa berhasil diperbarui.");
            } else {
                System.out.println("Tidak ada entri yang cocok dalam database untuk memperbarui grade mahasiswa.");
            }

        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    //course open
    public void Course_open(String[] tokens){
        String course_id = tokens[1];
        String year = tokens[2];
        String semester = tokens[3];
        String guardian = tokens[4];

        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement checkStatement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try{
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk memeriksa apakah mata kuliah dengan ID tertentu sudah ada dalam database
            String checkCourseOpeningSQL = "SELECT * FROM CourseOpening_tbl WHERE id_course = ? AND year = ? AND semester = ? AND guardian = ?"; // Kueri SQL untuk memeriksa mata kuliah berdasarkan ID
            checkStatement = connection.prepareStatement(checkCourseOpeningSQL); // Persiapkan kueri SQL untuk dieksekusi
            checkStatement.setString(1, course_id); // Set parameter kueri SQL dengan ID mata kuliah yang akan dicek (dari argumen)
            checkStatement.setString(2, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
            checkStatement.setString(3, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)
            checkStatement.setString(4, guardian); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)

            // Eksekusi kueri SQL
            resultSet = checkStatement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet
            
            // Jika terdapat baris dalam hasil kueri, artinya mata kuliah sudah ada dalam database
            if (resultSet.next()) { // Jika terdapat baris dalam hasil kueri SQL
                System.out.println("Mata kuliah dengan ID " + course_id + " sudah ada dalam database."); // Tampilkan pesan bahwa mata kuliah sudah ada dalam database
            } else {
                // Jika tidak ada baris, tambahkan mata kuliah ke database
                String insertSQL = "INSERT INTO CourseOpening_tbl (id_course, year, semester, guardian) VALUES (?, ?, ?, ?)"; // Kueri SQL untuk menyimpan data mata kuliah ke database
                PreparedStatement statement = connection.prepareStatement(insertSQL); // Persiapkan kueri SQL untuk dieksekusi
                statement.setString(1, course_id); // Set parameter kueri SQL dengan ID mata kuliah (dari argumen)
                statement.setString(2, year); // Set parameter kueri SQL dengan tahun masuk mahasiswa (dari argumen)
                statement.setString(3, semester); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)
                statement.setString(4, guardian); // Set parameter kueri SQL dengan program studi mahasiswa (dari argumen)
    
                statement.executeUpdate(); // Eksekusi kueri SQL untuk menyimpan data mata kuliah ke database
                
                System.out.println("Data mata kuliah berhasil disimpan ke database."); // Tampilkan pesan bahwa data mata kuliah berhasil disimpan ke database
            }
        }catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }

    }

    //course history
    public void Course_history(String[] tokens){
        String course_id = tokens[1];

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConection();

            String query = "SELECT c.course_id, c.course_name, c.sks, c.grade, co.year, co.semester, co.guardian " +
                            "FROM Course_tbl c INNER JOIN CourseOpening_tbl co ON c.course_id = co.id_course " +
                            "WHERE co.id_course = ? ORDER BY co.semester DESC";

            statement = connection.prepareStatement(query);
            statement.setString(1, course_id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id_course = resultSet.getString("course_id");
                String course_name = resultSet.getString("course_name");
                int sks = resultSet.getInt("sks");
                String grade = resultSet.getString("grade");
                String year = resultSet.getString("year");
                String semester = resultSet.getString("semester");
                String guardian = resultSet.getString("guardian");

                StringBuilder sb = new StringBuilder();

                String[] token_initial = guardian.split(",");
                for (String token : token_initial) {
                    String lectureQuery = "SELECT * FROM Lecture_tbl WHERE initial = ?";
                    PreparedStatement lectureStatement = connection.prepareStatement(lectureQuery);
                    lectureStatement.setString(1, token);
                    ResultSet lectureResult = lectureStatement.executeQuery();

                    if (lectureResult.next()) {
                        String email = lectureResult.getString("email");
                        sb.append(token).append(" (").append(email).append(")");
                        if (!token.equals(token_initial[token_initial.length - 1])) {
                            sb.append(";");
                        }
                    }
                }

                System.out.println(id_course + "|" + course_name + "|" + sks + "|" + grade + "|" + year + "|" + semester + "|" + sb.toString());
                sb.setLength(0);
                
                String enrollmentprint = "SELECT enr.*, crs.*, crs_open.* " + 
                                        "FROM EnrollmentHistory_tbl enr " +
                                        "JOIN course_tbl crs ON enr.id_course = crs.course_id " +
                                        "JOIN courseopening_tbl crs_open ON enr.id_course = crs_open.id_course AND enr.year = crs_open.year AND enr.semester = crs_open.semester " +
                                        "WHERE enr.id_course = ? AND enr.year = ? AND enr.semester = ?";
                                        //"WHERE enr.id_course = ?";


                PreparedStatement enrollmentStatement = connection.prepareStatement(enrollmentprint);
                enrollmentStatement.setString(1, course_id);
                enrollmentStatement.setString(2, year);
                enrollmentStatement.setString(3, semester);

                ResultSet enrollmentResult = enrollmentStatement.executeQuery();

                while (enrollmentResult.next()) {
                    String id_student = enrollmentResult.getString("id_student");
                    String status = enrollmentResult.getString("status");
                    String grade_before = enrollmentResult.getString("grade_before");

                    if (grade_before != null && !grade_before.isEmpty()) {
                        System.out.println(course_id + "|" + id_student + "|" + year + "|" + semester + "|" + status + "(" + grade_before + ")");
                    } else {
                        System.out.println(course_id + "|" + id_student + "|" + year + "|" + semester + "|" + status);
                    }
                }
            }

        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
    }

    //delete old transcript
    public void delete_OldTranscript(String[] tokens){
        String id_std = tokens[1];
    
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConection(); // Dapatkan koneksi ke database
             
            String deleteQuery = "DELETE e1 FROM Enrollment_tbl e1 " +
                                "JOIN Enrollment_tbl e2 ON e1.id_student = e2.id_student " +
                                "AND e1.id_course = e2.id_course " +
                                "AND e1.year < e2.year " +
                                "WHERE e1.id_student = ?";

            // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            statement = connection.prepareStatement(deleteQuery);
            statement.setString(1, id_std); // Set parameter kueri SQL dengan ID mahasiswa (dari argumen)
            
            // Eksekusi kueri SQL untuk menghapus transkrip lama
            int rowsAffected = statement.executeUpdate();
            
            // Tampilkan pesan berdasarkan jumlah baris yang terpengaruh
            if (rowsAffected > 0) {
                System.out.println("Transkrip lama berhasil dihapus.");
            } else {
                System.out.println("Tidak ada transkrip lama yang dihapus.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } 
    }


    //print transcript
    public void print_transcript(String[] tokens){
        String id_std = tokens[1];

        Connection connection = null; //membuat objek Connection untuk mengelola koneksi ke database
        PreparedStatement preparedStatement = null;//membuat objek preparedStatement
        ResultSet resultSet = null; //membuat objek ResultSet untuk menampung hasil kueri SQL

        try{
            connection = getConection();

            String query = "SELECT * FROM Enrollment_tbl WHERE id_student = ?"; //query untuk mengambil data mahasiswa berdasarkan ID

            preparedStatement = connection.prepareStatement(query); //persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL
            preparedStatement.setString(1, id_std); //set parameter kueri SQL dengan ID mahasiswa (dari argumen)

            resultSet = preparedStatement.executeQuery(); //eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            while (resultSet.next()) {
                String id_course = resultSet.getString("id_course");
                String id_student = resultSet.getString("id_student");
                String year = resultSet.getString("year");
                String semester = resultSet.getString("semester");
                String status = resultSet.getString("status");
                String grade_before = resultSet.getString("grade_before");

                if (grade_before != null && !grade_before.isEmpty()) {
                    System.out.println(id_course + "|" + id_student + "|" + year + "|" + semester + "|" + status + "(" + grade_before + ")");
                } else {
                    System.out.println(id_course + "|" + id_student + "|" + year + "|" + semester + "|" + status);
                }
            }

        }catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }

    //print everything details
    public void print_EverythingDetails(){
        printLecture();
        printCourse();
        printStudent();
        printEnrollment();
    }

    //konsep inner class
    public class printEverythingDetail{
        public void printEverythingDetails(){
            printLecture();
            printCourse();
            printStudent();
            printEnrollment();
        }
    }

    //mengakses inner class
    public void use_printEverythingDetails(){
        printEverythingDetail print_detail = new printEverythingDetail();
        print_detail.printEverythingDetails();
    }

    //print lecture
    public void printLecture(){
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil data dosen
            String query = "SELECT * FROM Lecture_tbl"; // Kueri SQL untuk mengambil data dosen
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tampilkan data dosen
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                String id = resultSet.getString("lecture_id"); // Ambil ID dosen
                String name = resultSet.getString("name"); // Ambil nama dosen
                String initial = resultSet.getString("initial"); // Ambil inisial dosen
                String email = resultSet.getString("email"); // Ambil email dosen
                String prodi = resultSet.getString("study_program"); // Ambil program studi dosen

                System.out.println(id + "|" + name + "|" + initial + "|" + email + "|" + prodi); // Tampilkan data dosen
            }
        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }

    }

    //print course
    public void printCourse(){
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try{
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil data mata kuliah
            String query = "SELECT * FROM Course_tbl"; // Kueri SQL untuk mengambil data mata kuliah
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tampilkan data mata kuliah
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                String course_id = resultSet.getString("course_id"); // Ambil ID mata kuliah
                String course_name = resultSet.getString("course_name"); // Ambil nama mata kuliah
                int sks = resultSet.getInt("sks"); // Ambil SKS mata kuliah
                String grade = resultSet.getString("grade"); // Ambil grade mata kuliah

                System.out.println(course_id + "|" + course_name + "|" + sks + "|" + grade); // Tampilkan data mata kuliah
            }
        }catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }

    //print enrollment
    public void printEnrollment(){
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try{
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil data pendaftaran
            String query = "SELECT * FROM EnrollmentHistory_tbl"; // Kueri SQL untuk mengambil data pendaftaran
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tampilkan data pendaftaran
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                String id_course = resultSet.getString("id_course"); // Ambil ID mata kuliah
                String id_student = resultSet.getString("id_student"); // Ambil ID mahasiswa
                String year = resultSet.getString("year"); // Ambil tahun masuk mahasiswa
                String semester = resultSet.getString("semester"); // Ambil semester mahasiswa
                String status = resultSet.getString("status"); // Ambil status mahasiswa
                String grade_before = resultSet.getString("grade_before"); // Ambil nilai grade sebelumnya

                if(grade_before != null && !grade_before.isEmpty()){
                    System.out.println(id_course + "|" + id_student + "|" + year + "|" + semester + "|" + status + "(" + grade_before + ")");
                }else{
                    System.out.println(id_course + "|" + id_student + "|" + year + "|" + semester + "|" + status);
                }
            }
        }catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }

    //print student
    public void printStudent() {
        Connection connection = null; // Inisialisasi objek Connection untuk mengelola koneksi ke database
        PreparedStatement statement = null; // Inisialisasi objek PreparedStatement untuk mengeksekusi kueri SQL
        ResultSet resultSet = null; // Inisialisasi objek ResultSet untuk menampung hasil kueri SQL

        try {
            connection = getConection(); // Dapatkan koneksi ke database untuk mengakses data

            // Buat kueri SQL untuk mengambil data mahasiswa
            String query = "SELECT * FROM Students_tbl ORDER BY Nim ASC"; // Kueri SQL untuk mengambil data mahasiswa
            statement = connection.prepareStatement(query); // Persiapkan pernyataan PreparedStatement untuk menjalankan kueri SQL

            resultSet = statement.executeQuery(); // Eksekusi kueri SQL dan simpan hasilnya dalam objek ResultSet

            // Iterasi melalui hasil set dan tampilkan data mahasiswa
            while (resultSet.next()) { // Selama masih terdapat baris dalam hasil kueri SQL
                String id = resultSet.getString("Nim"); // Ambil ID mahasiswa
                String name = resultSet.getString("Name"); // Ambil nama mahasiswa
                String year = resultSet.getString("year"); // Ambil alamat mahasiswa
                String prodi = resultSet.getString("Prodi"); // Ambil program studi mahasiswa

                System.out.println(id + "|" + name + "|" + year + "|" + prodi); // Tampilkan data mahasiswa
            }
        } catch (SQLException e) { // Tangani kesalahan yang terjadi saat mengakses database
            System.out.println("Error: " + e.getMessage()); // Tampilkan pesan kesalahan
        }
    }
    
    @Override
    public String toString(){
        printLecture();
        printCourse();
        printStudent();
        printEnrollment();
        
        return "";
    }
}


