//This program allows a user to explore data from user, business, and review subset files acquired from a Yelp JSON file.

#include <iostream>
#include <regex>
#include "gather.h"
#include "Database.h"
#include "Record.h"
#include "Table.h"
using namespace std;


/*
 Regex pattern to recognize user input for SQL query format. The user can enter either of the following to "SELECT" an attribute: SELECT or select. The user can enter either of the following to specify "FROM" from a specific table: FROM or FROM, and either of the following to specify the "WHERE" command: WHERE or where.
 */
regex query("(?:SELECT|select)\\s+(.*)\\s+(?:FROM|from)\\s+(\\S*)\\s*(?:\\s+(NATURAL|natural|CROSS|cross)\\s+(?:JOIN|join)\\s+(\\S*)\\s*)?(?:\\s+(?:WHERE|where)\\s+(.*)\\s*)");



/*
 Defining variables for the database program with appropriate return types
 */
Database db;
Table user_table;
Table business_table;
Table review_table;


/*
 The build_user_table function builds the user table with the following attributes: user_id, name, review_count, friends, useful, average_stars. The attributes are then stored in a vector of strings.
 */
void build_user_table(vector<Record*> user_record)
{
    //add attributes
    vector<string> attributes_user = {"user_id", "name", "review_count", "friends","useful", "average_stars"};
    user_table = Table(attributes_user);

    //set key an "user_id" for user_table
    user_table.setKey("user_id");

    //for loop to add each entry into the record vector
    for(int i = 0; i < user_record.size(); i++)
        user_table.insertEntry(*user_record[i]);
}


/*
 The build_business_table function builds the user table with the following attributes: business_id, name, city, state, stars, review_count. The attributes are stored in a vector of strings.
 */
void build_business_table(vector<Record*> business_record)
{
    //add attributes
    vector<string> attributes_business = {"business_id","name","city","state", "stars", "review_count"};
    business_table = Table(attributes_business);

    //set key as "business_id" for business table
    business_table.setKey("business_id");
    
    //for loop to add each entry into the business vector
    for(int i = 0; i < business_record.size(); i++)
        business_table.insertEntry(*business_record[i]);
}


/*
 The build_review_table function builds the review table with the following attributes: review_id, user_id, business_id, stars, date, useful. The attributes are stored in a vector of strings.
 */
void build_review_table(vector<Record*> review_record)
{

    //add attributes
    vector<string> attributes_review = {"review_id","user_id", "business_id","stars","date", "useful"};
    review_table = Table(attributes_review);
    
    //set key as "user_id" for review table
    review_table.setKey("user_id");
   
    //for loop to add each entry into the review vector
    for(int i = 0; i < review_record.size(); i++)
        review_table.insertEntry(*review_record[i]);
}


/*
 The build_Database() function builds database of the program.
 */
void build_Database()
{
    //declare three vector of record pointers
    vector<Record*> users;
    vector<Record*> businesses;
    vector<Record*> reviews;

    //use gather function to read from json file
    gather(users, businesses, reviews);

    //use build_table function to build three different tables
    build_user_table(users);
    build_business_table(businesses);
    build_review_table (reviews);

    // contruct database
    // add three tables to the database
    db.addTable(user_table,"user_table");
    db.addTable(business_table,"business_table");
    db.addTable(review_table,"review_table");
}


/*
 If there is no entry in Record, output "No entries/attributes found." If there are entries in Record, get each entry/attribute.
 */
void printRecord(Record* r) {
    if (r == NULL) {
        cout << "No entries/attributes in record" << endl;
    }
    else {
        for (int j = 0; j < r->size(); j++)
            cout << r->get(j) << " ";
        cout << "" << endl;
    }
}


/*
 If a table is empty or its size is 0, output "No data in table." If there are entries in the table, get each entry/attribute.
 */
