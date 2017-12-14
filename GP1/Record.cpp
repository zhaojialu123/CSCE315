#include "Record.h"
#include <iostream>
using namespace std;

Record::Record(int size)
{
    rec = vector<string>(size);
}

int Record::get_size()
{
    return rec.size();
}

void Record::set_entry(int i, string val)
{
	if(i < rec.size())
    	rec[i] = val;
	else
		rec.push_back(val);
}

string Record::get_entry(int i)
{
    return rec[i];
}

