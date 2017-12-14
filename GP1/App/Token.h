#ifndef TOKEN_H
#define TOKEN_H

#include <string>

using namespace std;

class Token {
	string value;
	string kind;
	int precedence;

public:
	Token(string str);
	bool isOperand() {return kind == "";}
	string getValue() {return value;}
	string getKind() {return kind;}
	int getPrec() {return precedence;}
};

Token::Token(string str) {
	if (str == "OR") {
		kind = str;
		value = "";
		precedence = 1;
	}
	else if (str == "AND") {
		kind = str;
		value = "";
		precedence = 2;
	}
	else if (str == "NOT") {
		kind = str;
		value = "";
		precedence = 3;
	}
	else if (str == "=" || str == "<=" || str == ">=" || str == "<" || str == ">" || str == "<>") {
		kind = str;
		value = "";
		precedence = 4;
	}
	else if (str == "(" || str == ")") {
		kind = str;
		value = "";
		precedence = 0;
	}
	else {
		kind = "";
		value = str;
		precedence = 0;
	}
}
 

#endif