void printTable(Table &t) {
    if (t.getSize() == 0){
        cout << "No data in table" << endl;
    }
    else {
        cout << endl;
        cout << "Table attributes in order: " << endl;
        for (auto x : t.getAttributes()) {
            cout << x << " ";
        }
        cout << endl;
        cout << endl;
        for (vector<Record>::iterator it = t.begin(); it != t.end(); ++it) {    //Use vector iterator to access attribute
            printRecord(&(*it));
        }
        cout << endl;
    }
}


/*
 Using toLower function to manage capitalization.
 */
string toLower(string s) {
    string my_string = s;
    for(unsigned int i = 0; i < my_string.length(); ++i) {
        my_string[i] = tolower(my_string[i]);
    }
    return my_string;
}


/*
 Function do_cross join with return type Table performs cross join for two tables.
*/
Table do_crossJoin(string table_name1, string table_name2)
{
    Table user_sub = *(db.query("name","user_table","average_stars > 4.5"));           //condition to restrict table size for users
    Table bus_sub = *(db.query("name", "business_table","state = OH"));                //condition to restrict table size for businesses
    Table review_sub = *(db.query("review_id","review_table","stars > 4"));            //condition to restrict table size for reviews
    Table cross_t;
    if(table_name1 == "user")
    {
        if(table_name2 == "user")
        {
            cross_t = user_table.crossJoin_2(user_sub, user_sub);                      //cross join two user tables
        }
        else if(table_name2 == "business")
        {
            cross_t = user_table.crossJoin_2(user_sub, bus_sub);                       //cross join user and business tables
        }
        else if(table_name2 == "review")
        {
        
            cross_t = user_table.crossJoin_2(user_sub, review_sub);
        }
    }
    else if(table_name1 == "business")
    {
        if(table_name2 == "user")
        {
            cross_t = business_table.crossJoin_2(bus_sub,user_sub);                 //cross join business and user tables
            
        }
        else if(table_name2 == "business")
        {
            cross_t = business_table.crossJoin_2(bus_sub,bus_sub);                  //cross join two business tables
            
        }
        else if(table_name2 == "review")
        {
            cross_t = business_table.crossJoin_2(bus_sub,review_sub);               //cross join business and review tables
            
        }
    }
    else if(table_name1 == "review")
    {
        if(table_name2 == "user")
        {
            cross_t = review_table.crossJoin_2(review_sub,user_sub);               //cross join review and user tables
            
        }
        else if(table_name2 == "business")
        {
            cross_t = review_table.crossJoin_2(review_sub,bus_sub);                //cross join review and business tables
            
        }
        else if(table_name2 == "review")
        {
            cross_t = review_table.crossJoin_2(review_sub,review_sub);            //cross join two review tables
        }
    }
    else
    {
        cout << "Please type the right name: " << endl;
    }
    return cross_t;
}


/*
 Function do_natrualJoin with return type Table performs natural join for two tables.
 */
Table do_naturalJoin(string table_name1,string table_name2)
{
    Table natural_t;
    if(table_name1 == "user" && table_name2 == "review")
    {
        review_table.setKey("user_id");
        natural_t = user_table.naturalJoin(user_table,review_table);            //natural join user and review table
        review_table.setKey("review_id");
    }
    else if(table_name1 == "business" && table_name2 == "review")
    {
        review_table.setKey("business_id");
        natural_t = business_table.naturalJoin(business_table,review_table);   //natural join business and review table
        review_table.setKey("review_id");
    }
    else if(table_name1 == "review")
    {
        if(table_name2 == "user")
        {
            natural_t = review_table.naturalJoin(review_table,user_table);     //natural join review and user table
        }
        else if(table_name2 == "business")
        {
            natural_t = review_table.naturalJoin(review_table,business_table); //natural join review and business table
        }
    }
    else
    {
        cout << "Please type the right name" << endl;
    }
    return natural_t;
}


/*
 Builds the query function in SQL format (SELECT, FROM, WHERE) by using regex to match user input.
 */
