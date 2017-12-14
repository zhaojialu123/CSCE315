#ifndef DATABASE_H
#define DATABASE_H
#include <string>
#include <unordered_map>
#include <list>
#include "Table.h"
#include "TokenTree.h"
#include <vector>
#include <queue>

using namespace std;

typedef unordered_map<string, Table> tableMap;



class Database {
	private:
	tableMap tables;
	vector<Token> tokenize(string s);	
	vector<string> convert_string(string a);
	queue<Token> toPostfix(vector<Token> tokenVec);
	vector<bool> evalTree(TokenTree* tree, Table* currTable);

	public:

	Database();
	void addTable(Table& t, string name);
	void dropTable(string name);
	list<string> listTables();
	list<Table*> getTables();
	Table* query(string select, string from, string where);
};

#endif