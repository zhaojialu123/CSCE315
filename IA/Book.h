// Book.h
#include <string>
#include <vector>
using namespace std;

// Define a book class 
struct Book {
    string ISBN = "";
    string Title = "";
    string Author = "";
    string Edition = "";
    string Date = "";
    // different format for a book 
    string cost_N = "";
    string cost_U = "";
    string cost_R = "";
    string cost_E = ""; 
    // different format has different prices,indicates whether the price for a New, Used, Rented, or Electronic version of the book.  
    char format_N = '\0'; 
    char format_U = '\0';
    char format_R = '\0';
    char format_E = '\0';
};