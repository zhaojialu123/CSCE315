#include <string>
#include <unordered_map>
#include <list>
#include "Record.h"

#ifndef Table_h
#define Table_h

// typedef unordered_map<string, Table> tableMap;
// Why is this out of the class definition?
struct TokenTree;

class Table {
	
	std::string key;
	
	friend class Database;

	bool isUniqueKeyVal(std::string s);

	int getAttrIndex(std::string attr);

	public:

 	// Vector of Records
	std::vector<Record> record_list;
	// Vector of Attributes
	
	std::vector<std::string> attribute_list;
	// Record size determined by number of attributes in attribute list
	int record_size;

	// Table Constructor
	Table();
	// Overloaded Table Constructor
	Table(std::vector<std::string> names);
	// Add record entry
	void addEntry(std::string name);
	// Delete Record entry
	void deleteEntry(std::string name);
	// Inserts a Record by reference
	void insertEntry(Record& r);
	// Finds a record
	std::vector<Record>::iterator findRecord(std::string key);

	// Sets a key
	void setKey(std::string name);
	// Renames a record attribute
	void renameAttribute(std::string original,std::string newAtrribute);
	// Specifies a Key
	void specifyKey();
	// Performs a cross Join between two tables and returns a Table
	Table crossJoin();
	// Get Functions

	// returns the Record_list
	std::vector<Record> getRecord_List();
	// Gets attributes from the record
	std::vector<std::string> getAttributes();
	// Returns the size of the table
	int getSize();
	// Performs a cross join with two tables
	Table crossJoin_2(Table one, Table two);
	// Performs a natural join with two tables
	Table naturalJoin(Table one, Table two);
	// Counts number of entries
	int countEntries(std::string name);
	// Counts Min
	std::string countMin(std::string name);
	// Counts Max
	std::string countMax(std::string name);
	// get iterator to beginning of table
	std::vector<Record>::iterator begin();
	// get iterator to end of table
	std::vector<Record>::iterator end();
 	

};

#endif