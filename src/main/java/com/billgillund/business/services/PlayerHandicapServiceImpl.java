package com.billgillund.business.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billgillund.entity.Courses;
import com.billgillund.entity.Players;
import com.billgillund.entity.Round;
import com.billgillund.repository.RoundRepository;

@Service
public class PlayerHandicapServiceImpl implements PlayerHandicapService {
	public static int MAX_ROUNDS = 20;      /* Maximum # of rounds to be used */
    public static int MIN_ROUNDS = 0;      /* Minimum # of rounds to be used */
    public static int AVG_SLOPE = 113; // Average slope of a golf course
    private String filename;
    private Map <String, List> playerRounds  = new Hashtable<String,List>();


	CourseService courseService;
	PlayerService playerService;
	RoundRepository roundRepository;
	 @Autowired
	 public PlayerHandicapServiceImpl(CourseService aCourseService) {
		  courseService=aCourseService;
	   }
	 @Autowired
	   public void setPlayerService(PlayerService aplayerService) {
		 	playerService = aplayerService;
	    }
	 @Autowired
	   public void setRoundRepository(RoundRepository aroundRepository) {
	            this.roundRepository = aroundRepository;
	    }
	 
	 public List addScore(String aDate, String aPlayer, String aCourseName, int aScore, int courseId,long playerId) throws Exception {
	        
	  	    Players player = playerService.getPlayer(aPlayer);
	    	if (player == null)
	    		throw new Exception("Player not found : " + aPlayer);
	  	
	    	Courses aCourse = getCourseService().getCourse(courseId);
	        if ( aCourse == null ) {
	            throw new Exception("Course Not Found: " + aCourseName);
	        }
	        
	        Round newRound = new Round();
	        newRound.setCourse(aCourseName);
	        newRound.setRoundDate(aDate);
	        newRound.setPlayer(aPlayer);
	        newRound.setPlayerId(playerId);
	        newRound.setCourse(aCourseName);
	        newRound.setScore(aScore);
	        newRound.setSlope(aCourse.getSlope());
	        newRound.setRating(aCourse.getRating());
	        newRound.setDelta(calculateDelta(newRound.getScore(),newRound.getSlope(),newRound.getRating()));
	        // get all rounds 
	        Round mySavedRound = roundRepository.save(newRound);
	        List<Round> rounds = readPlayerScores(player.getPlayerId());
	        // add new round
	        rounds.add(0,newRound);
	        
	        playerRounds.put(mySavedRound.getPlayer(), rounds);
	        
	    
	        
	    	return rounds;
	    
		}
	    
	    protected static float calculateDelta(int aScore, int aSlope, float aRating) {
	        return (aScore - aRating) * ((float) AVG_SLOPE / (float) aSlope);
	    }
	    
	    
	    public void deleteScore(Players aPlayer, Round aRound) throws Exception {
			if ((aPlayer == null) || aRound ==null){
	    		throw new Exception("Player not found : " + aPlayer);
	    	}
		   	Players player = playerService.getPlayer(aPlayer.getName());
	    	if (player == null)
	    		throw new Exception("Player not found : " + aPlayer);
	    	
	    	List oldRounds = readPlayerScores(aPlayer.getPlayerId());
	    //    Vector newRounds = removeScore(oldRounds,aRound); 
	    //	writePlayerScores(aPlayer , newRounds,aRound.getRoundId());
		}
	    
	    public Vector removeScore(Vector oldRounds, Round roundToDelete) {
	    	
	    	Vector newRounds = new Vector();
	    	 Enumeration enumeration = oldRounds.elements();
	         while ( enumeration.hasMoreElements() ) {
	             Round round = (Round) enumeration.nextElement();
	             if (!round.equals(roundToDelete)){
	            	 newRounds.add(round);
	             }
	             
	         }
	    	
	    	return newRounds;
	    
	    }
	    
	    public List<Round> getScoresForPlayer(Players aPlayer) {
	    	
	    	
	        List vPlayerScores = readPlayerScores(aPlayer.getPlayerId());   
	
	        return vPlayerScores;
	 }


	    protected String getFilename() {
	         return filename;
	    }
	    
	    protected CourseService getCourseService() {
	         return courseService;
	    }
	    
	    public float getHandicap(Players aPlayer) {
	        return calculateHandicap(getScoresForPlayer(aPlayer));
	   }

	   public float getAverage(Players aPlayer) {
	        return calculateAverage(getScoresForPlayer(aPlayer));
	   }

	   public float getAverageDelta(Players aPlayer) {
	        return calculateAverageDelta(getScoresForPlayer(aPlayer));
	   }
	    public float calculateHandicap(List rounds) {

	        int numberOfRounds = rounds.size();
	        if ( numberOfRounds < MIN_ROUNDS ) {
	            System.out.println("Requires " + MIN_ROUNDS + " rounds to calculate handicap");
	            return -999.0f;
	        }

	        if ( numberOfRounds >= MAX_ROUNDS ) {
	            numberOfRounds = MAX_ROUNDS;
	            System.out.println("Using last " + numberOfRounds + " of " + rounds.size() + " rounds");
	        }

	        float tempDelta = 0.0f;
	        int index = 0;
	        float totalDelta = 0.0f;

	        resetRounds(rounds);

	        // find 10 best deltas

	        for ( int i = 0 ; i < (numberOfRounds/2) ; i++ ) {
	            tempDelta = 999.0f;
	            for ( int j = 0 ; j < numberOfRounds ; j++ ) {
	                Round round = (Round) rounds.get(j);
	                if( (round.getDelta() < tempDelta) && !round.isInUse() ) {
	                    tempDelta = round.getDelta();
	                    index = j;
	                }
	            }

	            // This score was one of the 10 lowest so use it in the average
	            ((Round) rounds.get(index)).setInUse(true); 
	            totalDelta += tempDelta;
	        }
	/*
	 *  If you have one score your delta w 
	 * 
	 * */
	 
	       
	        float handicap =0;
	        if (numberOfRounds == 1){
	        	Round r = (Round)rounds.get(0);
	        	totalDelta = r.getDelta();
	        	handicap = (float)(totalDelta / (numberOfRounds/1)) * 0.96f;
	        }
	        else
	        {
	        	handicap = (float)(totalDelta / (numberOfRounds/2)) * 0.96f;
	        }
	        System.out.println("Handicap for last " + numberOfRounds + " rounds = " + handicap);
	        return handicap;
	    }
	    
