package quotes;

import java.util.Scanner;

public class main {

	static int recentSearchCount = 0;
	static String[] a = new String[5];
	static QuoteSaxParser parser = new QuoteSaxParser("quotes/quotes.xml");
	static QuoteList q = parser.getQuoteList(); ///Initialize list and create quotes
	
	public static void main(String[] args) {
		
		recentSearch("");//changed
		userChoice();//ask user what they want to do
		
	}
	
	public static void userChoice(){
		boolean stop = true;
		while(stop){
			
			box();///box function is to make a box for operation menu
			
			System.out.print("Add quote, Random quote, Search or Exit?(type A, R, S, or E): ");
			Scanner sc = new Scanner(System.in);
			String choice = sc.nextLine();
			Quote ans = new Quote();
			switch(choice){
				case "A":
				case "a":
					boolean flag = false;
					System.out.println("Please enter quote:");
					String quote = sc.nextLine(); 
					
					if(!validInput(quote)){///check for invalid input
						flag = true;///if invalid, raise flag, and break 
					}
					
					System.out.println("Please enter author:");
					String author = sc.nextLine();
					
					if(!validInput(author)){///check for invalid input
						flag = true; ///if invalid, raise flag, and break 
					}
					
					if(!flag){ ///check if the quote already exists, if it exists, raise flag
						for(int i= 0; i < q.getSize(); i++){///loop through the list
							if(q.getQuote(i).getQuoteText().equals(quote)){ ///search to see if quote already exists
								System.out.println("Error: unable to add because quote already exists\n");
								flag = true;
							}
						}
					}
					if(!flag){
						//q.addQuote(author, quote); ///be careful with order in the parameter, make sure it is addQuote(author, quote)
						AddXMLQuote adder = new AddXMLQuote();
						try {
							adder.addQuoteToXML(quote, author);
						} catch (Exception e) {
							System.out.println("Failed to add to XML:"+e);
						}
						parser = new QuoteSaxParser("quotes/quotes.xml");
						q = parser.getQuoteList();
						//System.out.println(q.getSize()+"---------");
					}
					
					break;
				
				case "R":
				case "r":
					ans = q.getRandomQuote(); ///get a random quote
					System.out.println(ans.getQuoteText());
					System.out.println("———"+ans.getAuthor());
					break;
					
				case "S":
				case "s": ///search for a quote or author
					System.out.print("Scope (type quote, author, or both): ");
					boolean innerSwitchStop = true;
						while(innerSwitchStop){
							choice = sc.nextLine();
							switch(choice){
								case "quote":
									boolean found = false;
									System.out.print("type in quote: ");
									choice = sc.nextLine(); 
									
									for(int i= 0; i < q.getSize(); i++){ ///loop through the list
										if(q.getQuote(i).getQuoteText().contains(choice)){ //compare
											found = true;
											System.out.println(q.getQuote(i).getQuoteText());
											System.out.println("———"+q.getQuote(i).getAuthor());
											
										}
									}
									if(found==false){
										System.out.println("quote not found");
										System.out.println();

									}
									recentSearch(choice);//changed
									innerSwitchStop = false;
									break;
								case "author":
									
									System.out.print("type in author: ");
									choice = sc.nextLine();
						
									found = false;
									for(int i= 0; i < q.getSize(); i++){///loop through the list
										if(q.getQuote(i).getAuthor().contains(choice)){//compare
											found = true;
											System.out.println(q.getQuote(i).getQuoteText());
											System.out.println("———"+q.getQuote(i).getAuthor());

										}
									}
									if(found==false){
										System.out.println("quote not found");
				
									}
									recentSearch(choice);//changed
									innerSwitchStop = false;
									
									break;
		
								case "both":
									System.out.print("--> ");
									choice = sc.nextLine();
									recentSearch(choice);
									found = false;
									for(int i= 0; i < q.getSize(); i++){///loop through the list
										if(q.getQuote(i).getQuoteText().contains(choice)||q.getQuote(i).getAuthor().contains(choice)){
											found = true;
											System.out.println(q.getQuote(i).getQuoteText());
											System.out.println("———"+q.getQuote(i).getAuthor());
					
										}
									}
									if(found==false){
										System.out.println("quote not found");
								
									}
									recentSearch(choice);//changed
									innerSwitchStop = false;
									break;
								default:
									System.out.println("quote not found");
										break;
							}///switch close brace
						}
					break;
			////end of case "S"
					
				case "E":
				case "e":
					System.out.println("Program Terminated...");
					stop = false;
					break;
				default:
					System.out.println("Invalid Input");
					break;	
			}//switch close brace
		}//while close brace
		
	}
	

	public static void box(){
		int x = 0;
		while(x<21){
			System.out.print(">");
			x++;
			if(x == 10){
				System.out.print("Opeartion Menu");
			}else if(x==21){
				System.out.println(">");
			}
		}
		int z = 0;
		while(z<7){
			if(z==1){
				System.out.println("^                                  ^");
				System.out.print("^    A for Adding a new quote ");
				System.out.println("     ^");
			}
			else if(z==2){
				System.out.print("^    R for random quote ");
				System.out.println("           ^");
			}
			else if(z==3){
				System.out.print("^    S for search");
				System.out.println("                  ^");
			}
			else if(z==4){
				System.out.print("^    E for exit  ");
				System.out.println("                  ^");
			}
			else if(z==5){
				System.out.print("^                                  ^");
				System.out.println();
			}
			else if(z==6){
				System.out.print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
				System.out.println();
			}
			z++;
		}
	
		System.out.println();
	}

	public static void recentSearch(String search){//changed
		
		System.out.println(">>>>>>>>>>>>>>>>>>recent search<<<<<<<<<<<<<<<<<<");
		System.out.println();
		
		if(recentSearchCount==0){
			for(int i = 0; i < 5; i++){
				a[i] = "";
			}
			System.out.println("");
		}else{
			if(recentSearchCount<5){
				a[recentSearchCount-1] = search;
				for(int i = 0; i < recentSearchCount; i++){
						System.out.println(i+1 + ". "+ a[i]);
				}
			}else{
				for(int i = 0; i < 4; i++){
					a[i] = a[i+1];
				}
				a[4] = search;
				for(int i = 0; i < 5; i++){
					System.out.println(i+1 + ". "+ a[i]);
				}
			}

		}
		System.out.println();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println();
		recentSearchCount++;	
	}
	
	public static boolean validInput(String userInput){
		
		boolean temp = true;
		
		if(userInput.length() > 300){
			temp = false;
			System.out.println("Error: input entered is too long\n");
			return temp;
		}
		if(userInput.isEmpty()){
			temp = false;
			System.out.println("Error: input cannot be empty");
			return temp;
		}
		if(userInput.charAt(0) == ' ' || userInput.charAt(0) == '\n'){
			temp = false;
			System.out.println("Error: input cannot start with a whitespace");
			return temp;
		}
		
		return temp;
	}
	
}
