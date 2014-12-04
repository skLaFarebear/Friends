//@author Farris Awadallah and Leon Chen
package apps;

import java.io.*;
import java.util.*;
import java.lang.*;

public class Friends{
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		
		int friendSize; 
		
		System.out.print("Enter file name: ");
		
		try{
			FileReader newFile = new FileReader(sc.next());
			BufferedReader line = new BufferedReader(newFile);
			try{
				friendSize = Integer.parseInt(line.readLine());
				String linedude = line.readLine();
				while(linedude!=null) {
					StringTokenizer Friender = new StringTokenizer(linedude, "|");
					FriendNode newNode = new FriendNode(Friender.nextToken());  //name of each person in line
					String nextToken = Friender.nextToken();
					if(nextToken.equals("y")) {   //if they're in school or not
						newNode.school = Friender.nextToken();
						System.out.println(newNode.toString());
					}else if(nextToken.equals("n")){
						System.out.println(newNode.toString());
					}else{
						
						break;
					}
					linedude = line.readLine();
				}
				while(linedude!=null){
					
				}
			}catch(IOException e) {
				System.out.println(e + ": Catch Wrong Name");
				System.out.println("shit");
				return;
			}
		}catch(FileNotFoundException e) {
			System.out.println(e + ": Catch Wrong Name");
			System.out.println("shit");
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
	
	FriendNode head;
	String name;
	String school;
	FriendNode edge;
	
	public FriendNode(String name){
		this.name = name;
	}
	
	public String toString(){
		String FrienderNode = "(" + name + ", " + school + ")";
		return FrienderNode;
		
	}
	
}