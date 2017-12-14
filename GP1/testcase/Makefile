# makefile
# compile: make run: ./TESTCASE

all: TESTCASE

TESTCASE: 
	g++ -std=c++11 -O2 TESTCASE.cpp -L. -lDatabaseLib -o TESTCASE
	export LD_LIBRARY_PATH=:LD_LIBRARY_PATH   #add shared library to our path 

clean:
	rm *.o TESTCASE
