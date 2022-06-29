import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class QuestionTree {

	//Fields
	private QuestionNode<String> root;
	private QuestionNode<String> curr;
	private QuestionNode<String> prev;
	private boolean yn;
	private int size;
	
	
	
	//Constructor
	
	/**
	 * Creates a question tree that initially will guess cat without asking any questions
	 */
	public QuestionTree() {
		this.root = new QuestionNode<String> ("A: cat", null, null);
		this.curr = this.root; 
		this.size = 1;
	}
	
	/**
	 * Creates a question tree pre-populated with the data in the file. 
	 * If the file does not exist, it creates a tree that will initially guess a cat without asking any questions
	 * @param filename - the files containing the data for the tree in pre-order traversal Questions begin with Q: and answers begin with A:
	 * @throws FileNotFoundException 
	 */
	public QuestionTree(String filename) {
		File file = new File(filename);
		try {
			this.root = preOrderAddition(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.curr = this.root;
	}
	
	
	//Public Methods
	/**
	 * Adds a new question to the tree at the current location with the new item 
	 * at the branch associated with the specified answer and the previous item on the other branch
	 * @param question - the question being added to the tree
	 * @param item - the new item to add to the tree
	 * @param answer - whether the new item has an answer yes or no to the question
	 */
	public void addQuestion(String question, String item, String answer) {
		if(this.size == 1) {
			if (answer.equals("yes")) {
				this.root = new QuestionNode<String>("Q: "+question,new QuestionNode<String>("A: "+item),this.root);
			}else {
				this.root = new QuestionNode<String>("Q: "+question,this.root, new QuestionNode<String>("A: "+item));
			}
		}else {
			if(yn) {
				if (answer.equals("yes")) {
					this.prev.setLeft(new QuestionNode<String>("Q: "+question, new QuestionNode<String>("A: "+item),this.curr));
				}else {
					this.prev.setLeft(new QuestionNode<String>("Q: "+question,this.curr, new QuestionNode<String>("A: "+item)));
				}
			}else {
				if (answer.equals("yes")) {
					this.prev.setRight(new QuestionNode<String>("Q: "+question, new QuestionNode<String>("A: "+item),this.curr));
				}else {
					this.prev.setRight(new QuestionNode<String>("Q: "+question,this.curr, new QuestionNode<String>("A: "+item)));
				}
			}
		}
	}
	
	/**
	 * Determines if the current location is a guess or a question
	 * @return
	 */
	public boolean isGuess() {
		if(this.curr.getData().charAt(0) == 'A') {
			return true;
		}
		return false;
	}
	
	/**
	 * Resets the game back to the first question
	 */
	public void restartGame() {
		this.curr = this.root;
	}
	
	/**
	 * Outputs the tree in pre-order traversal to a file
	 * @param filename - the file to save the tree to
	 */
	public void save(String filename) {
		File file = new File(filename);
		writeToFile(file);
	}
	/**
	 * Moves the computer's location to the next question or guess based on the specified answer to the current question
	 * @param answer - the answer to the question at the current location in the form of yes/y or no/n
	 */
	public void nextQuestion(String answer) {
		this.prev = this.curr;
		if (answer == "yes" || answer == "y") {
			this.curr = this.curr.getLeft();
			this.yn = true;
		}else if (answer == "no" || answer == "n") {
			this.curr = this.curr.getRight();
			this.yn = false;
		}else {
			
		}
	}
	
	
	//Private Helpers
	private QuestionNode<String> preOrderAddition(File file) throws FileNotFoundException {
		Scanner sc = new Scanner(file);
		
		if(isGuess(sc.nextLine())) {
			this.size++;
			return new QuestionNode<String>(sc.nextLine());
		}else {
			QuestionNode<String> newNode = new QuestionNode<String>(sc.nextLine());
			this.size++;
			newNode.setLeft(preOrderAddition(sc.nextLine(),sc));
			newNode.setRight(preOrderAddition(sc.nextLine(),sc));
			return newNode;
		}
		
	}
	
	private QuestionNode<String> preOrderAddition(String text, Scanner sc){
		
		if(isGuess(sc.nextLine())) {
			this.size++;
			return new QuestionNode<String>(sc.nextLine());
		}else {
			QuestionNode<String> newNode = new QuestionNode<String>(sc.nextLine());
			this.size++;
			newNode.setLeft(preOrderAddition(sc.nextLine(),sc));
			newNode.setRight(preOrderAddition(sc.nextLine(),sc));
			return newNode;
		}
	}
	
	private boolean isGuess(String data) {
		if(data.charAt(0) == 'A') {
			return true;
		}
		return false;
	}
	
	private void writeToFile(File file) {
		FileWriter write;
		try {
			write = new FileWriter(file);
			writeToFile(this.root,write);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void writeToFile(QuestionNode<String> curr,FileWriter write) {
		if(!curr.equals(null)) {
			try {
				write.write(curr.getData());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writeToFile(curr.getLeft(),write);
			writeToFile(curr.getRight(),write);
		}
	}
	
	
	
	
	//Setters and Getters
	/**
	 * Gets the current question or guess of the computer
	 * @return
	 */
	public String getCurrentText() {
		return this.curr.getData();
	}
}
