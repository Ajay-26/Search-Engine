import java.util.*;
import java.io.*;

class node<T>{
	public T data;
	public node<T> next;
	public node(T obj){
		this.data = obj;
		this.next = null;
	}
}
class myLinkedList<T>{
	public node<T> head;
	public myLinkedList(){
		this.head = null;
	}
	public boolean isEmpty(){
		return (null == head);
	}
	public boolean isMember(T item){
		node<T> curr = head;
		while(null != curr){
			if(item == curr.data){
				return true;
			}
			curr = curr.next;
		}
		if(null == curr){
			return false;
		}
		return false;
	}
	public void insertFront(T item){
		node<T> newHead = new node<T>(item);
		if(this.isEmpty()){
			head = newHead;
			return;
		}else{
			newHead.next = this.head;
			head = newHead;
			return;
		}
	}
	public void insertRear(T item){
		node<T> newNode = new node<T>(item);
		if(null == head){
			head = newNode;
			return;
		}else if(null == head.next){
			head.next = newNode;
			return;
		}else{
			node<T> curr = head;
			while(curr.next != null){
				curr = curr.next;
			}
			curr.next = newNode;
			return;
		}
	}
	public void deleteFront(){
		if(null == head){
			return;
		}else if(null == head.next){
			head = null;
		}else{
			node<T> curr = head;
			head = head.next;
			curr = null;
			return;
		}
	}
	public void deleteRear(){
		if(null == head){
			return;
		}else if(null == head.next){
			head = null;
			return;
		}else{
			node<T> curr = head;
			while(curr.next != null){
				curr = curr.next;
			}
			curr = null;
			return;
		}
	}
	public void joinList(myLinkedList<T> otherList){
		node<T> curr = this.head;
		if(null == curr){
			head = otherList.head;
			return;
		}else{
			while(null != curr.next){
				curr = curr.next;
			}
			curr.next = otherList.head;
			return; 
		}
	}
}

class mySet<X>{
//	public X element;
	public myLinkedList<X> list;
	public mySet(){
		this.list = new myLinkedList<X>();
	}
	public boolean isEmpty(){
		return this.list.isEmpty();
	}
	public boolean isMember(X element){
		return this.list.isMember(element);
	}
	public void addElement(X element){
		this.list.insertRear(element);
	}
	public mySet<X> Union(mySet<X> otherSet){
		mySet<X> unionSet = new mySet<X>();
		node<X> curr = this.list.head;
		while(curr != null){
			unionSet.addElement(curr.data);
			curr = curr.next;
		}
		curr = otherSet.list.head;
		while(null != curr){
			if(this.isMember(curr.data)){
				unionSet.addElement(curr.data);
			}
			curr = curr.next;
		}
		return unionSet;
	}
	public mySet<X> Intersection(mySet<X> otherSet){
		mySet<X> intersectionSet = new mySet<X>();
		node<X> curr = this.list.head;
		while(null != curr){
			if(otherSet.isMember(curr.data)){
				intersectionSet.addElement(curr.data);
			}
			curr = curr.next;
		}
		return intersectionSet;
	}

}

class Position{
   	public pageEntry p;
   	public int wordIndex;
   	public Position(pageEntry p, int wordIndex){
   		this.p = p;
   		this.wordIndex = wordIndex;
   	}
   	public pageEntry getPageEntry(){
   		return this.p;
   	}
   	public int getWordIndex(){
   		return this.wordIndex;
   	}

}

class wordEntry{
	public String str;
	public myLinkedList<Position> positionList;
	public wordEntry(String word){
		this.str = word;
		this.positionList = new myLinkedList<Position>();
	}
	public void addPosition(Position position){
		this.positionList.insertRear(position);
		return;
	}
	public void addPositions(myLinkedList<Position> positions){
		this.positionList.joinList(positions);
		return;
	}
	public myLinkedList<Position> getAllPositionsForThisWord(){
		return positionList;
	}
	public float getTermFrequency(String word){
		node<Position> curr = this.positionList.head;
		float count = 0;
		while(null != curr){
			count = count + 1;
			curr = curr.next;
		}// you forgot to divide by total number of words in the webpage
		return count;
	}
	public String printPositionList(){
		node<Position> temp = this.positionList.head;
		String listOfPositions = "";
		while(null != temp){
			listOfPositions = listOfPositions + Integer.toString(temp.data.wordIndex) + " ";
			temp = temp.next;
		}
		return listOfPositions;
	}

	
	/*@Override
	public boolean equals (Object o){
		wordEntry w=(wordEntry)o;
		return(this.str.equals(w.str));
	}*/
}

