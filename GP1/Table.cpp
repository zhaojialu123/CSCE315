//Table.cpp
//Jialu Zhao
#include "Table.h"
#include <vector>
#include <algorithm>

// default constructor, build an empty Table
Table::Table()
{
	name = "";
	attributes = new vector<string>(0);
	vector<Record*> empty_r;
	records = empty_r ;
	key = "";

}

Table::Table(vector<string>* input)
{
	name = "";
	attributes = input;
	vector<Record*> empty_r;
	records = empty_r ;
	key = "";
}

void Table::add_attribute(string atb)
{
	attributes -> push_back(atb);
	for(int i = 0; i < records.size(); i++)
		records[i] -> set_entry(attributes -> size(), "");
	//cout << "here" << endl;

}

void Table::remove_attribute(string atb)
{
	int index = 0;
	for(; index < attributes -> size(); index++)
	{
		if((*attributes)[index] == atb)
			break;
	}
	// erase elements in attributes
	attributes -> erase(attributes -> begin()+index);

	// erase elements in every records
	for(int i = 0; i < records.size(); i++)
		(records[i] -> rec).erase((records[i] -> rec).begin() + index);


}

void Table::insert_record(Record* red)
{
	records.push_back(red);

}

vector<string> Table::get_attributes()
{
	return *attributes;
}

int Table::get_size()
{
	return records.size();
}

Record* Table::get_record(int i)
{
	return records[i];
}

void Table::set_key(string atb)
{
	key = atb;
}

Table* Table::cross_join(Table* a, Table* b)
{
	Table* cross_t = new Table;

	// attributes
	// add a's attributes to cross_t
	for(int i = 0; i < (a -> get_attributes()).size(); i++) {
		vector<string> test = a->get_attributes();
		cross_t -> add_attribute(test[i]);
	}
	// add b's attributes to cross_t
	for(int i = 0; i < (b -> get_attributes()).size(); i++)
		cross_t -> add_attribute((b -> get_attributes())[i]);

	// records
	for(int i = 0; i < a -> get_size(); i++)
	{
		for(int j = 0; j < b -> get_size(); j++)
		{
			Record *r = new Record((a -> get_attributes()).size() + (b -> get_attributes()).size());
			//Record r((a -> get_attributes()).size() + (b -> get_attributes()).size());
			for(int k = 0; k < (a -> get_attributes()).size(); k++)
			{
				//cout << (a -> get_record(i))-> get_entry(k) << endl;
				r -> set_entry(k,(a -> get_record(i))-> get_entry(k));
			}

			for(int k = 0; k < (b -> get_attributes()).size(); k++)
			{
				r -> set_entry((a -> get_attributes()).size() + k,(b -> get_record(j))-> get_entry(k));
			}
			cross_t -> insert_record(r);
		}
	}
	return cross_t;

}

Table* Table::natural_join(Table* a, Table* b)
{
	string key_2 = b->key;
//	cout << key_2 << endl;
	// First exception
	if(b -> key == "")
		throw invalid_argument("The second table doesn't set key!!");
	//cerr << "passed empty key check" << endl;
	// Second exception
	bool find_key = false;
	for(int i = 0;i < (a -> get_attributes()).size(); i++)
	{
		if((a -> get_attributes())[i] == b -> key)
			find_key = true;
	}
	if(!find_key)
		throw invalid_argument("The key in the second table doesn't match anything in the first table's attributes!!");

	// create natural_join table
	// attributes
	// add a's attributes to natural_t

		// get the key index in table a
	int index_a;
	for(int i =0; i < get_attributes().size(); i++)
	{
		if(a -> get_attributes()[i] == key_2)
		{
			index_a = i;
			break;
		}
	}
	// get the key index in table b
	int index_b;
	for(int i =0; i < get_attributes().size(); i++)
	{
		if(b -> get_attributes()[i] == key_2)
		{
			index_b = i;
			break;
		}
	}

		vector<string>* new_attr = new vector<string>;
		vector<int> t2_unique; // unique attributes in table 2
		// put all the attributes in table a to the new table

		for(int i = 0; i < (a -> get_attributes()).size(); i++) //
		{
			//cout << "a attribute: " << (a -> get_attributes())[i] << endl;
			new_attr -> push_back((a -> get_attributes())[i]);
		}
		//cout << "here" << endl;
		for(int i = 0; i < (b -> get_attributes()).size(); i++)
		{
			vector<string>:: iterator it;
			it = find(new_attr -> begin(),new_attr -> end(),(b -> get_attributes())[i]);
			if(it == (new_attr -> end()))
			{
				//cout << (b -> get_attributes())[i] << endl;
				new_attr -> push_back((b -> get_attributes())[i]);
				//auto element = distance((b->get_attributes()).begin(), it);
				//cout << "element: " << i << endl;
				t2_unique.push_back(i);
			}
		}

		//cout << "before new table" << endl;
		Table* natural_t = new Table(new_attr);
		//cout << "after making new table" << endl;
		//cout << natural_t -> get_attributes()[0] << endl;
		// records
		for(int i = 0; i < a -> get_size(); i++)
		{
			for(int j = 0; j < b -> get_size(); j++)
				{
				//	cout << "before compare: i = " << i << ", j = " << j << endl;
					if((a -> get_record(i)) -> get_entry(index_a) == (b -> get_record(j)) -> get_entry(index_b))
					{
					//	cout << new_attr -> size() << endl;
						Record *r = new Record(new_attr -> size());
						for(int k = 0; k < (a -> get_attributes()).size(); k++)
							r -> set_entry(k, (a -> get_record(i)) -> get_entry(k));
						int count = 0;
						for(auto x : t2_unique)
						{
							r -> set_entry(count + (a -> get_attributes()).size(),(b -> get_record(j)) -> get_entry(x));
							count++;
						}
						natural_t -> insert_record(r);
					}
				}
			}

//	cout << (natural_t -> get_attributes()).size() << endl;
	return natural_t;

}


int Table::Count(string atb)
{
	int index = 0;
	for(int i =0; i < get_attributes().size(); i++)
	{
		if(get_attributes()[i] == atb)
		{
			index = i;
			break;
		}
	}
	vector<string> rec;
	for(int i = 0; i < get_size(); i++)
	{
		if((records[i])-> get_entry(index) != "")
		{
			rec.push_back((records[i])-> get_entry(index));
		}
	}
	return rec.size();

}

string Table::Min(string atb)
{
	int index = 0;
	for(int i =0; i < get_attributes().size(); i++)
	{
		if(get_attributes()[i] == atb)
		{
			index = i;
			break;
		}
	}
	vector<string> rec;
	for(int i = 0; i < get_size(); i++)
		rec.push_back((records[i])-> get_entry(index));
	auto min = *min_element(rec.begin(),rec.end());
	return min;

}

string Table::Max(string atb)
{
	int index = 0;
	for(int i =0; i < get_attributes().size(); i++)
	{
		if(get_attributes()[i] == atb)
		{
			index = i;
			break;
		}
	}
	vector<string> rec;
	for(int i = 0; i < get_size(); i++)
		rec.push_back((records[i])-> get_entry(index));
	auto max = *max_element(rec.begin(),rec.end());
	return max;

}
