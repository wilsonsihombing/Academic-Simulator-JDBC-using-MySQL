package academic.model;

/**
 * @author 12S22011 Wilson Eksaudi Sihombing
 * @author 
 */

public abstract class Person implements I_AcademicEntity{
    private String id;
    private String name;
    private String prodi;


    public Person(String id, String name, String prodi) {
        this.id = id;
        this.name = name;
        this.prodi = prodi;
    } 

    @Override
    public abstract String getType();

    @Override 
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProdi() {
        return this.prodi;
    }

}
