package RecomendadorJuegos;

import java.util.ArrayList;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class Game implements CaseComponent{
	
	String url;
	String code;
	String codeName;
	String title;
	String image;
	ArrayList<String> artists;
	ArrayList<String> designers;
	ArrayList<String> publishers;
	String yearPublished;
	String numPlayers;
	String bestNumPlayers;
	String recNumPlayers;
	String playingTime;
	String age;
	ArrayList<String> subdomains;
	ArrayList<String> categories;
	ArrayList<String> mechanics;
	
	public String export()
	{
		return url+"\n"+
		code+"\n"+
		codeName+"\n"+
		title+"\n"+
		image+"\n"+
		artists+"\n"+
		designers+"\n"+
		publishers+"\n"+
		yearPublished+"\n"+
		numPlayers+"\n"+
		bestNumPlayers+"\n"+
		recNumPlayers+"\n"+
		playingTime+"\n"+
		age+"\n"+
		subdomains+"\n"+
		categories+"\n"+
		mechanics;
	}
	
	
	@Override
	public String toString() {
		return "Game [age=" + age + ", artists=" + artists
				+ ", bestNumPlayers=" + bestNumPlayers + ", categories="
				+ categories + ", code=" + code + ", codeName=" + codeName
				+ ", designers=" + designers + ", image=" + image
				+ ", mechanics=" + mechanics + ", numPlayers=" + numPlayers
				+ ", playingTime=" + playingTime + ", publishers=" + publishers
				+ ", recNumPlayers=" + recNumPlayers + ", subdomains="
				+ subdomains + ", title=" + title + ", url=" + url
				+ ", yearPublished=" + yearPublished + "]";
	}
	
	public ArrayList<String> getArtists() {
		return artists;
	}
	public void setArtists(ArrayList<String> artists) {
		this.artists = artists;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getDesigners() {
		return designers;
	}
	public void setDesigners(ArrayList<String> designers) {
		this.designers = designers;
	}
	public ArrayList<String> getPublishers() {
		return publishers;
	}
	public void setPublishers(ArrayList<String> publishers) {
		this.publishers = publishers;
	}
	public String getYearPublished() {
		return yearPublished;
	}
	public void setYearPublished(String yearPublished) {
		this.yearPublished = yearPublished;
	}
	public String getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(String numPlayers) {
		this.numPlayers = numPlayers;
	}
	public String getBestNumPlayers() {
		return bestNumPlayers;
	}
	public void setBestNumPlayers(String bestNumPlayers) {
		this.bestNumPlayers = bestNumPlayers;
	}
	public String getRecNumPlayers() {
		return recNumPlayers;
	}
	public void setRecNumPlayers(String recNumPlayers) {
		this.recNumPlayers = recNumPlayers;
	}
	public String getPlayingTime() {
		return playingTime;
	}
	public void setPlayingTime(String playingTime) {
		this.playingTime = playingTime;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public ArrayList<String> getSubdomains() {
		return subdomains;
	}
	public void setSubdomains(ArrayList<String> subdomains) {
		this.subdomains = subdomains;
	}
	public ArrayList<String> getCategories() {
		return categories;
	}
	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}
	public ArrayList<String> getMechanics() {
		return mechanics;
	}
	public void setMechanics(ArrayList<String> mechanics) {
		this.mechanics = mechanics;
	}
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}


	@Override
	public Attribute getIdAttribute() {
		return new Attribute("code", this.getClass());
	}

	
	
	
}