class pageIndex{
	public myLinkedList<wordEntry> listOfEntries;
	public pageIndex(){
		listOfEntries = new myLinkedList<wordEntry>();
	}
	public void addPositionForWord(String str, Position p){
		node<wordEntry> curr = this.listOfEntries.head;
		while(curr != null){
			if(curr.data.str.equals(str)){
				curr.data.addPosition(p);
				return;
			}
			curr = curr.next;
		}
		wordEntry newEntry = new wordEntry(str);
		newEntry.addPosition(p);
		this.listOfEntries.insertRear(newEntry);
		return;
	}
	public myLinkedList<wordEntry> getWordEntries(){
		return this.listOfEntries;
	}
	public boolean containsWord(String word){
		node<wordEntry> curr = this.listOfEntries.head;
		while(null != curr){
			if(curr.data.str.equals(word)){
				return true;
			}
			curr = curr.next;
		}
		return false;
	}
}

class pageEntry{
	public String pageName;
	public pageIndex index;
	public int wordCount;
	public pageEntry(String pageName){
		this.pageName = pageName;
		this.index = new pageIndex();
		this.wordCount = 0;
	}
	public pageIndex getPageIndex(){
		return this.index;
	}
}

class myHashTable{

	myLinkedList[] hashList;
	public myHashTable(){
		hashList=(myLinkedList<wordEntry>[])new myLinkedList[503];
	}
	private int getHashIndex(String str){
		//hashList=(myLinkedList<wordEntry>[])new myLinkedList[1019];
		try{
				int hashInt=1;
			int i=0;
			while(i<= str.length()){
				hashInt = hashInt*((int)(str.charAt(i) - 'A'));
				i++; 
			}
			hashInt = hashInt%1019;
			hashInt = hashInt%500;
			hashInt = Math.abs(hashInt);
			return hashInt;
		}
		catch(Exception e){
			return 0;
		}
	}
	public void addPositionForWord(wordEntry w){
		int wordInt = getHashIndex(w.str);
		node<wordEntry> curr = hashList[wordInt].head;
		while(null != curr){
			if(curr.data.str.equals(w.str)){
				curr.data.positionList.joinList(w.positionList);
				return;
			}
			curr = curr.next;
		}
		if(null == curr){
			hashList[wordInt].insertRear(w);
		}
		return;
	}
}

class invertedPageIndex{
	public mySet<pageEntry> pageSet;
	public invertedPageIndex(){
		pageSet = new mySet<pageEntry>();
	}
	public void addPage(pageEntry p){
		this.pageSet.addElement(p);
	}
	public mySet<pageEntry> getPagesWhichContainWord(String str){
		mySet<pageEntry> wordSet = new mySet<pageEntry>();
		node<pageEntry> curr = this.pageSet.list.head;
		while(null != curr){
			if(curr.data.index.containsWord(str)){
				wordSet.addElement(curr.data);
			}
			curr = curr.next;
		}
		return wordSet;
	}
}

