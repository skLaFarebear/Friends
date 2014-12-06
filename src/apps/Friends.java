//@author Farris Awadallah and Leon Chen
package apps;

import java.io.*;
import java.util.*;
import java.lang.*;

public class Friends{
	
	public static void main(String[] args){
		
		// Creates a new scanner for user input
		Scanner userInput = new Scanner(System.in);
		
		// # of students in a file
		int totFriendSize; 
		FriendNode deGraph[];
		
		System.out.print("Enter file name: ");
		
		try {
			FileReader newFile = new FileReader(userInput.next()); // Sets file location
			BufferedReader wholeFile = new BufferedReader(newFile); // Takes buffer of file 
			
			try {
				totFriendSize = Integer.parseInt(wholeFile.readLine());
				//System.out.print(friendSize);
				
				// Checks if graph is empty
				if(totFriendSize <= 0) {
					System.out.println("No Graph Exists! No Students Found!");
					return;
				}
				
				//Starts making graph
				deGraph = new FriendNode[totFriendSize];
				
				
				int stuCount = 0;
				
				do {
					
					// New Line of file
					String nextLine = wholeFile.readLine();
					
					// Checks if line is null
					if (nextLine == null) {
						System.out.println(": Don't lie about the number of students in file!: C1");
						return;
					}
					
					// Parsed values of the line
					StringTokenizer lineParsed = new StringTokenizer(nextLine, "|"); 
					
					
					// While we are guaranteed that the list are still students, checks for less than 2 inputs and more than 3.
					int tokenAmount = lineParsed.countTokens();
					
					if (tokenAmount < 2 || tokenAmount > 3) {
						System.out.print(": Either too many or too little input values!: C1.1");
						return;
					}
					
					// Distributed values for parsed line
					String person = lineParsed.nextToken().toLowerCase(); // person  
					String schoolBool = lineParsed.nextToken().toLowerCase(); // yes or no
					
					// Setting up new nodes
					FriendNode newNode = new FriendNode(person, stuCount); 
					//System.out.print(person);
					//System.out.print(schoolBool);
					
					if(schoolBool.equals("y")) {
						
						// Checks if a school portion is added. 
						if(!lineParsed.hasMoreTokens()) {
							System.out.print(": This student should have a school!: C1.2");
							return;
						}
						
						String school = lineParsed.nextToken().toLowerCase();
						
						newNode.school = school; 
						//System.out.print(school);
					} else {
						// While we are still guaranteed a good list of names. Checks if second field is anything other than n. 
						if(!schoolBool.equals("n")) {
							System.out.println(": Field requires y or n or it's missing first field or wrong people size!: C1.3");
							return;
						}
						
						// If no school, but there are more words. Harsh check
						if(lineParsed.hasMoreTokens()) {
							System.out.print(": Shouldn't have anymore things in here: C1.4");
							return;
						}
					}
					
					deGraph[stuCount] = newNode;
					
					System.out.println(deGraph[stuCount]);
					
					++stuCount; // Increments student count
					
				} while (stuCount < totFriendSize);
				
				// For relationships between people
				String relationLine = wholeFile.readLine();
				int oneIdx = 0;
				int twoIdx = 0;
				
				while(relationLine != null) {
					
					StringTokenizer lineParsed = new StringTokenizer(relationLine, "|"); 
					
					// Parsed values from line 
					String relationOne = lineParsed.nextToken().toLowerCase();
					String relationTwo = lineParsed.nextToken().toLowerCase();
					
					// Checking if # of people overlaps with relationships 
					if(relationTwo.equals("y") || relationTwo.equals("n")) {
						System.out.println(": Adjust the number of students at start of file!: C2: ");
						return;
					} 
					
					for (int oneCnt = 0; oneCnt <= totFriendSize; oneCnt++) {
						
						// Checking if person exists 
						if(oneCnt == totFriendSize) {System.out.println(relationOne + " does not exist! C2.1"); return;}
						
						if(relationOne.equals(deGraph[oneCnt].name)) {
							oneIdx = oneCnt;
							break;
						} 
					}
					
					for (int twoCnt = 0; twoCnt <= totFriendSize; twoCnt++) {
						
						// Checking if person two exists 
						if(twoCnt == totFriendSize) {System.out.println(relationOne + " does not exist! C2.2"); return;}
						
						if(relationTwo.equals(deGraph[twoCnt].name)) {
							
							twoIdx = twoCnt;
							
							break;
						} 
					}
					
					/*// Sets head of graph
					
					if(deGraph[oneIdx].adjList == null || deGraph[twoIdx].adjList == null) {
						deGraph[oneIdx].adjList = new Neighbor(twoIdx, null);
						deGraph[twoIdx].adjList = new Neighbor(oneIdx, null);
						System.out.print("set");
						continue;
					}

					
					for (Neighbor pntOne = deGraph[oneIdx].adjList; pntOne == null; pntOne = pntOne.next) {
						
						if(pntOne.next == null) {
							pntOne.next.next = new Neighbor(twoIdx, null);
							System.out.print("add to end?");
						}
					}

					
					System.out.println(oneIdx + " " + twoIdx);
					System.out.println(relationOne + " " + relationTwo);*/
					
				
					relationLine = wholeFile.readLine();
					
				} 
				
				//System.out.println(oneIdx + " " + twoIdx);
				
				//System.out.print(Neighbor.in);
				
				/*// Goes through every line of file
				while(linedude != null) {
					
					StringTokenizer Friender = new StringTokenizer(linedude, "|");
					
					FriendNode newNode = new FriendNode(Friender.nextToken());  // name of each person in line
					
					String nextToken = Friender.nextToken();
					
					if(nextToken.equals("y")) {   // if they're in school or not
						
						newNode.school = Friender.nextToken();
						System.out.println(newNode.toString());
						
					} else if(nextToken.equals("n")){
						
						System.out.println(newNode);
						
					} else {
						//System.out.print("hi");
						break;
					}
					
					linedude = wholeFile.readLine();
					++stuCount;
				}
				
				while(linedude!=null){
					
				}*/
				
			} catch(NumberFormatException e) {
				
				System.out.println(e + ": First line of document should be number.");
				System.out.println("Fix the damn file.");
				return;
				
			} catch(IOException e) {
				
				System.out.println(e + ": I wonder...");
				System.out.println("Something wrong with input output.");
				return;
				
			}
			
		} catch(FileNotFoundException e) {
			System.out.println(e + ": Wrong document name.");
			System.out.println("Type in the right document name!");
			return;
			
		}
		
		
		/*try {
		//System.out.print(sc.next());
		Scanner line = new Scanner(new File(sc.next()));
		
		friendSize = Integer.parseInt(line.next()); 
		
		System.out.println(friendSize);
		//System.out.print(line.hasNext());
		
		//System.out.println(line.next());
		
		
		while(line.hasNext()) {
			StringTokenizer Friender = new StringTokenizer(line.next(), "|");
			FriendNode newNode = new FriendNode(Friender.nextToken());  //name of each person in line
			if(Friender.nextToken().equals("y")) {   //if they're in school or not
				newNode.school = Friender.nextToken();
				System.out.println(newNode.toString());
			}else if(Friender.nextToken().equals("n")){
				System.out.println(newNode.toString());
				continue;
			}
		}
		
		
		} catch(FileNotFoundException e) {
			System.out.println(e + ": Catch Wrong Name");
			System.out.println("shit");
			return;
			
		}*/
		
		
	
		
		//System.out.print();
		
		// Build graph
		
		
		/*System.out.println("Enter 1 to find the students at the school");
		System.out.println("Enter 2 to find the shortest chain from a person");
		System.out.println("Enter 3 to find the cliques at each school");
		System.out.println("Enter 4 to find the connectors");
		System.out.println("Enter 5 to quit");*/
		
		
		
		
		// User chooses a number 
		System.out.print("Enter choice: ");
		
		
		
		
		
		//int numChose = sc.nextInt();
		
		
		
		
		
		//System.out.println(a);
		
		// check check sss 
		// check farris's check
		// Everything should be working at this point.
		
		
	}
	
}

class FriendNode{
	
	String name;
	String school;
	int idxNum;
	
	Neighbor adjList;
	
	public FriendNode(String name, int idxNum){
		this.name = name;
		this.idxNum = idxNum;
	}
	
	public String toString(){
		String FrienderNode = "(" + name + ", " + school + "," + idxNum + ")";
		return FrienderNode;
		
	}
	
}

class Neighbor {
	
	int vertexNum;
	Neighbor next; 
	
	public Neighbor(int num, Neighbor nbr) {
		this.vertexNum = num;
		this.next = nbr; 
	}

}