void do_query(string a) {
    string input;
    smatch args;                       //Match arguments. If size of the input string is greater than 0, use the string for query
    if (a.size() > 0)
        input = a;

    if (a.size() == 0) {               //If size of input string is 0, program will ask user to enter a string for query again.
        cout << "Please enter a query: ";
        cin.ignore(numeric_limits<streamsize>::max(), '\n');
        getline(cin, input);
    }



    if (regex_match(input, args, query)) {
        Table* q1 = db.query(args[1],args[2], args[5]);         //args[1]: SELECT, args[2]: FROM, args[5]: WHERE
        Table temp = *q1;
/*
        if (toLower(args[3]) == "natural") {                    //natural join two tables using the query and regex function
            if (args[4] == "review_table") {

                Table* q2 = db.query("*", args[4], "business_id <> 0");
                Table temp2 = *q2;

                if (args[4] == "review_table")
                    q2 -> setKey("user_id");

                temp = q1->naturalJoin(*q1, *q2);

            } else if (args[4] == "user_table") {
                Table* q2 = db.query("*", args[4], "user_id <> 0");
                Table temp = *q2;
                q2 -> setKey("user_id");

                temp = q1->naturalJoin(*q1, *q2);

            } else if (args[4] == "business_table") {
                Table* q2 = db.query("*", args[4], "business_id <> 0");
                Table temp = *q2;
                q2 -> setKey("business_id");

                temp = q1->naturalJoin(*q1, *q2);
            }

        } else if (toLower(args[3]) == "cross") {           //cross join two tables using the query and regex function
            if (args[4] == "review_table" || args[4] == "business_table") {
                temp = q1->crossJoin_2(*q1, *db.query("*", args[4], "stars>=0"));
            } else if (args[4] == "user_table") {
                temp = q1->crossJoin_2(*q1, *db.query("*", args[4], "name>=A"));
            }
        }
*/
printTable(temp);
    }

    else if (args.size() == 0) {                            //If no arguments matched, error is thrown.
        cout << "Invalid command" << endl;
        cout << "Enter input: ";
        return;
    }
}


