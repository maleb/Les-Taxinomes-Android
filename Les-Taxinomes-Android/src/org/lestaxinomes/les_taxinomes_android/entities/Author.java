package org.lestaxinomes.les_taxinomes_android.entities;
/**
 * the entity Author
 * @author Marie
 */
public class Author {

	private Integer id;
	private String name;
	private String avatarUrl;
	private String login;
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String url) {
		this.avatarUrl = url;
	}

}
