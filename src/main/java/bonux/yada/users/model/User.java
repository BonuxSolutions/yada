package bonux.yada.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

@Entity
public final class User extends ResourceSupport {

	@Id
	@Column
	public String username;
	
	@Column
	private String password;
	
	@Column
	public Boolean enabled;
	
	
	
	
}
