package bonux.yada.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.ResourceSupport;

@Entity
@Table(name = "users")
public final class User extends ResourceSupport {

	@Id
	@Column
	public String username;

	@Column
	private String password;

	@Column
	public Boolean enabled;

}
