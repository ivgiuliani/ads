#ifndef TRIE_H
#define TRIE_H

#include <string>

/* A trie implementation.
 * It accepts only strings with letters within 'a' and 'z' as keys
 */

using namespace std;

namespace ds {
	class TrieNotFound {};
	class TrieInvalidKey {};

	template <class T>
	class Trie {
		private:
			void assume_valid_key(string key) {
				for (unsigned short int i = 0; i < key.length(); i++)
					if !(key[i] >= 'a' && key[i] <= 'z') throw TrieInvalidKey();
			};
	
		public:
			Trie *ptrs[26];
			bool leaf;
			T value;
	
			Trie() {
				this->leaf = false;
				for (unsigned short int i = 0; i < 26; i++)
					ptrs[i] = NULL;
			};
	
			~Trie() {
				for (unsigned short int i = 0; i < 26; i++) {
					if (this->ptrs[i]) delete this->ptrs[i];
				}
			};

			void insert(string key, T value) {
				assume_valid_key(key);

				if (key.length() == 0) {
					this->value = value;
					this->leaf = true;
					return;
				}
	
				unsigned short int pos = key[0] - 'a';
				if (!this->ptrs[pos])
					this->ptrs[pos] = new Trie();
	
				this->ptrs[pos]->insert(key.substr(1), value);
			};
	
			Trie *find(string key) {
				assume_valid_key(key);

				Trie *curr = this;
				unsigned short int pos;
				for (unsigned short int i = 0; i < key.length(); i++) {
					pos = key[i] - 'a';
					curr = curr->ptrs[pos];
					if (!curr) return NULL;
				}
	
				return curr;
			};
	
			T get(string key) {
				assume_valid_key(key);

				Trie *ptr = this->find(key);
				if (!ptr) throw TrieNotFound();
	
				if (ptr->leaf)
					return ptr->value;
				throw TrieNotFound();
			};
	};

}; /* namespace:ds */

#endif /* TRIE_H */