/*
 The get_summary function gets information about users and reviews for a business, by using switch statements.
*/
void get_summary(char choice) {

    switch(choice) {
        case 'a': {                                                          //summary for users
            //attributes: "user_id", "name", "review_count", "friends","useful", "average_stars"
            vector<Record> rec = user_table.getRecord_List();                //get the list of all users and store it in a vector of records, rec

            vector<int> review_counts;
            string min_review_count = user_table.countMin("review_count");   //minimum review count
            string max_review_count = user_table.countMax("review_count");   //maximum review count
            for (auto x : rec) {
                string temp = x.get(2);                                     //get(2) returns the review count for the record
                review_counts.push_back(stoi(temp));
            }
            float rc_average = accumulate( review_counts.begin(), review_counts.end(), 0.0)/review_counts.size(); //stores all averages using the accumulate function

            vector<int> friends_counts;                                     //vector made to store friends count
            string min_friends = user_table.countMin("friends");            //minimum friend count
            string max_friends = user_table.countMax("friends");            //maximum friend count
            for (auto x : rec) {
                string temp = x.get(3);                                     //get(3) returns the friends count for the record
                review_counts.push_back(stoi(temp));
            }
            float fc_average = accumulate( review_counts.begin(), review_counts.end(), 0.0)/review_counts.size();

            vector<int> useful_counts;                                      //vector made to store useful counts
            string min_useful = user_table.countMin("useful");              //minimum useful count
            string max_useful = user_table.countMax("useful");              //maximum useful count
            for (auto x : rec) {
                string temp = x.get(4);                                     //get(4) returns the useful count for the record
                useful_counts.push_back(stoi(temp));
            }
            float useful_average = accumulate( useful_counts.begin(), useful_counts.end(), 0.0)/useful_counts.size();

            vector<int> avg_stars_counts;                                   //vector made to store average stars count
            string min_avg_stars = user_table.countMin("average_stars");    //minimum average stars
            string max_avg_stars = user_table.countMax("average_stars");    //maximum average stars
            for (auto x : rec) {
                string temp = x.get(5);                                     //get(5) returns the average stars count for the record
                avg_stars_counts.push_back(stoi(temp));
            }
            float stars_average = accumulate( avg_stars_counts.begin(), avg_stars_counts.end(), 0.0)/avg_stars_counts.size();

            /*
             Print statements
            */
            cout << endl;
            cout << "Summary of the User table: " << endl;
            cout << "Number of entries: " << user_table.getSize() << endl;
            cout << "Review Count - Min: " << min_review_count << " Max: " << max_review_count << " Average: " << rc_average << endl;
            cout << "friends Count - Min: " << min_friends << " Max: " << max_friends << " Average: " << fc_average << endl;
            cout << "Useful Rating - Min: " << min_useful << " Max: " << max_useful << " Average: " << useful_average << endl;
            cout << "Average Stars - Min: " << min_avg_stars << " Max: " << max_avg_stars << " Average: " << stars_average << endl;

            break;
        }

        case 'b': {                                                           //summary for businesses
            //attributes: "business_id","name","city","state", "stars", "review_count"
            vector<Record> rec = business_table.getRecord_List();

            vector<int> review_counts;
            string min_review_count = business_table.countMin("review_count");
            string max_review_count = business_table.countMax("review_count");
            for (auto x : rec) {
                string temp = x.get(5);                                       //get(5) returns review count for the record
                review_counts.push_back(stoi(temp));
            }
            float rc_average = accumulate( review_counts.begin(), review_counts.end(), 0.0)/review_counts.size();

            vector<int> stars_counts;
            string min_stars = business_table.countMin("stars");
            string max_stars = business_table.countMax("stars");
            for (auto x : rec) {
                string temp = x.get(4);                                       //get(4) returns the stars for the record
                stars_counts.push_back(stoi(temp));
            }
            float stars_average = accumulate( stars_counts.begin(), stars_counts.end(), 0.0)/stars_counts.size();

            vector<string> states;                                             //vector used to find unique state count
            for (auto x : rec) {
                states.push_back(x.get(3));                                    //get(3) returns the state for the record
            }
            sort(states.begin(), states.end());
            int unique_states = unique(states.begin(), states.end()) - states.begin();

            /*
             Print statements
            */
            cout << endl;
            cout << "Summary of the Business table: " << endl;
            cout << "Number of entries: " << business_table.getSize() << endl;
            cout << "Review Count - Min: " << min_review_count << " Max: " << max_review_count << " Average: " << rc_average << endl;
            cout << "Stars - Min: " << min_stars << " Max: " << max_stars << " Average: " << stars_average << endl;
            cout << "Number of states represented, including other countries: " << unique_states << endl;
            break;
        }

        case 'c': {                                                             //summary for reviews
            //attributes: "review_id","user_id", "business_id","stars","date", "useful"
            vector<Record> rec = review_table.getRecord_List();

            vector<int> stars_counts;
            string min_stars = review_table.countMin("stars");
            string max_stars = review_table.countMax("stars");
            for (auto x : rec) {
                string temp = x.get(3);                                        //get(3)) returns the stars for the record
                stars_counts.push_back(stoi(temp));
            }
            float stars_average = accumulate( stars_counts.begin(), stars_counts.end(), 0.0)/stars_counts.size();

            vector<string> businesses;                                        //vector used to find unique business count
            for (auto x : rec) {
               businesses.push_back(x.get(2));
            }
            sort(businesses.begin(), businesses.end());
            int unique_businesses = unique(businesses.begin(), businesses.end()) - businesses.begin();

            vector<string> users;                                            //vector used for finding unique user count
            for (auto x : rec) {
                users.push_back(x.get(1));
            }
            sort(users.begin(), users.end());
            int unique_users = unique(users.begin(), users.end()) - users.begin();

            /*
             Print statements
             */
            cout << endl;
            cout << "Summary of the Review table: " << endl;
            cout << "Number of entries: " << review_table.getSize() << endl;
            cout << "Stars - Min: " << min_stars << " Max: " << max_stars << " Average: " << stars_average << endl;
            cout << "Number of unique businesses : " << unique_businesses << endl;
            cout << "Number of unique users: " << unique_users << endl;
            break;

            break;
        }
    }
}

