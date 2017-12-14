// TESTCASE
#define CATCH_CONFIG_MAIN
#include "catch.hpp"
#include "Record.h"
#include "Database.h"
#include "Table.h"
#include <iostream>
#include <string>
#include <vector>
#include <stdbool.h>
using namespace std;

TEST_CASE("testing everything", "[testing]") {

	Record r1(4);
	Record r2(4);
	Record r3(4);
	Record r4(4);
	Record r5(4);
	Record r6(4);

	Record r7(6);
	Record r8(6);
	Record r9(6);
	Record r10(6);
	Record r11(6);
	Record r12(6);
    
    // test for []
	r1[0] = "A";
	r1[1] = "00";
	r1[2] = "M";
	r1[3] = "2017";

	r2[0] = "B";
	r2[1] = "01";
	r2[2] = "F";
	r2[3] = "2018";

	r3[0] = "C";
	r3[1] = "02";
	r3[2] = "M";
	r3[3] = "2020";

	r4[0] = "D";
	r4[1] = "03";
	r4[2] = "F";
	r4[3] = "2021";

	r5[0] = "E";
	r5[1] = "04";
	r5[2] = "F";
	r5[3] = "2019";

	r6[0] = "F";
	r6[1] = "05";
	r6[2] = "M";
	r6[3] = "2018";

	// test for set() and get()
	r7.set(0, "G");
	r7.set(1, "06");
	r7.set(2, "F");
	r7.set(3, "2021");
	r7.set(4, "2.4");
	r7.set(5, "C");

	r8.set(0, "H");
	r8.set(1, "07");
	r8.set(2, "F");
	r8.set(3, "2021");
	r8.set(4, "3.7");
	r8.set(5, "A");

	r9.set(0, "I");
	r9.set(1, "08");
	r9.set(2, "M");
	r9.set(3, "2019");
	r9.set(4, "4.0");
	r9.set(5, "A");

	r10.set(0, "J");
	r10.set(1, "09");
	r10.set(2, "M");
	r10.set(3, "2017");
	r10.set(4, "2.9");
	r10.set(5, "C");

	r11.set(0, "K");
	r11.set(1, "10");
	r11.set(2, "F");
	r11.set(3, "2018");
	r11.set(4, "3.5");
	r11.set(5, "C");

	r12.set(0, "L");
	r12.set(1, "11");
	r12.set(2, "M");
	r12.set(3, "2020");
	r12.set(4, "3.1");
	r12.set(5, "B");

    // test []
	SECTION ("Tesing [] for Record class") {
		REQUIRE( r1[0] == "A");
	}

    // test get()
    SECTION ("Tesing get() function for Record class") {
        REQUIRE(r7.get(0) == "G");
    }

    //test size()
    SECTION ("Tesing size() function for Record class") {
        REQUIRE( r1.size() == 4);
    }

    // create two tables using default constructor
    Table t1;
    Table t2;
    // create Database using default constructor 
    Database db;

    SECTION("Testing listTable() function"){
    // test listTables()
    list<string> test_list = {"t1","t2"};   
    REQUIRE(db.listTables() == test_list);
    }

    SECTION("Testing dropTable() function"){
    // test dropTables()
    list<string> test_list = {"t1"};
    db.dropTable("t2");
    REQUIRE(db.listTables() == test_list);
    }

    SECTION("Testing getTable() function"){
    // test getTables()
    list<Table*> test_get = {&t1};   
    REQUIRE(db.getTables() == test_get);
    }

    db.addTable(t2,"t2");

	// insert record to table
    t1.insertEntry(r1);
    t1.insertEntry(r2);
    t1.insertEntry(r3);
    t1.insertEntry(r4);
    t1.insertEntry(r5);
    t1.insertEntry(r6);
    
    t2.insertEntry(r7);
    t2.insertEntry(r8);
    t2.insertEntry(r9);
    t2.insertEntry(r10);
    t2.insertEntry(r11);
    t2.insertEntry(r12);

    // use overload constructor to add attributes to the table
    vector<string> attributes1 = {"name", "UIN" , "gender", "graduation year"};
    t1 = Table(attributes1);
    vector<string> attributes2 = {"name", "UIN", "gender", "graduation year", "GPA","grade"};
    t2 = Table(attributes2);

    // test for query function
    Table* q1 = db.query("*", "t1", "graduation year = 2019");
    Table* q2 = db.query("name, UIN", "t2", "((UIN > 09) OR ((gender = M) AND (graduation year <= 2020))");
    Table* q3 = db.query("name, graduation year, grade", "t2", "((GPA >= 3.2) OR (grade = A)) AND ((graduation year = 2019) OR (graduation year = 2018))");
    // if they didn't match anything and then return a NULL, make it to be an empty table
    Table empty_t;
    if(q1 == NULL)
        q1 = &empty_t;
    if(q2 == NULL)
        q2 = &empty_t;
    if(q3 == NULL)
        q3 = &empty_t;

    // correct answers for query test
    // test elements one by one because there is no overloaded == for Table class
    /* query test1*/
    vector<string> atb_tq1 = {"name","UIN","gender","graduation year"};
    bool test_1 = (q1 -> getAttributes() == atb_tq1);
    vector<string> tq1_r;
    tq1_r.push_back((q1 -> getRecord_List()[0])[0]);
    tq1_r.push_back((q1 -> getRecord_List()[0])[1]);
    tq1_r.push_back((q1 -> getRecord_List()[0])[2]);
    tq1_r.push_back((q1 -> getRecord_List()[0])[3]);
    vector<string> tq1_a = {"E","04","F","2019"};
    bool test_2 = (tq1_r == tq1_a);
    bool test_tq1 = (test_1 && test_2);

    // test query function
    SECTION("Query test1") {
    REQUIRE(test_tq1 == true);
    }

    /* query test2*/
    vector<string> atb_tq2 = {"name","graduation year","grade"};
    bool test_3 = (q2 -> getAttributes() == atb_tq2);
    vector<string> tq2_r;
    tq2_r.push_back((q2 -> getRecord_List()[0])[0]);
    tq2_r.push_back((q2 -> getRecord_List()[0])[1]);
    tq2_r.push_back((q2 -> getRecord_List()[0])[2]);
    tq2_r.push_back((q2 -> getRecord_List()[1])[0]);
    tq2_r.push_back((q2 -> getRecord_List()[1])[1]);
    tq2_r.push_back((q2 -> getRecord_List()[1])[2]);
    vector<string> tq2_a = {"I","2019","A","K","2018","A"};
    bool test_4 = (tq2_r == tq2_a);
    bool test_tq2 = (test_3 && test_4);

    SECTION("Query test2") {
    REQUIRE(test_tq2 == true);
    }
    
    /* query test3*/
    Table tq3;
    vector<string> atb_tq3 = {"name","UIN"};
    bool test_5 = (q3 -> getAttributes() == atb_tq3);
    vector<string> tq3_r;
    tq3_r.push_back((q3 -> getRecord_List()[0])[0]);
    tq3_r.push_back((q3 -> getRecord_List()[0])[1]);
    tq3_r.push_back((q3 -> getRecord_List()[1])[0]);
    tq3_r.push_back((q3 -> getRecord_List()[1])[1]);
    tq3_r.push_back((q3 -> getRecord_List()[2])[0]);
    tq3_r.push_back((q3 -> getRecord_List()[2])[1]);
    tq3_r.push_back((q3 -> getRecord_List()[3])[0]);
    tq3_r.push_back((q3 -> getRecord_List()[3])[1]);
    vector<string> tq3_a = {"I","08","J","09","K","10","L","11"};
    bool test_6 = (tq3_r == tq3_a);
    bool test_tq3 = (test_5 && test_6);

    SECTION("Query test3") {
    REQUIRE(test_tq3 == true);
    }

    // test renameAttribute() and getAttributes()
    // test for adding an attribute to the table
    t1.renameAttribute("","birthday");
    SECTION("Testing renameAttribute() and getAttributes() for adding") {
    vector<string> get1 = {"name", "UIN" , "gender", "graduation year","birthday"};
    REQUIRE(t1.getAttributes()== get1);
    }
    // test for renaming an existed attribute
    t2.renameAttribute("GPA","birthday"); 
    SECTION("Testing renameAttribute() and getAttributes() for renaming") {
    vector<string> get2 = {"name", "UIN" , "gender", "graduation year","birthday","grade"};
    REQUIRE(t2.getAttributes()== get2);
    }

    // test addEntry()
    t1.addEntry("Hometown");
    SECTION("Testing addEntry") {
    REQUIRE(r1[0] == "Hometown");
    }

    //test deleteEntry()
    t1.deleteEntry("Hometown");
    SECTION("Testing deleteEntry") {
    REQUIRE(r1[0] == "A");
    }

    //test findRecord()
    // test iterator begin() and end() also 
    vector<Record>::iterator i1 = t1.findRecord("UIN");
    vector<Record>::iterator i2 = t2.findRecord("name");

    // test setKey()
    t1.setKey("UIN");
    t2.setKey("name");

    // test specifyKey()
    t1.specifyKey();
    t2.specifyKey();
    
    //test getRecord_List()
    SECTION("Testing getRecord_List()") {
    vector<string> test_list;
    test_list.push_back(r7[0]);
    test_list.push_back(r8[0]);
    test_list.push_back(r9[0]);
    test_list.push_back(r10[0]);
    test_list.push_back(r11[0]);
    test_list.push_back(r12[0]);
    vector<string> correct_list = {"G","H","I","J","K","L"};
    REQUIRE(test_list == correct_list);
    }
    
    // test getSize()
    SECTION("Testing getSize()") {
    REQUIRE(t2.getSize() == 6);
    }
    
     // test countEntries()
    SECTION ("testing countEntries() functions") {
    REQUIRE(t1.countEntries("M") == 3);
	}

    // test countMin()
    SECTION ("testing countMin() functions") {
    REQUIRE(t1.countMin("graduation year") == 2017);
    }

    // test countMax
    SECTION ("testing countMax() functions") {
    REQUIRE(t2.countMax("GPA") == 4);
    }


/*build new two simple tables to test cross join
-------------------------------------------------------------------------------
*/
    Record r_1(2);
    Record r_2(2);
    Record r_3(1);
    Record r_4(1);

    r_1[0] = "Jason";
    r_1[1] = "1";
    r_2[0] = "Jane";
    r_2[1] = "2";
    r_3[0] = "IT";
    r_4[0] = "HR";

    Table t3;
    Table t4;

    t3.insertEntry(r_1);
    t3.insertEntry(r_2);
    t4.insertEntry(r_3);
    t4.insertEntry(r_4);


    vector<string> attributes3 = {"name", "DepartmentID"};
    t3 = Table(attributes3);
    vector<string> attributes4 = {"DepartmentName"};
    t4 = Table(attributes4);


    vector<string> attributes_c = {"name", "DepartmentID","DepartmentName"};

    //test for cross join
    Table cross_table = t3.crossJoin_2(t3,t4);

    SECTION("Testing cross join") {

    // test for natural join attributes 
    bool test1 = (cross_table.getAttributes() == attributes_c);

    // test for first column
    vector<string> first_c;
    first_c.push_back((cross_table.getRecord_List()[0])[0]);
    first_c.push_back((cross_table.getRecord_List()[1])[0]);
    first_c.push_back((cross_table.getRecord_List()[2])[0]);
    first_c.push_back((cross_table.getRecord_List()[3])[0]);
    vector<string> first_correct = {"Jason","Jane","Jason","Jane"};
    bool test2 = (first_c == first_correct);
    bool test = ((test1 == true) && (test2 == true));
    // by testing the first column and the attributes to compare the whole table becasue there is no overload ==
    REQUIRE(test == true);
    }

/*--------------------------------------------------------------------------------*/
//test the exception of natural join, should throw an error
    Record n1(2);
    Record n2(2);
    Record n3(2);
    Record n4(2);
    Record n5(2);
    Record n6(2);

    n1[0] = "Jason";
    n1[1] = "1";
    n2[0] = "Jane";
    n2[1] = "2";
    n3[0] = "Jennifer";
    n3[1] = "5";
    n4[0] = "1";
    n4[1] = "A";
    n5[0] = "2";
    n5[1] = "C";
    n6[0] = "5";
    n6[1] = "B";

    Table t5;
    Table t6;

    t5.insertEntry(n1);
    t5.insertEntry(n2);
    t5.insertEntry(n3);
    t6.insertEntry(n4);
    t6.insertEntry(n5);
    t6.insertEntry(n6);
    


    vector<string> attributes5 = {"name", "UIN"};
    t5 = Table(attributes5);
    vector<string> attributes6 = {"UIN", "grade"};
    t6 = Table(attributes6);

    // set key for the second table
    t6.setKey("UIN"); 

// build new two tables and test for success natural join
    vector<string> attributes_n = {"name", "UIN","grade"};

    //test for natural join
    Table natural_table = t5.naturalJoin(t5,t6);

    SECTION("Testing natural join") {

    // test for natural join attributes 
    bool test1 = natural_table.getAttributes() == attributes_n;

    // test for first column
    vector<string> first_c;
    first_c.push_back((natural_table.getRecord_List()[0])[0]);
    first_c.push_back((natural_table.getRecord_List()[1])[0]);
    first_c.push_back((natural_table.getRecord_List()[2])[0]);
    vector<string> first_correct = {"Jason","Jane","Jennifer"};
    bool test2 = (first_c == first_correct);
    bool test = ((test1 == true) && (test2 == true));
    // by comparing the first column and the attributes to compare the whole table, because there is no overload == 
    REQUIRE(test == true);
    }


}
