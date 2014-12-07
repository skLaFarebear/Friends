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
				
				
				
				/* ****************************************************** 
				 * Gets the relationships and puts them in graph
				 ********************************************************/ 
				String relationLine = wholeFile.readLine();
				int oneIdx = 0;
				int twoIdx = 0;
				
				while(relationLine != null) {
					
					StringTokenizer lineParsed = new StringTokenizer(relationLine, "|");
					
					int tokenAmount = lineParsed.countTokens();
					
					// Checks for skipped spaces. 
					if (tokenAmount < 2 || tokenAmount > 2) {
						System.out.print("Problem with relationships list: C2");
						return;
					}
					
					// Parsed values from line 
					String relationOne = lineParsed.nextToken().toLowerCase();
					String relationTwo = lineParsed.nextToken().toLowerCase();
					
					// Checking if # of people overlaps with relationships 
					if(relationTwo.equals("y") || relationTwo.equals("n")) {
						System.out.println(": Adjust the number of students at start of file!: C2.1: ");
						return;
					} 
					
					// Goes through each person in the people list, sets first relation's index 
					for (int oneCnt = 0; oneCnt <= totFriendSize; oneCnt++) {
						
						// Checking if person exists 
						if(oneCnt == totFriendSize) {System.out.println(relationOne + " does not exist! C2.2"); return;}
						
						if(relationOne.equals(deGraph[oneCnt].name)) {
							oneIdx = oneCnt;
							break;
						} 
					}
					
					// Goes through each person in the people list, sets second relation relation's index 
					for (int twoCnt = 0; twoCnt <= totFriendSize; twoCnt++) {
						
						// Checking if person two exists 
						if(twoCnt == totFriendSize) {System.out.println(relationTwo + " does not exist! C2.3"); return;}
						
						if(relationTwo.equals(deGraph[twoCnt].name)) {
							
							twoIdx = twoCnt;
							break;
						} 
					}
					
					// Make new neighbor nodes 
					Neighbor newNeighbor = new Neighbor(twoIdx, null);
					Neighbor undirNeighbor = new Neighbor(oneIdx, null);
					
					// Adds the newly made list as head if the first relation is null
					if(deGraph[oneIdx].adjList == null) {
						/* ****************************************************** 
						 * If head of the vertex is null, then second relationship is either null and needs making
						 * or it already exists and needs to be added too
						 ********************************************************/ 
						if(deGraph[twoIdx].adjList == null) {
							System.out.println("C2.5");
							deGraph[twoIdx].adjList = undirNeighbor;
						} else {
							
							for (Neighbor pntOne = deGraph[twoIdx].adjList; pntOne != null; pntOne = pntOne.next) {
								
								if(pntOne.next == null) {
									pntOne.next = undirNeighbor;
									System.out.println("C2.9009");
									break;
								}
							}
							
						}
						
						System.out.println("C2.55");
						deGraph[oneIdx].adjList = newNeighbor;
						
					} else {
						
						// Adds the new neighbor to the end of the list 
						//deGraph[oneIdx].adjList.next = newNeighbor;
						System.out.println("C2.555");
						
						// Checks if a connection needs to be made if second relation is added to end of first relation
						if(deGraph[twoIdx].adjList == null) {
							System.out.println("C2.77");
							deGraph[twoIdx].adjList = undirNeighbor;
						}
						
						for (Neighbor pntOne = deGraph[oneIdx].adjList; pntOne != null; pntOne = pntOne.next) {
							
							if(pntOne.next == null) {
								pntOne.next = newNeighbor;
								System.out.println("C2.99");
								break;
							}
						}
						
						//deGraph[oneIdx].adjList.next = newNeighbor;
					}
					
					
					//System.out.println(deGraph[oneIdx].idxNum + " --> " + deGraph[oneIdx].adjList.vertexNum + "fuck");
					//System.out.println(deGraph[twoIdx].idxNum + " --> " + deGraph[twoIdx].adjList.vertexNum + "fuck2");
					
					
					System.out.print(deGraph[oneIdx].name + " --> ");
					for(Neighbor pntr = deGraph[oneIdx].adjList; pntr != null; pntr = pntr.next) {
						System.out.print(deGraph[pntr.vertexNum].name + "--> ");
						//System.out.println();
						//System.out.print("C2.4");
					}
					
					
					System.out.println();

					
					System.out.println(oneIdx + " " + twoIdx);
					System.out.println(relationOne + " " + relationTwo);
					
					//break;
					relationLine = wholeFile.readLine();
					
				}
				// end of while 
				// ending of try
				
				/* ****************************************************** 
				 * USE THIS SECTION TO TEST YOUR CODE
				 ********************************************************/ 
				
				System.out.print(deGraph[Friends.nameintConv("nick", deGraph)].name + " --> ");
				for(Neighbor pntr = deGraph[Friends.nameintConv("nick", deGraph)].adjList; pntr != null; pntr = pntr.next) {
					System.out.print(deGraph[pntr.vertexNum].name + "--> ");
					//System.out.println();
					//System.out.print("C2.4");
				}
				
				
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
		
		
		System.out.println("Enter 1 to find the students at the school");
		System.out.println("Enter 2 to find the shortest chain from a person");
		System.out.println("Enter 3 to find the cliques at each school");
		System.out.println("Enter 4 to find the connectors");
		System.out.println("Enter 5 to quit");
		
		
		
		
		// User chooses a number 
		System.out.print("Enter choice: ");
		
		
		
		
		
		//int numChose = sc.nextInt();
		
		/*System.out.println(nameintConv("sam", deGraph));
		System.out.println(Friends.nameintConv("jane", deGraph));
		System.out.println(Friends.nameintConv("nick", deGraph));*/
		//Friends.
		
	}
	
	
	// This method does name conversion to index. 
	public static int nameintConv(String name, FriendNode daShit[]) {
		
		for(int x = 0; x < daShit.length; x++) {
			if(daShit[x].name.equals(name)) {
				return  daShit[x].idxNum;
			}
		}
		
		return -1;
	}
	
	
	
	//Start code here
	
}

class FriendNode{
	
	
	String name;
	String school;
	int idxNum;
	
	Neighbor adjList; // head of the neighbor 
	
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



