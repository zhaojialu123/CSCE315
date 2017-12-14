#include <iostream>
#include "Database.h"
using namespace std;

/*
These are the functions for the Database class minus the Query function, which has its own file as
it's a pretty large function.

The "first" and "second" refer to the map's key and data elements.
"first" is the key string and "second" is the Table pointer.
*/
Database::Database() {}

void Database::add_Table(Table* tab, string name) {
  database.insert(pair<string,Table*>(name, tab));
}

void Database::drop_Table(string name)
{
  map<string, Table*>::iterator it = database.find(name);
  if (it != database.end())
    database.erase (it);
  else
    cout << "Error finding table: " << name << endl;
}

vector<string> Database::list_Tables()
{
  vector<string> names;
  map<string,Table*>::iterator it = database.begin();
  while (it != database.end()) {
    names.push_back(it->first);
    ++it;
  }
  return names;
}

vector<Table*> Database::get_Tables()
{
  vector<Table*> tables;
  map<string, Table*>::iterator it = database.begin();
  while (it != database.end()) {
    tables.push_back(it->second);
    ++it;
  }
  return tables;
}
