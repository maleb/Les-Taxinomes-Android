package org.lestaxinomes.les_taxinomes_android.model;

import org.lestaxinomes.les_taxinomes_android.entities.Author;
import org.lestaxinomes.les_taxinomes_android.views.UpdatableView;

public class AuthorModel extends Model {
	
	private Author author;

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
	
	public void loadAuthor(){
		getConnexionManager().loadAuthor(this);
	}

}