	    public float calculateAverage(List rounds) {
	    	
	        if ( rounds.isEmpty() ) {
	        	return 0.0f;
	        }
	        else if ( rounds.size() == 1 ) {
	        	Round aRound = (Round) rounds.get(0);
	        	return aRound.getScore();
	        }

	        float sum = 0.0f;
	        int i = 0;
	        Iterator enumeration = rounds.iterator();
	        while ( enumeration.hasNext() && i < MAX_ROUNDS ) {
	            Round round = (Round) enumeration.next();
	            sum += round.getScore();
	            i++;
	        }

	        System.out.println("Average Score for " + i + " rounds: " + sum/i);
	        return sum / i;
	    }

	    public float calculateAverageDelta(List rounds) {
	    	
	        if ( rounds.isEmpty() ) {
	        	return 0.0f;
	        }
	        else if ( rounds.size() == 1 ) {
	        	Round aRound = (Round) rounds.get(0);
	        	return aRound.getDelta();
	        }
	        
	        float sum = 0.0f;
	        int i = 0;
	        Iterator enumeration = rounds.iterator();
	        while ( enumeration.hasNext() && i < MAX_ROUNDS ) {
	            Round round = (Round) enumeration.next();
	            sum += round.getDelta();
	            i++;
	        }

	        System.out.println("Average Delta for " + i + " rounds: " + sum/i);
	        return sum / i;
	    }
	    
	    protected List getPlayerRoundss(String aPlayer){
	        
	    	if (playerRounds == null)
	    	{
	    		playerRounds = new Hashtable();
	    	}
	    	
	    	List rounds = playerRounds.get(aPlayer);
	        
	        if (rounds == null)
	        {
	        	rounds = new ArrayList();
	        }
	        
	        return rounds;
	    	
	    }
	    
	    protected List readPlayerScores(long playerId) {
	    	
	    	List scores = this.getScoresForPlayer(playerId);
	    	
	        return scores;
	    }
	    @Override
		public List<Round> getScoresForPlayer(long playerId) {
	    	
	    	Iterable <Round> it = roundRepository.findByPlayerId(playerId);
	    	List al = new ArrayList();
	    	it.forEach(round ->{
	    		Round r = new Round();
	    		r.setId(round.getId());
	    		r.setCourse(round.getCourse());
	    		r.setDelta(round.getDelta());
	    		r.setPlayer(round.getPlayer());
	    		r.setPlayerId(round.getPlayerId());
	    		r.setRating(round.getRating());
	    		r.setRoundDate(round.getRoundDate());
	    		r.setScore(round.getScore());
	    		r.setSlope(round.getSlope());
	    		al.add(r);
	    		
	    	});
	    	
			return al;
		}
	    
	    
	    
	    
	    
	    protected BufferedReader getInputStream(String aFilename) throws IOException {
	        return new BufferedReader(new FileReader(aFilename));
	    }

	    protected Round createRound(String aPlayerName,String line) throws NumberFormatException, Exception {
	        StringTokenizer st = new StringTokenizer(line, ",");
	        String date = st.nextToken();
	        String courseName = st.nextToken();
	        String score  = st.nextToken();
	        String courseId = st.nextToken();
	        
	        int intCourseId = Integer.parseInt(courseId);
	        
	        Courses aCourse = getCourseService().getCourse(intCourseId);
	        if ( aCourse == null ) {
	            throw new Exception("Course Not Found: " + courseName);
	        }

	        Round r = new Round();
	        r.setRoundDate(date);
	        r.setPlayer(aPlayerName);
	        r.setCourse(aCourse.getCourseName());
	        r.setScore(Integer.parseInt(score));
	        r.setSlope(aCourse.getSlope());
	        r.setRating(aCourse.getRating());
	        return r;
	    }

	        
	    protected void writePlayerScores(String aPlayer , Vector rounds, int remove) throws Exception{}
	    protected String getPlayerFileName(String aPlayer){
	    	return  aPlayer.replace(' ', '$')+ ".dat";
	    }
	    
	    protected BufferedWriter getOutputStream(String aFilename) throws IOException {
	        return new BufferedWriter(new FileWriter(aFilename));
	    }
	    
	   

	    public void printBestDeltas(Vector rounds) {
	        System.out.println("Best Round : ");
	        Enumeration enumeration = rounds.elements();
	        while ( enumeration.hasMoreElements() ) {
	            Round round = (Round) enumeration.nextElement();
	           
	        }
	      
	    }

	    protected void resetRounds(List rounds) {
	        Iterator enumeration = rounds.iterator();
	        while ( enumeration.hasNext() ) {
	            Round round = (Round) enumeration.next();
	            round.setInUse(false);
	        }
	    }

		
		


}
