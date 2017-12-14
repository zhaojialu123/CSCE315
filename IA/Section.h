// Section.h
#include <string>
#include <vector>
using namespace std;

// Define sections class which belong to a course 
 struct Section{
  string section_num;
  vector<pair<Book, char>> books; // all books in this section, with the required or not  

};