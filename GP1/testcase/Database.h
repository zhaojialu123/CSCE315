#ifndef DATABASE_H
#define DATABASE_H
#include <string>
#include <unordered_map>
#include <list>
#include "Table.h"

using namespace std;

typedef unordered_map<string, Table> tableMap;

class Database {
	tableMap tables;
	
	public:
	Database();
	void addTable(Table& t, string name);
	void dropTable(string name);
	list<string> listTables();
	list<Table*> getTables();
	Table* query(string select, string from, string where);
};

#endif