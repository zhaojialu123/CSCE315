#ifndef RECORD_H
#define RECORD_H

#include <string>
#include <vector>
#include <cstdarg>

class Record
{

private:

	std::vector<std::string> entries;

public:
	// record constructor
	Record(int size);
	// record copy constructor
	Record(const Record& r);
	// return the size of record (number of entries)
	int size() const;
	// access an entry by reference to allow for modification
	std::string& operator[](int index);
	// access an entry for reading only
	std::string get(int index) const;
	// set the item at index to a value
	void set(int index, std::string value);
	// delete a specific index
	void deleteEntry(int index);

};

#endif