package academic.model;

/**
 * @author 12S22011 Wilson Eksaudi Sihombing
 * @author 
 */

public class Lecture extends Person{
    private String initial;
    private String email;

 
    public Lecture(String id, String name, String initial, String email, String study_program){
        super(id, name, study_program);
        this.initial = initial;
        this.email = email; 
    } 

    @Override
    public String getType() {
        return "Lecture";
    }   

    public String getInitial() {
        return this.initial;
    }

    public String getEmail() {
        return this.email;
    }

}


