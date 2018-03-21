#include <iostream>
#include <vector>
#include <map>
#include <string>
#include <list>
#include <set>
#include <fstream>
#include <algorithm>
#include <ctime> /*time_t, difftime() - Use to find runtime of your function for lists of different sizes*/

using namespace std;

map<char, int> getCharMap(const string& s) {

	map<char, int> m;

	for (int i = 0; i < s.length(); ++i) {

		if (m.find(s[i]) == m.end())
			m[s[i]] = 1;
		else
			++m[s[i]];

	}

	return m;
}

bool areCharMapEqual(map<char, int>& m1, map<char, int>& m2) {

	if (m1.size() != m2.size())
		return false;

	auto it1 = m1.begin();
	auto it2 = m2.begin();


	while (it1 != m1.end()) {

		if (it1->first != it2->first || it1->second != it2->second)
			return false;

		++it1;
		++it2;
	}


	return true;
}


struct StringWithCharMap {
	string str;
	map<char, int> strMap;

	StringWithCharMap(string s, map<char, int> m) : str(s), strMap(m) {}
};


void findAnagrams(vector<string>&lines) {

	list<StringWithCharMap>linesCharMaps;

	for (int i = 0; i <lines.size(); ++i)
		linesCharMaps.push_back(
			StringWithCharMap(lines[i], getCharMap(lines[i]))
		);


	while (!linesCharMaps.empty()) {

		map<char, int> curStrMap = linesCharMaps.begin()->strMap;
		cout << linesCharMaps.begin()->str << " ";

		linesCharMaps.erase(linesCharMaps.begin());

		auto it = linesCharMaps.begin();
		while (!linesCharMaps.empty() && it != linesCharMaps.end()) {

			if (areCharMapEqual(curStrMap, it->strMap)) {
				cout << it->str << " ";
				it = linesCharMaps.erase(it);
			}
			else
				++it;

		}


		cout << endl;
	}


}




int main() {

	vector<string> lines;
	ifstream file("words.txt");
	string line;
	while (getline(file, line)) {
		if (!line.empty())
			lines.push_back(line);
	}

	sort(lines.begin(), lines.end());
	string filenames[] = { "word5.txt","word50.txt","word500.txt","word1K.txt","word5K.txt" };
	vector<string> tmp;
	int sizes[] = { 5,50,500,1000,5000 };

	
	/*Use only the make the different files consisting of
	lists of different lengths. You comment this section after the files are
	created*/
	//Beginning of Files Creation
	ofstream lfout;
	int index;

	for (int i = 0; i < 5; i += 1)
	{
		lfout.open(filenames[i], ofstream::out);
		tmp = lines;

		for (int j = 0; j < sizes[i]; j += 1)
		{
			index = rand() % tmp.size();
			lfout << tmp.at(index) << "\n";
		}

		lfout.close();
	}
	//End of Files Creation

	//Checking runtime of different list lengths
	double runtime;
	clock_t time;

	for (int i = 0; i < 5; i += 1)
	{
		tmp.clear();
		file.open(filenames[i], ifstream::in);

		while (getline(file, line))
		{
			if (!line.empty())
				tmp.push_back(line);
		}

		file.close();

		time = clock();
		findAnagrams(tmp);// Work on your findAnagrams() function
		time = clock() - time;
		runtime = (double)time/CLOCKS_PER_SEC;

		cout << "For a list of " << sizes[i] << " words the runtime is " << runtime << "\n";
	}

	

	system("pause");
}
