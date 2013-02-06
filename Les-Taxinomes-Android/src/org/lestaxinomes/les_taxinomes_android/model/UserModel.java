package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;

public class UserModel extends Model {
	
	private Author author;
	private String login;
	private String password;

	@Override
	public void updateView(UpdatableView v) {
		v.update();
		
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public void authenticate(){
		getConnexionManager().authenticate(this);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
