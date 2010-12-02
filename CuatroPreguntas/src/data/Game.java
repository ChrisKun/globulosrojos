package data;

import java.util.ArrayList;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class Game implements CaseComponent{

	Integer gameId;
	String url;
	String name;
	String codeName;
	String image;
	ArrayList<String> artists;
	ArrayList<String> designers;
	ArrayList<String> publishers;
	Integer yearPublished;
	Integer minNumPlayers;
	Integer maxNumPlayers;
	Integer minBestNumPlayers;
	Integer maxBestNumPlayers;
	Integer minRecNumPlayers;
	Integer maxRecNumPlayers;
	Integer playingTime;
	Integer age;
	ArrayList<String> subdomains;
	ArrayList<String> categories;
	ArrayList<String> mechanics;
	
	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getYearPublished() {
		return yearPublished;
	}

	public void setYearPublished(Integer yearPublished) {
		this.yearPublished = yearPublished;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public ArrayList<String> getArtists() {
		return artists;
	}

	public void setArtists(ArrayList<String> artists) {
		this.artists = artists;
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
	
	public void setNumPlayers(ArrayList<Integer> numList)
	{
		this.minNumPlayers = numList.get(0);
		
		if(numList.size() == 1)
		{
			this.maxNumPlayers = numList.get(0);
		}
		else
		{
			this.maxNumPlayers = numList.get(1);
		}
	}

	public Integer getMinNumPlayers() {
		return minNumPlayers;
	}

	public void setMinNumPlayers(Integer minNumPlayers) {
		this.minNumPlayers = minNumPlayers;
	}

	public Integer getMaxNumPlayers() {
		return maxNumPlayers;
	}

	public void setMaxNumPlayers(Integer maxNumPlayers) {
		this.maxNumPlayers = maxNumPlayers;
	}
	
	public void setBestNumPlayers(ArrayList<Integer> numList)
	{
		this.minBestNumPlayers = numList.get(0);
		this.maxBestNumPlayers = numList.get(numList.size()-1);
	}

	public Integer getMinBestNumPlayers() {
		return minBestNumPlayers;
	}

	public void setMinBestNumPlayers(Integer minBestNumPlayers) {
		this.minBestNumPlayers = minBestNumPlayers;
	}

	public Integer getMaxBestNumPlayers() {
		return maxBestNumPlayers;
	}

	public void setMaxBestNumPlayers(Integer maxBestNumPlayers) {
		this.maxBestNumPlayers = maxBestNumPlayers;
	}
	
	public void setRecNumPlayers(ArrayList<Integer> numList)
	{
		this.minRecNumPlayers = numList.get(0);
		this.maxRecNumPlayers = numList.get(numList.size()-1);
	}

	public Integer getMinRecNumPlayers() {
		return minRecNumPlayers;
	}

	public void setMinRecNumPlayers(Integer minRecNumPlayers) {
		this.minRecNumPlayers = minRecNumPlayers;
	}

	public Integer getMaxRecNumPlayers() {
		return maxRecNumPlayers;
	}

	public void setMaxRecNumPlayers(Integer maxRecNumPlayers) {
		this.maxRecNumPlayers = maxRecNumPlayers;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public Integer getgameId() {
		return gameId;
	}

	public void setgameId(Integer id) {
		this.gameId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Integer playingTime) {
		this.playingTime = playingTime;
	}
	
	public String toString()
	{
		return gameId + ", " + name + ", " + playingTime + ", " +
				url + ", " + codeName + ", " + image + ", " +
				yearPublished + ", " + 
				playingTime + ", " + age + ", " + artists + ", " + 
				designers + ", " + publishers + minNumPlayers + ", "+ maxNumPlayers + ", "+
				minBestNumPlayers + ", " + maxBestNumPlayers + ", " + minRecNumPlayers + ", " + 
				maxRecNumPlayers + ", " + subdomains + ", " + categories + ", " + mechanics;
	}

	@Override
	public Attribute getIdAttribute() {
		return new Attribute("id", this.getClass());
	}

}
