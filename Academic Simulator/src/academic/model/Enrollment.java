package academic.model;

/**
 * @author 12S22011 Wilson Sihombing
 * @author 
 */
public class Enrollment {
    private String id_student;
    private String id_course;
    private String year;
    private String semester;
    private String status = "None"; 
    private String grade_before = ""; 
    private String status_remendial = "";

    public Enrollment(String id_student, String id_course, String year, String semester) {
        this.id_student = id_student;
        this.id_course = id_course;
        this.year = year;
        this.semester = semester;
    }

    public String getId_student() {
        return this.id_student;
    }
 
    public String getId_course() {
        return this.id_course;
    }

    public String getYear() {
        return this.year;
    }

    public String getSemester() {
        return this.semester;
    }

    public String getStatus() {
        return this.status;
    }
 
    public void setStatus (String stat){
        this.status = stat;
    }

    public void setGradeBefore(String grade){
        this.grade_before = grade;
    }

    public String getGradeBefore(){
        return this.grade_before;
    }

    public void setStatusRemedial(String stat){
        this.status_remendial = stat;
    }

    public String getStatusRemedial(){
        return this.status_remendial;
    }

}