int main()
{
    build_Database();
    while (true) {              //while the input doesn't reach end-of-file, match results
        
        cout << endl;
        cout << "What would you like to do? Please enter your choice's respective number." << endl;
        cout << "(0) QUIT" << endl;
        cout << "(1) Search for a user's name" << endl;
        cout << "(2) Search for a business' name" << endl;
        cout << "(3) Search for reviews" << endl;
        cout << "(4) Cross join two tables" << endl;
        cout << "(5) Natural join two tables" << endl;
        cout << "(6) Query a table" << endl;
        cout << "(7) Print a table summary" << endl;
        
        int option;
        cout << "Your option: ";
        if(cin >> option)
        {
            
            switch (option) {
                case 0:
                {
                    return 0;
                }
                case 1: {
                    cout << endl;
                    cout << "Please enter a name: ";
                    string name;
                    cin >> name;
                    string query = "select * from user_table where name = " + name;  //select all attributes from user_table for a specific name
                    do_query(query);
                    break;
                }
                case 2: {
                    cout << endl;
                    cout << "Please enter a business: ";
                    string bus;
                    cin.ignore(numeric_limits<streamsize>::max(), '\n');
                    std::getline(cin, bus);
                    string query = "select * from business_table where name = " + bus;    //select all attributes from business_table for a specific business
                    cout << "Viewing results for " + bus << endl;
                    do_query(query);
                    break;
                }
                case 3:{ // search for good reviews or bad reviews
                    cout << "(a) Good Reviews" << endl;
                    cout << "(b) Bad Reviews" << endl;
                    char choice;
                    cout << "Your choice: ";
                    cin >> choice;
                    if(choice == 'a')
                    {
                        string query = "select * from review_table where stars >= 2.5";
                        cout << "Viewing good reviews: " << endl;
                        do_query(query);
                    }
                    else
                    {
                        string query = "select * from review_table where stars < 2.5";
                        cout << "Viewing bad reviews: " << endl;
                        do_query(query);
                    }
                    
                    break;
                }
                case 4: {   //option 4-- cross join
                    cout << endl;
                    cout << "Please enter two tables name: " << endl;
                    cout << "Note: This is done on subsets as the dataset is too large and requires too much memory (gigabytes!)" << endl;
                    cout << "The first is:(choose from user, business, review)" << endl;
                    
                    
                    string table_name1;
                    cin >> table_name1;
                    cout << "The second is:(choose from user, business, review)" << endl;
                    string table_name2;
                    cin >> table_name2;
                    Table cross_t;
                    cross_t = do_crossJoin(table_name1,table_name2);
                    printTable(cross_t);
                    break;
                    
                }
                case 5: { //option 5-- natural join
                    cout << endl;
                    cout << "Please enter two table names: " << endl;
                    cout << "Note: If choosing user or review for the first table, choose review as the second and vice versa." << endl;
                    cout << "The first is:(choose from user, business, review)" << endl;
                    string table_name1;
                    cin >> table_name1;
                    cout << "The second is:(choose from user, business, review)" << endl;
                    string table_name2;
                    cin >> table_name2;
                    Table natural_t;
                    natural_t = do_naturalJoin(table_name1,table_name2);
                    printTable(natural_t);
                    break;
                }
                    
                case 6: {  //option 6-- query a table using SQL format
                    cout << endl;
                    do_query("");
                    break;
                }
                case 7: {     //option 7-- gets a table's summary
                    
                    cout << endl;
                    cout << "Which table would you like a summary of? Please enter your choice's respective letter." << endl;
                    cout << "(a) Users" << endl;
                    cout << "(b) Businesses" << endl;
                    cout << "(c) Reviews" << endl;
                    char choice;
                    cout << "Your choice: ";
                    cin >> choice;
                    get_summary(choice);
                    break;
                }
                default:
                {
                    cout << "Not an option." << endl;
                    break;
                }
            }
        }
        else{
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(),'\n');
        }
        
        
        
        
    }
}
