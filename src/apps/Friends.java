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
		
		//Scanner userInput2 = new Scanner(System.in);
		
		
		
		System.out.println("Enter 1 to find the shortest chain from a person");
		System.out.println("Enter 2 to find the cliques at each school");
		System.out.println("Enter 3 to find the connectors");
		System.out.println("Enter 4 to quit");
		
		
		System.out.println("Enter choice: ");
		
		String decision = userInput.next();
		
		while(!decision.equals("4")) {
			
			switch(Integer.parseInt(decision)) {
				case 1:
					System.out.println("Enter person where you want to start search");
					String name1 = userInput.next().toLowerCase();
					System.out.println("Enter the person you want to find in the friend chain");
					String name2 = userInput.next().toLowerCase();
					shortPath(name1, name2, totFriendSize, deGraph);
					System.out.println("Enter choice: ");
					decision = userInput.next();
					continue;
				case 2:
					System.out.println("Enter the school that you want the cliques for");
					String school = userInput.next();
					
					// Fix this...
					//System.out.print(userInput.hasNext());
					/*if(userInput.hasNext()) {
						school = school.concat(" " );
					}*/
					System.out.print(school);
					cliques(school.toLowerCase(), deGraph);
					System.out.println("Enter choice: ");
					decision = userInput.next();
					continue;
				case 3:
			}
			
			System.out.println("Enter 1 to find the shortest chain from a person");
			System.out.println("Enter 2 to find the cliques at each school");
			System.out.println("Enter 3 to find the connectors");
			System.out.println("Enter 4 to quit");
			
			
			System.out.print("Enter choice: ");
			
			decision = userInput.next();
			
		}
		
		
		// User chooses a number 
		//System.out.print("Enter choice: ");
		
		
		//String cool = userInput.next();
		
		//System.out.print(cool);
		
		
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
	
	
	public static void shortPath(String relation1, String relation2, int totFriendSize, FriendNode deGraph[]) {
		ArrayList<String> list = new ArrayList<String>();
		String nameChecked[] = new String[totFriendSize];
		nameChecked[0] = relation1;
		
		int counter = 0; // keeps track of the amount of people already searched
		int nameCheckCount = 1;
		list = DFS(relation1, nameChecked, relation2, list, deGraph, totFriendSize, counter, nameCheckCount);
		for(int i=0;i<list.size();i++){
			System.out.print(list.get(i) + "-->");
		}
		System.out.println();
	}
	
	private static ArrayList<String> DFS(String start, String[] nameChecked, String finish, ArrayList<String> list, FriendNode deGraph[], int totFriendSize, int counter, int nameCheckCount){
		if(start.equals(finish)){
			for(int i = 0;i<list.size();i++){
				if(list.get(i).equals(start)){
					return list;
				}
			}
			list.add(finish);
			return list;
		}
		if(counter>=totFriendSize){
			return list;
		}
		
		list.add(start);
		
		FriendNode friendlist = deGraph[Friends.nameintConv(start, deGraph)];//goes to person vertex in array
		if(deGraph[friendlist.adjList.vertexNum].name == null){
			return list;
		}
		start = deGraph[friendlist.adjList.vertexNum].name; //first person in the list
		

		for(int x = 0; x < nameChecked.length; x++) {
			
			
			
			if(start.equals(nameChecked[x])) {
				
				if(friendlist.adjList.next == null) {
					return list; 
				}
				
				
				start = deGraph[friendlist.adjList.next.vertexNum].name;
				
			}
		}
		
		
		counter++;
		
		nameChecked[nameCheckCount] = start;
		++nameCheckCount;
		
		//System.out.println(start+"*");
		
		while(friendlist.adjList!=null){
			DFS(start, nameChecked, finish, list, deGraph, totFriendSize, counter, nameCheckCount);
			
			for(int x = 0; x < nameChecked.length; x++) {
				if(nameChecked[x].equals(start)){
					return list;
				}
			}
			
			
			friendlist.adjList=friendlist.adjList.next;
			
		}	
		
		
		return list;
				
	}
	
	//Start code here
	


