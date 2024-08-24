package it.polito.tvseriesdb;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class TVSeriesDB {

	LinkedList<String> trasmissionServices = new LinkedList<>();
	TreeMap<String, TVSeries> tvSeries = new TreeMap<>();
	TreeMap<String, Actor> actors = new TreeMap<>();
	TreeMap<String, User> users = new TreeMap<>();


	// R1
	
	/**
	 * adds a list of transmission services.
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param tServices the transmission services
	 * @return number of transmission service inserted so far
	 */
	public int addTransmissionService(String...tServices) {
		for(String s: tServices) if(!trasmissionServices.contains(s)) trasmissionServices.add(s);
		return trasmissionServices.size();
	}
	
	/**
	 * adds a TV series whose name is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param title the title of the TV Series
	 * @param tService the name of the transmission service 
	 * broadcasting the TV series.
	 * @param genre the genre of the TV Series
	 * @return number of TV Series inserted so far
	 * @throws TSException if TV Series is already inserted or transmission service does not exist.
	 */
	public int addTVSeries(String title, String tService, String genre) throws TSException {
		if(!trasmissionServices.contains(tService)) throw new TSException();
		if(tvSeries.containsKey(title)) throw new TSException();

		tvSeries.put(title, new TVSeries(title, tService, genre));

		return tvSeries.size();
	}
	
	/**
	 * adds an actor whose name and surname is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param name the name of the actor
	 * @param surname the surname of the actor
	 * @param nationality the nationality of the actor
	 * @return number of actors inserted so far
	 * @throws TSException if actor has already been inserted.
	 */
	public int addActor(String name, String surname, String nationality) throws TSException {
		if(actors.containsKey(name+" "+surname)) throw new TSException();

		actors.put(name+" "+surname, new Actor(name+" "+surname, name, surname, nationality));
		return actors.size();
	}
	
	/**
	 * adds a cast of actors to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the cast is inserted
	 * @param actors	list of actors to add to a TV series, format of 
	 * 					each actor identity is "name surname"
	 * @return number of actors in the cast
	 * @throws TSException in case of non-existing actor or TV Series does not exist
	 */
	public int addCast(String tvSeriesTitle, String...actors) throws TSException {
		if(!tvSeries.containsKey(tvSeriesTitle)) throw new TSException();
		for(String s : actors) if(!this.actors.containsKey(s)) throw new TSException();

		for(String s : actors) tvSeries.get(tvSeriesTitle).addActorToCast(this.actors.get(s));

		return tvSeries.get(tvSeriesTitle).getCast().size();
	}
      
	// R2
	
	/**
	 * adds a season to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numEpisodes	number of episodes of the season
	 * @param releaseDate	release date for the season (format "gg:mm:yyyy")
	 * @return number of seasons inserted so far for the TV series
	 * @throws TSException in case of non-existing TV Series or wrong releaseDate
	 */
	public int addSeason(String tvSeriesTitle, int numEpisodes, String releaseDate) throws TSException {
		if(!tvSeries.containsKey(tvSeriesTitle)) throw new TSException();
		String[] date = releaseDate.split(":");
		String dateCompl = date[2]+date[1]+date[0]; 
		Integer intDate = Integer.parseInt(dateCompl);
		if (tvSeries.get(tvSeriesTitle).getLatestExitDate()>intDate) throw new TSException();
		
		tvSeries.get(tvSeriesTitle).addSeason(new Season(tvSeries.get(tvSeriesTitle).getSeasons().size()+1,tvSeriesTitle, numEpisodes, intDate));
		
		return tvSeries.get(tvSeriesTitle).getSeasons().size();
	}
	

	/**
	 * adds an episode to a season of a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numSeason	number of season to which add an episode
	 * @param episodeTitle	title of the episode (unique)
	 * @return number of episodes inserted so far for that season of the TV series
	 * @throws TSException in case of non-existing TV Series, season, repeated title 
	 * 			of the episode or exceeding number of episodes inserted
	 */
	public int addEpisode(String tvSeriesTitle, int numSeason, String episodeTitle) throws TSException {
		if(!tvSeries.containsKey(tvSeriesTitle)) throw new TSException();
		if(!tvSeries.get(tvSeriesTitle).getSeasons().containsKey(numSeason)) throw new TSException();
		if(tvSeries.get(tvSeriesTitle).getSeasons().get(numSeason).getNumEpisode()==tvSeries.get(tvSeriesTitle).getSeasons().get(numSeason).getEpisodes().size()) throw new TSException();
		if(tvSeries.get(tvSeriesTitle).getSeasons().get(numSeason).getEpisodes().contains(episodeTitle)) throw new TSException();
		
		tvSeries.get(tvSeriesTitle).getSeasons().get(numSeason).addEpisode(episodeTitle);


		return tvSeries.get(tvSeriesTitle).getSeasons().get(numSeason).getEpisodes().size();
	}

	/**
	 * check which series and which seasons are still lacking
	 * episodes information
	 * 
	 * @return map with TV series and a list of seasons missing episodes information
	 * 
	 */
	public Map<String, List<Integer>> checkMissingEpisodes() {
		return tvSeries.values().stream().flatMap(s->s.getSeasons().values().stream())
		.filter(s->s.getEpisodes().size()<s.getNumEpisode()).collect(Collectors.groupingBy(Season::getNameSerie, TreeMap::new, Collectors.mapping(Season::getNumber, Collectors.toList())));
	}

	// R3
	
	/**
	 * Add a new user to the database, with a unique username.
	 * 
	 * @param username	username of the user
	 * @param genre		user favourite genre
	 * @return number of registered users
	 * @throws TSException in case username is already registered
	 */
	public int addUser(String username, String genre) throws TSException {
		if(users.containsKey(username)) throw new TSException();
		users.put(username, new User(username, genre));
		return users.size();
	}

	/**
	 * Adds a series to the list of favourite
	 * series of a user.
	 * 
	 * @param username	username of the user
	 * @param tvSeriesTitle	 title of TV series to add to the list of favourites
	 * @return number of favourites TV series of the users so far
	 * @throws TSException in case user is not registered or TV series does not exist
	 */
	public int likeTVSeries(String username, String tvSeriesTitle) throws TSException {
		if(!users.containsKey(username)) throw new TSException();
		if(!tvSeries.containsKey(tvSeriesTitle)) throw new TSException();
		if(users.get(username).getFavSeries().containsKey(tvSeriesTitle))  throw new TSException();

		users.get(username).addFavSeries(tvSeries.get(tvSeriesTitle));
		return users.get(username).getFavSeries().size();
	}
	
	/**
	 * Returns a list of suggested TV series. 
	 * A series is suggested if it is not already in the user list and if it is of the user's favourite genre 
	 * 
	 * @param username name of the user
	 * @throws TSException if user does not exist
	 */
	public List<String> suggestTVSeries(String username) throws TSException {
		if(!users.containsKey(username)) throw new TSException();

		List<String> list = tvSeries.values().stream().filter(s->users.get(username).getFavSeries().containsKey(s.getName())==false)
		.filter(s->users.get(username).getFavGenre().equals(s.getGenre())).map(TVSeries::getName)
		.collect(Collectors.toList());

		if(list.size()==0) list.add("");
		return list;
	}
	
	//R4 

	/**
	 * Add reviews from a user to a tvSeries
	 * 
	 * @param username	username of the user
	 * @param tvSeries		name of the participant
	 * @param score		review score assigned
	 * @return the average score of the series so far from 0 to 10, extremes included
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double addReview(String username, String tvSeries, int score) throws TSException {
		return -1.0;
	}

	/**
	 * Average rating of tv series in the favourite list of a user
	 * 
	 * @param username	username of the user
	 * @return the average score of the series in the list of favourites of the user
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double averageRating(String username) throws TSException {
		return -1.0;
	}
	
	// R5

	/**
	 * Returns most awaited season of a tv series using format "TVSeriesName seasonNumber", the last season of the best-reviewed TV series who has not come out yet with
	 * respect to the current date passed in input. In case of tie, select using alphabetical order. Date format: dd::mm::yyyy
	 * 
	 * @param currDate	currentDate
	 * @return the most awaited season of TV series who still has to come out
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public String mostAwaitedSeason(String currDate) throws TSException {
		return null;
	}
	
	/**
	 * Computes the best actors working in tv series of a transmission service passed
	 * in input. The best actors have worked only in tv series of that transmission service
	 * with average rating higher than 8.
	 * @param transmissionService	transmission service to consider
	 * @return the best actors' names as "name surname" list
	 * @throws TSException	in case of transmission service not in the DB
	 */
	public List<String> bestActors(String transmissionService) throws TSException {
		return null;
	}

	
}
