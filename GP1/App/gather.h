#include "Database.h"
#include "json.hpp"
#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;
using json = nlohmann::json;

void gather(vector<Record*> &users, vector<Record*> &businesses, vector<Record*> &reviews) {

	ifstream UF; //user file
	ifstream BF; //business file
	ifstream RF; //review file;


	UF.open("user_subset.json");			//open file and test
	if (!UF) {
		cerr << "Unable to open file";
		exit(1);
	}

	string line;
	while (getline(UF, line)) {
		auto j = json::parse(line); 		//converts line into JSON data type
		string ID = j["user_id"]; 			//gets the string/field/datatype of whatever is after the string in brackets
		string name = j["name"];

		int int_rc = j["review_count"];		//Returns a number...
		string str_rc = to_string(int_rc);	//but needs to be a string

		vector<string> temp = j["friends"];
		string num_friends = to_string(temp.size());

		int int_useful_r = j["useful"];
		string str_useful_r = to_string(int_useful_r);

		double d_avg_str = j["average_stars"];
		stringstream ss;
		ss << fixed << setprecision(2) << d_avg_str; //to set number of decimal places
		string str_avg_str = ss.str();
		ss.clear();
		ss.str("");

		Record* temp_rec = new Record(6);
		temp_rec -> set(0, ID);
		temp_rec -> set(1, name);
		temp_rec -> set(2, str_rc);
		temp_rec -> set(3, num_friends);
		temp_rec -> set(4, str_useful_r);
		temp_rec -> set(5, str_avg_str);

		users.push_back(temp_rec);
	}

	UF.close();

	BF.open("business_subset.json");
	if (!BF) {
		cerr << "Unable to open file";
		exit(1);
	}

	while (getline(BF, line)) {
		auto j = json::parse(line);
		string ID = j["business_id"];
		string name = j["name"];
		string city = j["city"];
		string state = j["state"];

		double d_stars = j["stars"];
		stringstream ss;
		ss << fixed << setprecision(2) << d_stars; //to set number of decimal places 
		string str_stars = ss.str();
		ss.clear();
		ss.str("");

		int int_review_count = j["review_count"];
		string str_review_count = to_string(int_review_count);

		Record* temp_rec = new Record(6);
		temp_rec -> set(0, ID);
		temp_rec -> set(1, name);
		temp_rec -> set(2, city);
		temp_rec -> set(3, state);
		temp_rec -> set(4, str_stars);
		temp_rec -> set(5, str_review_count);

		businesses.push_back(temp_rec);
	}

	BF.close();


	RF.open("review_subset.json");
	if (!RF) {
		cerr << "Unable to open file";
		exit(1);
	}

	while (getline(RF, line)) {
		auto j = json::parse(line);
		string review_id = j["review_id"];
		string user_id = j["user_id"];
		string business_id = j["business_id"];

		double d_stars = j["stars"];
		string str_stars = to_string(d_stars);

		string date = j["date"];

		int int_useful = j["useful"];
		string str_useful = to_string(int_useful);

		Record* temp_rec = new Record(6);
		temp_rec -> set(0, review_id);
		temp_rec -> set(1, user_id);
		temp_rec -> set(2, business_id);
		temp_rec -> set(3, str_stars);
		temp_rec -> set(4, date);
		temp_rec -> set(5, str_useful);

		reviews.push_back(temp_rec);
	}

	RF.close();

}