public class SearchEngine{
	public invertedPageIndex myIndex;
	public myHashTable hashTable;
	public SearchEngine(){
		myIndex = new invertedPageIndex();
		hashTable = new myHashTable();
	}
	public pageEntry getPageEntry(String page){
		pageEntry newPage = new pageEntry(page);
		pageIndex newIndex = newPage.getPageIndex();
		String newString;
		String[] requiredWords;
		try{
			BufferedReader br = new BufferedReader( new FileReader("./webpages/" + page));
			int count = 0;
			while((newString = br.readLine()) != null){
				newString = newString.toLowerCase();
				requiredWords = newString.split("\\s++|\\{|}|<|>|\\(|\\)|\\.|,|;|'|\"|\\?|#|!|-|:|=");
				for(int x=0; x<requiredWords.length;x++){
					if(!(requiredWords[x].equals(""))){
					//	if(requiredWords[x] != "" && requiredWords[x] != " " && requiredWords[x] != "a" && requiredWords[x] != "an" && requiredWords[x] != "they" && requiredWords[x] !="the" && requiredWords[x] !="these" && requiredWords[x] !="this" && requiredWords[x] !="for" && requiredWords[x] !="is" && requiredWords[x] !="are" && requiredWords[x] !="was" && requiredWords[x] !="of" && requiredWords[x] !="or" && requiredWords[x] !="and" && requiredWords[x] !="does" && requiredWords[x] !="will" && requiredWords[x] !="whose" ){
						if(!(requiredWords[x].equals("a")) && !(requiredWords[x].equals("an")) && !(requiredWords[x].equals("the")) && !(requiredWords[x].equals("they")) && !(requiredWords[x].equals("these")) && !(requiredWords[x].equals("this")) && !(requiredWords[x].equals("for")) && !(requiredWords[x].equals("is")) && !(requiredWords[x].equals("are")) && !(requiredWords[x].equals("was")) && !(requiredWords[x].equals("of")) && !(requiredWords[x].equals("or")) && !(requiredWords[x].equals("and")) && !(requiredWords[x].equals("does")) && !(requiredWords[x].equals("will")) && !(requiredWords[x].equals("whose"))){
							if(requiredWords[x].equals("stacks")){
								requiredWords[x] = "stack";
							}else if(requiredWords[x].equals("structures")){
								requiredWords[x] = "structure";
							}else if(requiredWords[x].equals("applications")){
								requiredWords[x] = "application";
							}
						Position newPosition = new Position(newPage, count+1);
						//wordEntry newEntry = new wordEntry(requiredWords[x]);
						//newEntry.addPosition(newPosition);
						//this.hashTable.addPositionForWord(newEntry);
						newIndex.addPositionForWord(requiredWords[x], newPosition); // place the word entries into the page index
						//newPage.wordCount++;
						//System.out.println(requiredWords[x] + " " + newPosition.wordIndex);
						}
						count++;
					}
				}
			}
			return newPage;
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
	public String performAction(String actionMessage){
		 String actionWord = "";
		int i=0;
		while(actionMessage.charAt(i) != ' '){
			actionWord = actionWord + actionMessage.substring(i,i+1);
			i++;
		}
		if(actionWord.equals("addPage")){
			i++;
			String x = actionMessage.substring(i,actionMessage.length());
			pageEntry newPage = this.getPageEntry(x);
			this.myIndex.addPage(newPage);


			//put the word entries of the page in your hash table
			return "";
		}else if(actionWord.equals("queryFindPagesWhichContainWord")){
			i++;
			String answer = "";
			String x = actionMessage.substring(i,actionMessage.length());
			x = x.toLowerCase(); //since we have to treat uppercase letters same as lowercase letters, I am converting the input to lowercase for the program
			try{
				mySet<pageEntry> myWordSet = this.myIndex.getPagesWhichContainWord(x);
				node<pageEntry> curr = myWordSet.list.head;
				if(null == curr){
					System.out.println("No webpage contains word " + x );
					throw new Exception();
				}
				while(null != curr){
					if(curr.next != null){
						answer = answer + curr.data.pageName + ", ";
					}else{
						answer = answer + curr.data.pageName;
					}
					curr = curr.next;
				}
				System.out.println(answer);
				return answer;
			}
			catch(Exception en){
				return "No webpage contains word " + x;
			}
		}else if(actionWord.equals("queryFindPositionsOfWordInAPage")){
			i++;
			String x = "";
			String y = "";
			while(actionMessage.charAt(i) != ' '){
				x = x + actionMessage.substring(i,i+1);
				i++;
			}
			i++;
			y = y + actionMessage.substring(i,actionMessage.length());
			String z = x;
			x = x.toLowerCase(); //since we have to treat uppercase letters same as lowercase letters, I am converting the input to lowercase for the program
			if(x.equals("stacks")){
				x = "stack";
			}else if(x.equals("structures")){
				x = "structure";
			}else if(x.equals("applications")){
				x = "application";
			}
			try{
				node<pageEntry> curr = this.myIndex.pageSet.list.head;
				while(null != curr){
					if(curr.data.pageName.equals(y)){
						node<wordEntry> temp = curr.data.index.listOfEntries.head;
						while(temp != null){
							if(temp.data.str.equals(x)){
								System.out.println(temp.data.printPositionList());
								return temp.data.printPositionList();
							}
							//System.out.println("a");
							temp = temp.next;
						}
						if(null == temp){
							System.out.println("Webpage " + y + " does not contain word " + z);
							return "Webpage " + y + " does not contain word " + z;
						}
					}
					curr = curr.next;
				}
				if(null == curr){
					System.out.println("No webpage " + y + " found");
					return "No webpage " + y + " found"; 
				}
			}
			catch(Exception ex){
				System.out.println(ex);
				return "";

			}
			return "";
		}
		return actionMessage;
	}
	public static void main ( String args [])
	{
		BufferedReader br = null;
		SearchEngine myEngine = new SearchEngine();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("actionsGD.txt"));

			while ((actionString = br.readLine()) != null) {
				myEngine.performAction(actionString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}

class checker
{
	public static void main ( String args [])
	{
		BufferedReader br = null;
		SearchEngine myEngine = new SearchEngine();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("actionsGD.txt"));

			while ((actionString = br.readLine()) != null) {
				myEngine.performAction(actionString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}