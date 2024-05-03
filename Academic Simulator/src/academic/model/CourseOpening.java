package academic.model;

/**
 * @author 12S22011 Wilson Eksaudi Sihombing
 * @author NIM Nama
 */
public class CourseOpening {
    private String course_id;
    private String year;
    private String semester;
    private String guardian;

    public CourseOpening(String course_id, String year, String semester, String guardian) {
        this.course_id = course_id;
        this.year = year;
        this.semester = semester;
        this.guardian = guardian;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getYear() {
        return this.year;
    }

    public String getSemester() {
        return this.semester;
    }

    public String getGuardian() {
        return this.guardian;
    }
    
}