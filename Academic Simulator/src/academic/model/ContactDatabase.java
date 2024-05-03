package academic.model;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class ContactDatabase extends AbstractDb{
    public ContactDatabase(String url, String username, String password) throws SQLException{
        super(url, username, password);
    }

    protected void createTables() throws SQLException{
        String contactsDDL = "CREATE TABLE IF NOT EXISTS contacts ("
        + "email VARCHAR(100) NOT NULL,"
        + "name VARCHAR(100) NOT NULL,"
        + "phone VARCHAR(100) NOT NULL,"
        + "PRIMARY KEY(email),"
        + "UNIQUE(phone)"
        + ");";

        String groupDDL = "CREATE TABLE IF NOT EXISTS student_groups ("
        + "id INTEGER PRIMARY KEY,"
        + "name VARCHAR(100) NOT NULL"
        + ");";

        String contactGroupDDL = "CREATE TABLE IF NOT EXISTS contact_group (" 
        + "contact_email VARCHAR(100),"
        + "group_id INTEGER,"
        + "PRIMARY KEY(contact_email, group_id),"
        + "FOREIGN KEY(contact_email) REFERENCES contacts(email) ON DELETE CASCADE ON UPDATE NO ACTION,"
        + "FOREIGN KEY(group_id) REFERENCES student_groups(id) ON DELETE CASCADE ON UPDATE NO ACTION"
        + ");";
    
            // step 3
            Statement statement = this.getConection().createStatement();

            statement.execute(contactsDDL);
            statement.execute(groupDDL);
            statement.execute(contactGroupDDL);

            // step 5
            statement.close();
    }

    protected void seedTables() throws SQLException{
        String cleanUpSQL[] = {
            "DELETE FROM contact_group",
            "DELETE FROM contacts",
            "DELETE FROM student_groups"
        };
        

        String groupInsertSQL[] = {
            "INSERT INTO student_groups (id, name) VALUES (1, 'Family')",
            "INSERT INTO student_groups (id, name) VALUES (2, 'Friend')",
            "INSERT INTO student_groups (id, name) VALUES (3, 'Work')"
        };

        String contactInsertSQL[] = {
            "INSERT INTO contacts (email, name, phone) VALUES ('jaka@sembung.com', 'Jaka Sembung', '081234567211')",
            "INSERT INTO contacts (email, name, phone) VALUES ('wiro@sableng.com', 'Wiro Sableng', '081254567890')",
            "INSERT INTO contacts (email, name, phone) VALUES ('milkyman@heroes.com', 'Milkyman', '085354356765')",
            "INSERT INTO contacts (email, name, phone) VALUES ('supraman@flying-heroes.com', 'Supraman', '084254356769')"
        };

        Map<String, Integer[]> contactstudent_Groups = new HashMap<String, Integer[]>();
        contactstudent_Groups.put("wiro@sableng.com", new Integer[]{ 2 });
        contactstudent_Groups.put("jaka@sembung.com", new Integer[]{ 2, 3 });
        contactstudent_Groups.put("milkyman@heroes.com", new Integer[]{ 1, 3 });
        contactstudent_Groups.put("supraman@flying-heroes.com", new Integer[]{ 2 });

        // step 3
        Statement statement = this.getConection().createStatement();

        for(String sql : cleanUpSQL){
            int affected = statement.executeUpdate(sql);
            System.out.println(sql + ": "+ affected);
            statement.execute(sql);
        }


        

        for(String sql : contactInsertSQL){
            try {
                int affected = statement.executeUpdate(sql);
                System.out.println(sql + ": "+ affected);
            } catch (SQLException e) {
                System.out.println("Error inserting contact: " + e.getMessage());
            }
        }

        for(String sql : groupInsertSQL){
            try{
                int affected = statement.executeUpdate(sql);
                System.out.println(sql + ": "+ affected);
                statement.execute(sql);
            } catch (SQLException e) {
                System.out.println("Error inserting group: " + e.getMessage());
            }
        }

        // for(String sql : contactInsertSQL){
        //     int affected = statement.executeUpdate(sql);
        //     System.out.println(sql + ": "+ affected);
        //     statement.execute(sql);
        // }

        // step 5
        statement.close();

        String sql = "INSERT INTO contact_group (contact_email, group_id) VALUES (?, ?)";

        // step 3
        PreparedStatement pStatement = this.getConection().prepareStatement(sql);

        contactstudent_Groups.entrySet().forEach(entry -> {
            String contactEmail = entry.getKey();

            for(Integer groupId : entry.getValue()){
                try{
                    pStatement.setString(1, contactEmail);
                    pStatement.setInt(2, groupId);

                    int affected = pStatement.executeUpdate();
                    System.out.println(sql + ": "+ affected);
                }catch(SQLException sqle){
                    System.out.println(sqle.getMessage());
                }
                
            }
        });

        pStatement.close();
    }

    public void printAllContacts() throws SQLException{
        Statement statement = this.getConection().createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts ORDER BY name");

        while(resultSet.next()){
            System.out.println(
                resultSet.getString("email") + "" +
                resultSet.getString("name") + "" +
                resultSet.getString("phone")
            );
        }

        resultSet.close();
        statement.close();
    }

    public void printAllContactsByGroup() throws SQLException{
        Statement statement = this.getConection().createStatement();

        String sql = "SELECT g.name AS 'group', c.name, c.email, c.phone FROM " + "student_groups g JOIN contact_group cg ON g.id "+ "JOIN contacts c ON c.email = cg.contact_email " + 
        "ORDER BY g.name, c.name";

        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            System.out.println(
                resultSet.getString("group") + "|" +
                resultSet.getString("name") + "|" +
                resultSet.getString("email") + "|" +
                resultSet.getString("phone")
            );
        }
        resultSet.close();
        statement.close();
    }

    public void printAllContactsWithEmailContains(String email) throws SQLException{
        String sql = "SELECT * FROM contacts WHERE email LIKE ?";

        PreparedStatement pStatement = this.getConection().prepareStatement(sql);
        pStatement.setString(1, "%" + email + "%");

        ResultSet resultSet = pStatement.executeQuery();

        while(resultSet.next()){
            System.out.println(
                resultSet.getString("email") + "|" +
                resultSet.getString("name") + "|" +
                resultSet.getString("phone")
            );
        }

        resultSet.close();
        pStatement.close();
    }

}