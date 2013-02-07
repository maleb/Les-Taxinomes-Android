package org.lestaxinomes.les_taxinomes_android.entities;

import java.util.Date;
import java.util.List;

public class Media {

	private Integer id;
	private String titre;
	private String description;
	private String imageUrl;
	private String vignetteUrl;
	private List<Author> authors;
	private Date modificationDate;
	private Integer visites;
	private GIS gis;
	private Integer licenceId;
	

	public Integer getLicenceId() {
		return licenceId;
	}

	public void setLicenceId(Integer licenceId) {
		this.licenceId = licenceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Integer getVisites() {
		return visites;
	}

	public void setVisites(Integer visites) {
		this.visites = visites;
	}

	public GIS getGis() {
		return gis;
	}

	public void setGis(GIS gis) {
		this.gis = gis;
	}

	public String getVignetteUrl() {
		return vignetteUrl;
	}

	public void setVignetteUrl(String vignetteUrl) {
		this.vignetteUrl = vignetteUrl;
	}

}
