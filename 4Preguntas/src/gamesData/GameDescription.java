/**
 * GameDescription.java
 * @author Markel Arizaga Begil
 * 26/11/2010
 */
package gamesData;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
//import jcolibri.test.recommenders.housesData.Area;
//import jcolibri.test.recommenders.housesData.Beds;
//import jcolibri.test.recommenders.housesData.Type;

/**
 * Implements the game description.
 * @author Markel Arizaga Begil
 * @version 0.1
 *
 */
public class GameDescription implements CaseComponent
{
//    public enum Beds  {StudioFlat,one,two,three,four,five,six,seven};
//    public enum Type  {Flat,House};
//    public enum Area  {Acton,Addlestone,Alperton,Balham,Barnes,Battersea,Bayswater,Belsize_Park,Bermondsey,Bloomsbury,Brentford,Brixton,Brondesbury,Byfleet,Camden,Canary_Wharf,Chelsea,Chiswick,Clapham,Clerkenwell,Cricklewood,Croyden,Earls_Court,Egham,Finchley,Finsbury,Fulham,Golders_Green,Greenwich,Hammersmith,Hampstead,Highgate,Holland_Park,Holloway,Hornsey,Hounslow,Hyde_Park,Islington,Kensington,Kilburn,Kings_Cross,Kingston,Knightsbridge,Lambeth,Little_Venice,Maida_Vale,Marylebone,Mayfair,Mitcham,Notting_Hill,Paddington,Parsons_Green,Pimlico,Primrose_Hill,Putney,Regents_Park,Richmond,Shepherds_Bush,Shoreditch,Soho,South_Kensington,Southwark,St_Johns_Wood,Stepney,Streatham,Swiss_Cottage,The_City,Tooting,Twickenham,Walton_on_Thames,Wandsworth,Wapping,West_Ham,West_Horsley,West_Kensington,Westminster,Weybridge,Whitechapel,Willesden,Wimbledon};
	
    Integer id;
    String name;
    Integer numPlayers;
    Integer bestNumPlayers;
    Integer playingTime;
    Integer age;
    
    /*<url>: Url of the game web page.
    <gameId>: bgg id of the game. Game web page can be found at http://www.boardgamegeek.com/boardgame/<gameId>
    <gameCodeName>: bgg code name of the game. Game web page is: http://www.boardgamegeek.com/boardgame/<gameId>/<gameCodeName>
    <gameName>: game name (includes whitespaces)
    <imageUrl>: game image url
    <artists>: list of artists
    <designers>: list of designers
    <publishers>: list of publishers
    <yearPublished>: publication year
    <numPlayers>: Official number of players
    <bestNumPlayers>: best number of players suggested by bgg users
    <recNumPlayers>: recommended number of players suggested by bgg users
    <playingTime>: playing time
    <age>: Recommended age
    <subdomains>: list of bgg subdomain classification. Complete list available in subdomains file
    <categories>: list of bgg categories. Complete list available in categories file
    <mechanics>: list of mechanics accordint to bgg. Complete list available in mechanics file
	*/
    
    
    
    
    
    
    public String toString() 
    {
		return "("+id+";"+name+";"+numPlayers+";"+bestNumPlayers+";"+playingTime+";"+age+";";
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(Integer numPlayers) {
		this.numPlayers = numPlayers;
	}

	public Integer getBestNumPlayers() {
		return bestNumPlayers;
	}

	public void setBestNumPlayers(Integer bestNumPlayers) {
		this.bestNumPlayers = bestNumPlayers;
	}

	public Integer getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Integer playingTime) {
		this.playingTime = playingTime;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	/* (non-Javadoc)
     * @see jcolibri.cbrcore.CaseComponent#getIdAttribute()
     */
    public Attribute getIdAttribute()
    {
	return new Attribute("id",this.getClass());
    }

}
