#include "Evaluator.h"


using namespace std;

/*
//get the letters' values from users
void Evaluator::usersValue()
{
	vector<string> temp;
	while(!helper.isEmpty())
		temp.push_back(helper.dequeue());
	for(int i = 0; i < temp.size()/2; i++)
		 {
		 	if(temp[i][0] >= 'a' && temp[i][0] <= 'z')
		 	{
		 		cout << "please input the value of " << temp[i][0] << ":" << endl;
		 		int value;
		 		cin >> value;
		 		temp[i] = to_string(value);
		 	}
		 }
	for(int i = 0;i < temp.size()/2; i++)
		postfix.enqueue(temp[i]);

}
*/
bool is_number(const std::string& s)
{
    return !s.empty() && std::find_if(s.begin(),
        s.end(), [](char c) { return !std::isdigit(c); }) == s.end();
}

string Evaluator::getValue() {
  /* returns the result of expression evaluation */
	while(!postfix.isEmpty())
	{
		if(postfix.first() != "=" && postfix.first() != "#" && postfix.first() != ">" && postfix.first() != "<" && postfix.first() != "@" && postfix.first() != "%" && postfix.first()!= "&" && postfix.first()!= "|" && postfix.first()!= "!")
		{
			//cout << "operands" << endl;
				valStack.push(postfix.first());
		}
		else if(postfix.first() == "=")
			{
				//cout << "=" << endl;
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					//cout << "---------------------" << endl;
					//cout << t -> get_record(i) -> get_entry(index) << endl;
					if(t -> get_record(i) -> get_entry(index) == compared)
						result = result + "1";
					else
						result = result + "0";
				}
				//cout << result << endl;
				valStack.push(result);
			}
		else if(postfix.first() == ">")
			{
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					int left;
					int right;
					if(is_number(t -> get_record(i) -> get_entry(index)) && is_number(compared))
						{
							left = stoi(t -> get_record(i) -> get_entry(index));
							right = stoi(compared);
						}

					if(left > right)
						result = result + "1";
					else
						result = result + "0";
				}
				valStack.push(result);
			}
		else if(postfix.first() == "<")
			{
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					int left;
					int right;
					if(is_number(t -> get_record(i) -> get_entry(index)) && is_number(compared))
						{
							left = stoi(t -> get_record(i) -> get_entry(index));
							right = stoi(compared);
						}

					if(left < right)
						result = result + "1";
					else
						result = result + "0";
				}
				valStack.push(result);
			}
		else if(postfix.first() == "#")
			{
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					if(t -> get_record(i) -> get_entry(index) != compared)
						result = result + "1";
					else
						result = result + "0";
				}
				valStack.push(result);
			}
		else if(postfix.first() == "@")
			{
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					int left;
					int right;
					if(is_number(t -> get_record(i) -> get_entry(index)) && is_number(compared))
						{
							left = stoi(t -> get_record(i) -> get_entry(index));
							right = stoi(compared);
						}

					if(left >= right)
						result = result + "1";
					else
						result = result + "0";
				}
				valStack.push(result);
			}
		else if(postfix.first() == "%")
			{
				string compared = valStack.pop();
				string attribute = valStack.pop();
				int index = 0;
				for( ; index < (t -> get_attributes()).size(); index++)
				{
					if(t -> get_attributes()[index] == attribute)
						break;
				}
				string result = "";

				// compare all records with the compared name one by one
				for(int i = 0; i < t -> get_size(); i++)
				{
					int left;
					int right;
					if(is_number(t -> get_record(i) -> get_entry(index)) && is_number(compared))
						{
							left = stoi(t -> get_record(i) -> get_entry(index));
							right = stoi(compared);
						}

					if(left <= right)
						result = result + "1";
					else
						result = result + "0";
				}
				valStack.push(result);
			}
		else if(postfix.first() == "&")
			{
				//cout << "&" << endl;
				string s2 = valStack.pop();
				string s1 = valStack.pop();
				string result = "";
/*?????????????????? if the size is different ???????????*/
				/*bitset<4> bitset1(s1);
				bitset<4> bitset2(s2);
				bitset<4> result_b = bitset1 & bitset2;
				string result = result_b.to_string();
				//cout << result << endl;*/
				for(int i = 0; i < s2.size(); i++)
				{
					if(s1[i] == '1' && s2[i] == '1')
						result = result + '1';
					else
						result = result + '0';
				}


				valStack.push(result);
			}
			else if(postfix.first() == "|")
			{
				string s2 = valStack.pop();
				string s1 = valStack.pop();
				string result = "";
/*?????????????????? if the size is different ???????????*/
				/*bitset<4> bitset1(s1);
				bitset<4> bitset2(s2);
				bitset<4> result_b = bitset1 | bitset2;
				string result = result_b.to_string();
				//cout << result << endl;*/

				/* stupid way*/
				for(int i = 0; i < s2.size(); i++)
				{
					if(s1[i] == '0' && s2[i] == '0')
						result = result + '0';
					else
						result = result + '1';
				}

				valStack.push(result);
			}
		else if(postfix.first() == "!")
			{
				//cout << "!" << endl;
				string s = valStack.pop();
				string result = "";
				for(int i = 0; i < s.size(); i++)
				{
					if(s[i] == '0')
						result = result + '1';
					else
						result = result + '0';
				}
				valStack.push(result);
			}

			postfix.dequeue();
	}
	return valStack.top();
}
