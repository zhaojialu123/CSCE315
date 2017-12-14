// Course.h
#include <string>
#include <vector>
using namespace std;

// Define a course class
struct Course {
	string depart_code;
	string course_num;
	string course_name;
	vector<Section> section_list; // same course includes different sections 
 };