public static void cliques(String school, FriendNode[] deGraph) {
	
	ArrayList<FriendNode> sameSkool = new ArrayList<FriendNode>();
	
	
	for(int x = 0; x < deGraph.length; x++) {
		
		if(deGraph[x].school == null) {
			continue;
		}
		
		if(deGraph[x].school.equals(school)) {
			sameSkool.add(deGraph[x]);
			System.out.print(deGraph[x]);
		}
	}
	
	if(sameSkool.isEmpty()) {
		System.out.print("Nobody goes to that school");
		return;
	}
	
	
	/*System.out.print(deGraph[Friends.nameintConv("sam", deGraph)].name + " --> ");
	for(Neighbor pntr = deGraph[Friends.nameintConv("sam", deGraph)].adjList; pntr != null; pntr = pntr.next) {
		System.out.print(deGraph[pntr.vertexNum].name + "--> ");
	}*/
	
	
	//for()

	ArrayList<FriendNode> clique = new ArrayList<FriendNode>();
	clique.add(sameSkool.get(0));
	sameSkool.set(0, new FriendNode(null, -1));
	//sameSkool.remove(2);
	
	System.out.println();
	
	//Prints out schoolmates
	for(int x = 0; x < sameSkool.size(); x++) {
		System.out.print(sameSkool.get(x));
	}
	System.out.println();
	
	boolean isEmpty = false;
	
	
	
	while(isEmpty != true)	{
	
		// This loop double checks the list for cases like penn state and ming
	for(int y = 0; y < sameSkool.size() + 1; y++) {
	for(int i = 1; i<sameSkool.size();i++){
		for(Neighbor pnter = sameSkool.get(i).adjList; pnter != null; pnter = pnter.next) {
			//System.out.print(sameSkool.get(i));
			//System.out.print(deGraph[pnter.vertexNum].name);
			//System.out.println();
			//System.out.print(clique.get(clique.size() - 1).idxNum);
			
			for(int x = 0; x < clique.size(); x++) {
				
				if(pnter.vertexNum == clique.get(x).idxNum) {
					clique.add(sameSkool.get(i));
					sameSkool.set(i, new FriendNode(null, -1));
				}
				
			}
				
			
		}
		
	}
	}
	

	
	// Prints size of clique 
	System.out.println(clique.size());
	
	// Gets list of clique
	for(int x = 0; x < clique.size(); x++) {
		System.out.println(clique.get(x).name + "|y|" + clique.get(x).school);
	}
	
	//Prints out schoolmates
	for(int x = 0; x < sameSkool.size(); x++) {
		System.out.print(sameSkool.get(x));
	}
	
	for(int m = 0; m < sameSkool.size(); m++) {
		
		if(sameSkool.get(m).name != null) {
			
			//System.out.print(m + "hey");
			//System.out.print(sameSkool.get(m) + "hey");
			
			clique.clear();
			clique.add(sameSkool.get(m));
			sameSkool.set(m, new FriendNode(null, -1));
			
			break; 
		}
		
		
		if(m == sameSkool.size()-1) {
			isEmpty = true; 
		}
		
	}
		//System.out.println();
		
		/*for(int x = 0; x < sameSkool.size(); x++) {
			System.out.print(sameSkool.get(x));
		}*/
	}
	
	
	
		
	
	
	System.out.println();
	
	/*System.out.print(deGraph[Friends.nameintConv("jane", deGraph)].name + " --> ");
	for(Neighbor pntr = deGraph[Friends.nameintConv("jane", deGraph)].adjList; pntr != null; pntr = pntr.next) {
		System.out.print(deGraph[pntr.vertexNum].name + "--> ");
	}*/
	
	/*for(int x = 0; x < clique.size(); x++) {
		System.out.print(clique.get(x).name);
	}*/
	
		
	}
	
	
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



