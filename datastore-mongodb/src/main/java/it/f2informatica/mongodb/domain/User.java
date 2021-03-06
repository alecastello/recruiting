package it.f2informatica.mongodb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document
public class User extends Identifiable<String> {

	@Indexed(unique = true)
	private String username;

	private String password;

	private boolean removable = true;

	@DBRef
	private Role role;

}
