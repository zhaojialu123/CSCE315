#include <sstream>
#include <iostream>
#include <map>
#include <vector>
#include <string>
#include <cstring>
#include "Database.h"
#include "Table.h"
#include "Record.h"
#include "Parser.h"
#include "Evaluator.h"
#include <regex>

using namespace std;

/*
	Fucntions to tokenize input strings and get them ready for the Evaluator

	tokenize_SELECT() takes in the SELECT string from Query(). The input is put into a stringstream for parsing.
	Getline() reads until it finds a comma. The resulting string is stored in a token vector.
	Leading whitespace is then removed from each string in the vector
*/

vector<string> tokenize_SELECT(string in){
	istringstream ss(in);
	string token;
	vector<string> tokens;

	while(getline(ss, token, ',')) {
	    tokens.push_back(token);	//get tokens
	}

	for (int i = 0; i < tokens.size(); ++i) {	//remove leading whitespace from strings.
		string temp = tokens[i];
		temp = regex_replace(temp, regex("^ +"), "");
		tokens[i] = temp;
	}

	return tokens;
}

string replace_all(string where) {
	/*
		Regex_replace replaces all instances of one string with the desired string.
		This is done to help the evaluator later on.
	*/
	string temp1 = regex_replace(where, regex("AND"), "&");
	string temp2 = regex_replace(temp1, regex("OR"), "|");
	string temp3 = regex_replace(temp2, regex("NOT"), "!");
	string temp4 = regex_replace(temp3, regex("<>"), "#");
	string temp5 = regex_replace(temp4, regex(">="), "@");
	string temp6 = regex_replace(temp5, regex("<="), "%");

	return temp6;
}

/*
The following functions are the tokenizer and tokenizer helper functions
*/

bool isOperator(char ch) {

  switch(ch) {
    case '!': case '@': case '#': case '%': case '&': case '|': case '<': case '>': case '=':
      return true;
      break;
    default:
      return false;
    }
}

bool isParen(char ch) {
  if (ch == '(' || ch == ')')
    return true;
  else
    return false;
}

/*

*/

vector<string> tokenize_WHERE(string where) {
  string input = where;
  vector<string> tokens;
  string holder = "";

  int i = 0;
  while (i != input.length()) {

    if (isParen(input[i])) {
      string s;
      s += input[i];
      tokens.push_back(s);
      ++i;
    }

	//If the character matches an operator, it looks in front and behind the operator to make sure it isn't accidentally
	//a part of another string. Operators must have spaces before and after, or an open parenthesis after
    if (isOperator(input[i]))
	{
      if (i < input.length())
	  { //Where shouldn't ever start with an operator or end with an operator
        if ((input[i-1] == ' ' && input[i+1] == ' ') || (input[i+1] == '(')) {

          string s;	//convering character to string. There are other ways to do this. I chose this one.
          s += input[i];
          tokens.push_back(s);
          ++i;
        }
      }
    }

    if (input[i] == ' ') {
      ++i; //ignore spaces
    }

	//while i isn't out of bounds and isn't an operator or parenthesis
    while (i != input.length() && !(isOperator(input[i]) || isParen(input[i]) )) {
      holder += input[i]; //Holder builds a string operand such as attribute or data in a field. This could be done in other ways.
      ++i;
    }
    //delete space on end if present
    if (holder.length() > 0) {
      if (holder[holder.length() - 1] == ' ') {
        string temp = holder.substr(0, holder.length()-1);
        holder = temp;
      }
    }

    if (holder.length() > 0) { //means that the holder is an operand. The operand needs to be put in the token vector
      tokens.push_back(holder);
      holder = "";
    }
  }
  return tokens;
}

/*
Below is the Query function. SELECT is a string including the attributes for the new Table. FROM is the table the data comes from.
WHERE is a conditional statement on how to sort the data in the table.

*/

