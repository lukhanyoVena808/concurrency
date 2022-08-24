package typingTutor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Updated the class by making all functions synchronized, including run method
 */

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static ArrayList<FallingWord> HungryWords = new ArrayList<>(); //hungry words
	private static int noWords; //how many
	private static Score score; //user score

	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}


	public static void setHungryWords( ArrayList<FallingWord> wordList) {
		HungryWords=wordList;	
	}
	
	public static synchronized void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static synchronized void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	@Override
	public void run() {
		int i=0;
		while (i<noWords) {		
			if (words[i].matchWord(target, false) && !pause.get()) {
				System.out.println( " score! " + target); //for checking
				score.caughtWord(target.length());	
				break;
			}
		
			//check Hungry words
			if ((HungryWords.get(i)).matchWord(target, true) && !pause.get()) {
				System.out.println( " score! " + target); //for checking
				score.caughtWord(target.length());	
				break;
			}
		   i++;
		}		
	}
	
}
