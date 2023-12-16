package fi.haagahelia.course.domain;

import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Motivo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)	
	private long motivoid;
	
    @Column(name="motivoname")
	private String name; 
     
    @ManyToMany(mappedBy = "motivo", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Citas> students;  

    public Motivo() {
	}

	public Motivo(String name) {
		this.name = name;
	}     

    public long getMotivoid() {
		return motivoid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
    
    
    public Set<Citas> getStudents() {
        return students;
    }

    public void setStudents(Set<Citas> students) {
        this.students = students;
    }	
}