Table* Database::Query(string SELECT, string FROM, string WHERE)
{
	vector<string> attributes; //These are the Table attributes from the SELECT string.

  	//Tokenize the SELECT statement to get names of attributes.
	attributes = tokenize_SELECT(SELECT);

  	//Make sure the table is in the database
  	map<string,Table*>::iterator from_db = database.find(FROM);
  	if (from_db == database.end() ) {
		return NULL; // if it returns, then the database is an empty database;
	}

  /*
  Below is the code for when the user wants to query a record without a WHERE statement.
  It essentially returns all records of the desired attributes;
  */

  	if (SELECT == "*" && WHERE == "") {
		//If they want all attributes with no specific conditions, it's the original table.
    	return from_db ->second;
	}
	else if(WHERE == "" && SELECT != "*") {
		//If there are no specific conditions, then just return the desired columns from the Table
	    Table* desired_tab = from_db -> second;
	    vector<string> d_t_attr = desired_tab -> get_attributes();	// "desired_table_attribute"
	    vector<int> match_atr_elem; //if SELECT attribute matches table attribute, store the element number, "match_attribute_element"

	    for(int i = 0; i < attributes.size(); i++)
	    {
			vector<string>:: iterator it;
		    it = find(d_t_attr.begin(),d_t_attr.end(), attributes[i]); //compare elements in SELECT to table's attributes
		    if(it != (d_t_attr.end()))
		    {
				//find() returns the iterator. distance() tells you which element of the vector the iterator is. It's an int, but auto to be safe.
				auto element = distance(d_t_attr.begin(), it);
		    	match_atr_elem.push_back(element);
		    }
	    }

	    Table* new_table = new Table(& attributes);
	    for(int j = 0; j < desired_tab -> get_size(); j++) //iterate down through records
		{
	    	Record *r = new Record(attributes.size());

	    	for (int k = 0; k < match_atr_elem.size(); ++k) {
				 //iterate across records from Table and returns entry from each desired column.
	        	r -> set_entry(k, (desired_tab -> get_record(j)) -> get_entry(match_atr_elem[k]));
	    	}

	    new_table-> insert_record(r);
		}

    return new_table;
	}

	//replace longer non-single character conditions to single characters so they can be delimiters
  	WHERE = replace_all(WHERE);


  vector<string> tokenized_WHERE;   //tests above make sure vector isn't empty
  tokenized_WHERE = tokenize_WHERE(WHERE); //This is the vector that ToPostfix will use *******

  Parser test(tokenized_WHERE);

  Table* tab = from_db->second;
  Evaluator test2(test,tab);

  string vals = test2.getValue(); //This returns a string representing a bit-vector. "1001" means use the first and last records of the Table.
  Table *new_t = new Table;

	// select everything but have specific conditions.
	if (SELECT == "*") {
	  for(int i = 0; i < (tab -> get_attributes()).size(); i++)
	  {
		 //add the original table's attributes to the new table.
	    new_t -> add_attribute((tab -> get_attributes())[i]);
	  }
	  for (int i = 0; i< tab -> get_size(); i++) {
	       if(vals[i] == '0') {	//Current record isn't needed.
	            continue;
	       }
			new_t -> insert_record(tab -> get_record(i));
		}
	}
	else {	//select specific attributes
		for (auto& x: attributes) {
			new_t -> add_attribute(x);
		}

		//From_db is a map iterator. Second is the Table pointer stored in the map.
		//Some of this code is the same as up above.
		Table* desired_tab = from_db -> second;
	    vector<string> d_t_attr = desired_tab -> get_attributes();
	    vector<int> match_atr_elem;

	    for(int i = 0; i < attributes.size(); i++)
	    {
			vector<string>:: iterator it;
		    it = find(d_t_attr.begin(),d_t_attr.end(), attributes[i]); //compare elements in SELECT to table's attributes
		    if(it != (d_t_attr.end()))
		    {
				auto element = distance(d_t_attr.begin(), it);
		    	match_atr_elem.push_back(element);
		    }
	    }

		for (int i = 0; i< tab -> get_size(); i++) {

  	       if(vals[i] == '0') {
  	            continue;
  	        }
	    	Record *r = new Record(attributes.size());

	    	for (int k = 0; k < match_atr_elem.size(); ++k) { //iterate across record
	        	r -> set_entry(k, (desired_tab -> get_record(i)) -> get_entry(match_atr_elem[k]));
	    	}
			new_t -> insert_record(r);
		}
	}

  return new_t;
}
