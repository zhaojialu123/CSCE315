#include <iostream>
#include <fstream>
#include <string>
#include <stdlib.h>     /* strtof */
#include <iomanip>
#include <vector>
#include <sstream>
#include <fstream>
#include "Book.h"
#include "Section.h"
#include "Course.h"
using namespace std;

// compile: make 
// run: ./run

int main()
{
  string command;   // read from user's standard input 
  vector<Book> book_list; // using a vector to store a list of book 
  vector<Course> course_list; // using a vector to store a list of course 

// unless the users type Q, keep running to get input from users 
while(true)
{ 
  bool jump_judge = false; // use to stop running 
  cout << "Please, enter your command(following standard input): " << endl;
  getline (cin,command);

  stringstream ss;
  ss << command;

  char comm = command[0]; // the first letter of the input is used to separate different cases 
  Book B; // define a book first 
  Course C; // define a course first before assigned a book to this course 
  switch(comm)
  {
    case 'Q': // QUIT 
        jump_judge = true;
    break;

  	case 'B': // define a book 
    {
  			// add ISBN and Title to this book
  			B.ISBN = command.substr(2,13);
  			B.Title = command.substr(16,100);
  			// if the book has already been defined, just update it or push it back to the list of book
  			bool judge = false;
  		  for (int i = 0; i < book_list.size(); i++) 
  			{ 
  				if(command.substr(2,13) == book_list[i].ISBN)
  				{
  						book_list[i] = B;
  						judge = true;
  				}
  			   if(judge)
  						break;
				}
				// can't find it in the library, push it back to the list 
				if(judge == false)
  			  	book_list.push_back(B);
  	break;
    }

  	case 'D': // define a characteristic of a book 
    {
  				// iterate the book list to match the ISBN
  				bool judge = false;
  				for (int i = 0; i < book_list.size(); i++) 
  				{ 
  					if(command.substr(2,13) == book_list[i].ISBN)
  					{
              if(command[16] == 'A')
  						    book_list[i].Author = command.substr(18,100);
                else if(command[16] == 'E')
                  book_list[i].Edition = command.substr(18,100);
                else
                  book_list[i].Date = command.substr(18,100);
  						judge = true;
  					}
  					if(judge)
  						break;
				}
				if(judge == false)
					cout << "Sorry, can't find this book in your library!!" << endl;
  	
  	break;
    }

  	case 'M':   // define the cost of a book, note: a book might have different costs for different format 
    {
  				// iterate the book list to match the ISBN
  				bool judge = false; 
          // for iterating the command to get the cost number 
          string first, second, third, fourth;
          ss >> first >> second >> third >> fourth;

  				for (int i = 0; i < book_list.size(); i++) 
  				{ 
            // match diffferent format and give its cost 
  					if(second == book_list[i].ISBN)
  					{
              if(command[22] == 'N')
              {
                book_list[i].cost_N = third;
                book_list[i].format_N = 'N';
              }
              else if(command[22] == 'U')
              {
                book_list[i].cost_U = third;
                book_list[i].format_U = 'U';
              }
              else if(command[22] == 'R')
              {
                book_list[i].cost_R = third;
                book_list[i].format_R = 'R';
              }
              else
              {
                book_list[i].cost_E = third;
                book_list[i].format_E = 'E';
              }
  						judge = true;
  					}
  					if(judge)
  						break;
				}
				if(judge == false)
					cout << "Sorry, can't find this book in your library!!" << endl;
  	

  	break;
    }

  	case 'C':// deifne a course
    {
  				// add department code, course number and name to this course
  				  C.depart_code = command.substr(2,4);
  			    C.course_num = command.substr(7,3);
  			    C.course_name = command.substr(11,100);
  			    // if the book has already been defined, just update it or push it back to the list of book
  			    bool judge = false;
  				for (int i = 0; i < course_list.size(); i++) 
  				{ 
  					if(command.substr(2,4) == course_list[i].depart_code && command.substr(7,3) == course_list[i].course_num)
  					{
  						course_list[i] = C;
  						judge = true;
  					}
  					if(judge)
  						break;
				}
				// can't find it in the course library, push it back to the list 
				if(judge == false)
  			  	course_list.push_back(C);
          //cout << course_list[0].depart_code << course_list[0].course_num<< endl;

  	break;
  }

  	case 'A': // assign a book to a class 
    {
  				// first find that book in the book library 
          Section sec;
  				Book assigned_book;
  				bool judge1 = false;
  				for (int i = 0; i < book_list.size(); i++) 
  				{ 
  					if(command.substr(2,13) == book_list[i].ISBN)
  					{
  						assigned_book = book_list[i];
  						judge1 = true;
  					}
  					if(judge1)
  						break;
				}
				if(judge1 == false)
					cout << "Sorry, can't find this book in your library!!" << endl;

				// next find the course in the course library
				bool judge2 = false;
  				for (int i = 0; i < course_list.size(); i++) 
  				{ 
  					if(command.substr(16,4) == course_list[i].depart_code && command.substr(21,3) == course_list[i].course_num)
  					{
  						sec.section_num = command.substr(25,3);
              pair<Book,char> book_pair;
              book_pair.first = assigned_book;
              book_pair.second = command[29];
              
  						bool judge3 = false;
  						for (int j = 0; j < ((course_list[i]).section_list).size(); j++) 
  						{
                // if the section already existed 
  							if((course_list[i]).section_list[j].section_num == command.substr(25,3))
  								{
                    // if this book have already existed in this book list, we just need to update it.
                    bool judge4 = false;
                    for(int k =0; k < (course_list[i]).section_list[j].books.size(); k++)
                    {
                      if(assigned_book.ISBN == (course_list[i]).section_list[j].books[k].first.ISBN)
                      {
                        (course_list[i].section_list[j].books[k]).second = command[29];
                        judge4 = true;
                      }
                      if(judge4)
                        break;
                    }
                    if(judge4);
                    else // didn't find this book in the book list, push it back to this section's book list 
                      {
                        (course_list[i]).section_list[j].books.push_back(book_pair);
                      }
    							  judge3 = true;
                  }
  							if(judge3)
  							break; 
  						}
  					if(judge3 == false) // if section is not existed, push it into this course
  					{
                sec.books.push_back(book_pair);
                ((course_list[i]).section_list).push_back(sec);
            }

  						judge2 = true;

  					}
  					if(judge2)
  						break;
				}
        // can't find the course, output error words 
				if(judge2 == false)
  			  	cout << "Sorry, can't find this course in your library!!" << endl;

	break;
}

  	case 'G':
    {
  			if(command[1] == 'C') //Print the books required and optional for all sections of a given course
  			{
  				bool judge = false;
          // iterate the course list to find this course's all sections
  				for (int i = 0; i < course_list.size(); i++) 
  				{ 
  					if(command.substr(3,4) == course_list[i].depart_code && command.substr(8,3) == course_list[i].course_num)
  					{
  						cout << " The books required and optional for all sections of a given course ";
  						cout << course_list[i].depart_code << " " << course_list[i].course_num << " includes:" << endl;

  						for (int j = 0; j < ((course_list[i]).section_list).size(); j++) 
  						{
  							
  							cout << (course_list[i]).section_list[j].section_num << endl;
                for(int k = 0; k < (((course_list[i]).section_list[j]).books).size(); k++)
                { // print all informaions about a book 
                    cout << (((course_list[i]).section_list[j].books[k]).first).ISBN << " " << (((course_list[i]).section_list[j].books[k]).first).Title << " ";
                    cout << (((course_list[i]).section_list[j].books[k]).first).Author << " " << (((course_list[i]).section_list[j].books[k]).first).Edition << " " << (((course_list[i]).section_list[j].books[k]).first).Date << " " ;
                    if((((course_list[i]).section_list[j].books[k]).first).format_N != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_N << " " << (((course_list[i]).section_list[j].books[k]).first).format_N << " ";
                    if((((course_list[i]).section_list[j].books[k]).first).format_U != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_U << " " << (((course_list[i]).section_list[j].books[k]).first).format_U << " ";
                    if((((course_list[i]).section_list[j].books[k]).first).format_R != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_R << " " << (((course_list[i]).section_list[j].books[k]).first).format_R << " "; 
                    if((((course_list[i]).section_list[j].books[k]).first).format_E != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_E << " " << (((course_list[i]).section_list[j].books[k]).first).format_E << " ";
  						      if(((course_list[i]).section_list[j].books[k]).second == 'R')
                      cout << "Required" << endl;
                    else
                      cout << "Optional" << endl;
                }
  						}
  						judge = true;
  					}
				}
				if(judge == false)
					cout << " Sorry, this course is not existed!!" << endl;
  			}

  			else if(command[1] == 'S')  //Print the books required and optional for a given section of a course
  			{
  				bool judge = false;
          // iterate all course's section list to find the specific course section
  				for (int i = 0; i < course_list.size(); i++) 
  				{ 
  					if(command.substr(3,4) == course_list[i].depart_code && command.substr(8,3) == course_list[i].course_num)
  					{
  						for (int j = 0; j < ((course_list[i]).section_list).size(); j++) 
              {
  							if((course_list[i]).section_list[j].section_num == command.substr(12,3))
  							{
  								cout << " The books required and optional for a given section of a course ";
  								cout << course_list[i].depart_code << " " << course_list[i].course_num << " " << (course_list[i]).section_list[j].section_num << " includes: " << endl;
  								for(int k = 0; k < (((course_list[i]).section_list[j]).books).size(); k++)
                {
                  // print all information about a book 
                    cout << (((course_list[i]).section_list[j].books[k]).first).ISBN << " " << (((course_list[i]).section_list[j].books[k]).first).Title << " ";
                    cout << (((course_list[i]).section_list[j].books[k]).first).Author << " " << (((course_list[i]).section_list[j].books[k]).first).Edition << " " << (((course_list[i]).section_list[j].books[k]).first).Date << " " ;
                    if((((course_list[i]).section_list[j].books[k]).first).format_N != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_N << " " << (((course_list[i]).section_list[j].books[k]).first).format_N << " ";
                    if((((course_list[i]).section_list[j].books[k]).first).format_U != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_U << " " << (((course_list[i]).section_list[j].books[k]).first).format_U << " ";
                    if((((course_list[i]).section_list[j].books[k]).first).format_R != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_R << " " << (((course_list[i]).section_list[j].books[k]).first).format_R << " ";
                    if((((course_list[i]).section_list[j].books[k]).first).format_E != '\0')
                      cout << (((course_list[i]).section_list[j].books[k]).first).cost_E << " " << (((course_list[i]).section_list[j].books[k]).first).format_E << " ";
                    if(((course_list[i]).section_list[j].books[k]).second == 'R')
                      cout << "Required" << endl;
                    else
                      cout << "Optional" << endl;
                }
  								judge = true;
  						
  							}

  						}
  						
  					}
				}
				if(judge == false)
					cout << " Sorry, this course of this given section is not existed!!" << endl;
  			}

  			else   // Print all information known about a particular book
  			{
  				bool judge = false;
  				for (int i = 0; i < book_list.size(); i++) 
  				{ 
  					if(command.substr(3,13) == book_list[i].ISBN)
  					{
  						judge = true;
  						cout << "All information about book: " << book_list[i].ISBN << " are:" << endl;
  						cout << book_list[i].Title << " " << book_list[i].Author << " " << book_list[i].Edition << " " << book_list[i].Date << " " ;
              if(book_list[i].format_N != '\0')
                    cout << (book_list[i]).cost_N << " " << (book_list[i]).format_N << " ";
              if(book_list[i].format_U != '\0')
                    cout << (book_list[i]).cost_U << " " << (book_list[i]).format_U << " ";
              if(book_list[i].format_R != '\0')
                    cout << (book_list[i]).cost_R << " " << (book_list[i]).format_R << " ";
              if(book_list[i].format_E != '\0')
                    cout << (book_list[i]).cost_E << " " << (book_list[i]).format_E << " ";
              cout << endl;
  					}
  					if(judge)
  						break;
				}

  			}
  	break;
  }

  	case 'P':
  {
  	switch(command[1])
  	{

  	case 'B': //Print a list of all books that are defined
    {
  				cout << "Print a list of all books that are defined:" << endl;
  				for (int i = 0; i < book_list.size(); i++) 
  				{ 
  					cout << book_list[i].ISBN << " " << book_list[i].Title << " " << book_list[i].Author << " " << book_list[i].Edition << " " << book_list[i].Date << " ";
				    if(book_list[i].format_N != '\0')
                    cout << (book_list[i]).cost_N << " " << (book_list[i]).format_N << " ";
            if(book_list[i].format_U != '\0')
                    cout << (book_list[i]).cost_U << " " << (book_list[i]).format_U << " ";
            if(book_list[i].format_R != '\0')
                    cout << (book_list[i]).cost_R << " " << (book_list[i]).format_R << " ";
            if(book_list[i].format_E != '\0')
                    cout << (book_list[i]).cost_E << " " << (book_list[i]).format_E << " ";
            cout << endl;
          }
  	break;
  }

  	case 'C':  //Print a list of all courses that are defined
    {
  				cout << "Print a list of all courses that are defined:" << endl;
  				for (int i = 0; i < course_list.size(); i++ ) 
  				{ 
  					cout << course_list[i].depart_code << " " << course_list[i].course_num << " " << course_list[i].course_name << endl;
				  }
  	break;
  }

  	case 'Y':   //Print all books with known publication dates in the given month/year or later
    {
  				cout << "Print all books with known publication dates in the given month/ year " << command.substr(3,9) << " or later:" << endl;
          for (int i = 0; i < book_list.size(); i++) 
          { 
            if(book_list[i].Date != "")
            {
              int month_book = stoi((book_list[i].Date).substr(0,2));
              int year_book = stoi((book_list[i].Date).substr(3,4));
              int month = stoi(command.substr(3,2));
              int year = stoi(command.substr(6,4)); 
              // first compare year and then compare month 
              if(year_book < year)
              {
              	if(month_book < month);
              	else
              	{
	              	cout << book_list[i].ISBN << " " << book_list[i].Title << " " << book_list[i].Author << " " << book_list[i].Edition << " " << book_list[i].Date << " ";
	                if(book_list[i].format_N != '\0')
	                    cout << (book_list[i]).cost_N << " " << (book_list[i]).format_N << " ";
	                if(book_list[i].format_U != '\0')
	                    cout << (book_list[i]).cost_U << " " << (book_list[i]).format_U << " ";
	                if(book_list[i].format_R != '\0')
	                    cout << (book_list[i]).cost_R << " " << (book_list[i]).format_R << " ";
	                if(book_list[i].format_E != '\0')
	                    cout << (book_list[i]).cost_E << " " << (book_list[i]).format_E << " ";
	                cout << endl;
              	}
              }
              else
               {
	                cout << book_list[i].ISBN << " " << book_list[i].Title << " " << book_list[i].Author << " " << book_list[i].Edition << " " << book_list[i].Date << " ";
	                if(book_list[i].format_N != '\0')
	                    cout << (book_list[i]).cost_N << " " << (book_list[i]).format_N << " ";
	                if(book_list[i].format_U != '\0')
	                    cout << (book_list[i]).cost_U << " " << (book_list[i]).format_U << " ";
	                if(book_list[i].format_R != '\0')
	                    cout << (book_list[i]).cost_R << " " << (book_list[i]).format_R << " ";
	                if(book_list[i].format_E != '\0')
	                    cout << (book_list[i]).cost_E << " " << (book_list[i]).format_E << " ";
	                cout << endl;
              }
            }
          }
  	break;
  }

  	case 'D':  //Print the list of all books used in a department, given by department code. Do not list by section

    {
          cout << " Print the list of all books used in a department, given by department code " << command.substr(3,6) << " :" << endl;
          vector<Book> b_list; // used to  put the book used in a department, avoid repetition  
          for (int i = 0; i < course_list.size(); i++) 
          { 
            if(course_list[i].depart_code == command.substr(3,4))
            {
              for (int j = 0; j < ((course_list[i]).section_list).size(); j++) 
                  for(int k = 0; k < (((course_list[i]).section_list[j]).books).size(); k++)
                {
                	bool find_it = false;
                    for(int a =0; a < b_list.size(); a++)
                    {
                    	if(b_list[a].ISBN == ((course_list[i]).section_list[j]).books[k].first.ISBN)
                    		find_it = true;
                    }
                    if(find_it); // if already existed in the b_list, don't do anything, otherwise push it back to b_list 
                    else
                    	b_list.push_back(((course_list[i]).section_list[j]).books[k].first);
                }

            }
          }
          // print all information about books 
            	for(int b =0; b < b_list.size(); b++)
            	{
            		cout << b_list[b].ISBN << " " << b_list[b].Title << " ";
                    cout << b_list[b].Author << " " << b_list[b].Edition << " " << b_list[b].Date << " ";
                    if(b_list[b].format_N != '\0')
                      cout << b_list[b].cost_N << " " << b_list[b].format_N << " ";
                    if(b_list[b].format_U != '\0')
                      cout << b_list[b].cost_U << " " << b_list[b].format_U << " ";
                    if(b_list[b].format_R != '\0')
                      cout << b_list[b].cost_R << " " << b_list[b].format_R << " ";
                    if(b_list[b].format_E != '\0')
                      cout << b_list[b].cost_E << " " << b_list[b].format_E << " ";
                  cout << endl;
              }
          
  	break;
  }


  	case 'M':   //Print the AVERAGE minimum and maximum costs of all books in a department.
    {
          cout << " Print the AVERAGE minimum and maximum costs of all books in a department " << command.substr(3,6) << " :" << endl;
          //calculate the caheapest and most exoensive book costs
          float minimum_value = 0;
          float maximum_value = 0;

          // calculate the cheapest version of required book for a section
          int section_size_min = 0;  // count the size of section which has minimum cost 
          for (int i = 0; i < course_list.size(); i++) 
          { 
            if(course_list[i].depart_code == command.substr(3,4))
            {
              for (int j = 0; j < ((course_list[i]).section_list).size(); j++)
              {
                float section_minimum = 0;
                  for(int k = 0; k < (((course_list[i]).section_list[j]).books).size(); k++)
              
                {
                  float cheapest_cost = 10000;
                  if(((course_list[i]).section_list[j].books[k]).second == 'R')   // check if this is book is required 
                  {
                    // compare different format's cost of a book 
                    if((((course_list[i]).section_list[j].books[k]).first).format_N != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_N.c_str(), &pEnd);
                          if(cost < cheapest_cost)
                            cheapest_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_U != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_U.c_str(), &pEnd);
                          if(cost < cheapest_cost)
                            cheapest_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_R != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_R.c_str(), &pEnd);
                          if(cost < cheapest_cost)
                            cheapest_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_E != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_E.c_str(), &pEnd);
                          if(cost < cheapest_cost)
                            cheapest_cost = cost;
                        }
                       section_minimum = section_minimum + cheapest_cost;
                  }
                }
                if(section_minimum != 0)
                    section_size_min++;
                  minimum_value = minimum_value + section_minimum;
              } 
            } 
          }

          float minimum_average = minimum_value / section_size_min; // calcultae the minimum_average 
  

          // calculate the most expensive version of required and optional books in a course
          int section_size_max = 0; // count the size of section which has miaximum cost 
          for (int i = 0; i < course_list.size(); i++) 
          { 
            if(course_list[i].depart_code == command.substr(3,4))
            {
              for (int j = 0; j < ((course_list[i]).section_list).size(); j++)
              {
                float section_maximum = 0;
                  for(int k = 0; k < (((course_list[i]).section_list[j]).books).size(); k++)
              
                {
                  float expensive_cost = 0;
                  // compare different format's cost of a book 
                    if((((course_list[i]).section_list[j].books[k]).first).format_N != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_N.c_str(), &pEnd);
                          if(cost > expensive_cost)
                            expensive_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_U != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_U.c_str(), &pEnd);
                          if(cost > expensive_cost)
                            expensive_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_R != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_R.c_str(), &pEnd);
                          if(cost > expensive_cost)
                            expensive_cost = cost;
                        }
                    if((((course_list[i]).section_list[j].books[k]).first).format_E != '\0')
                        {
                          char* pEnd;
                          float cost = strtof((((course_list[i]).section_list[j].books[k]).first).cost_E.c_str(), &pEnd);
                          if(cost > expensive_cost)
                            expensive_cost = cost;
                        }
                        section_maximum = section_maximum + expensive_cost;
               }
                  if(section_maximum != 0)
                    section_size_max++;
                   maximum_value = maximum_value + section_maximum;
              } 
            }
          }

          float maximum_average = maximum_value / section_size_max; // calcultae the minimum_average 


          cout << "The AVERAGE minimum costs of all books(required) in this department is: " << minimum_average << endl;
          cout << "The AVERAGE maximum costs of all books(required and optional) in this department is: " << maximum_average << endl;

  	break;
  }
}

  	break;
  }
}
if(jump_judge)
break;
}

	return 0;
}
