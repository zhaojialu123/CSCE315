#ifndef TOKEN_TREE_H
#define TOKEN_TREE_H

#include "Token.h"
#include <iostream>

struct TokenTree {
	Token t;
	TokenTree* leftChild;
	TokenTree* rightChild;
	
	TokenTree(Token _t) : t(_t) {
		leftChild = NULL;
		rightChild = NULL;
		//cout << t.getKind() << endl;
	}
};

//Only used for error checking
void outputTree(TokenTree* tree) {
	//cout << tree << endl;
	if (tree == NULL) {return;}
	else {
		outputTree(tree->leftChild);
		outputTree(tree->rightChild);
		std::cout << tree->t.getValue();
		std::cout << '\t' << tree->t.getKind() << std::endl;
	}
}

#endif