package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// findByName의 경우, 아래와 같은 JPQL이 실행됨.
	// JPQL select m from Member m where m.name = ?
	@Column(name = "name")
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
