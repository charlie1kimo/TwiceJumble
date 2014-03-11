package jumble;

import java.io.*;
import java.util.*;

import trie.*;

public class Jumble {
	public HashMap<Character, Trie> dictionary;
	
	public Jumble(String[] wordFiles) {
		dictionary = new HashMap<Character, Trie>();
		buildDictionary(wordFiles);
	}
	
	/*
	 * private function for building the dictionary trie for search.
	 */
	private void buildDictionary(String[] wordFiles) {
		for (int i = 0; i < wordFiles.length; i++) {
			try {
				FileReader fr = new FileReader(wordFiles[i]);
				BufferedReader bfr = new BufferedReader(fr);
				String word;
				while ((word = bfr.readLine()) != null) {
					if (word.length() == 0) { continue; }
					
					char head = word.charAt(0);
					Trie trie;
					if (dictionary.containsKey(new Character(head))) {
						trie = dictionary.get(head);
					}
					else {
						trie = new Trie(new TrieNode(head));
						dictionary.put(new Character(head), trie);
					}
					
					TrieNode currNode = trie.getRoot();
					TrieNode newNode;
					for (int j = 1; j < word.length(); j++) {
						char curr = word.charAt(j);
						int searchIndexForCurr = currNode.getChildren().indexOf(new TrieNode(curr));
						
						if (searchIndexForCurr >= 0) {
							List<TrieNode> l = currNode.getChildren();
							currNode = l.get(searchIndexForCurr);
						}
						else {
							newNode = new TrieNode(curr);
							currNode.addChild(newNode);
							currNode = newNode;
						}
					}
					// put and EOW character
					newNode = new TrieNode(Trie.EOW);
					currNode.addChild(newNode);
				}
			}
			catch (Exception e) {
				String err = String.format("ERROR: Error in reading %s in Jumble.\n", wordFiles[i]);
				e.printStackTrace();
				System.err.println(err);
			}
		}
	}
	
	public Set<String> getJumbleWords(String sequence) {
		Set<Character> visited = new HashSet<Character>();
		Set<String> jumbleWords = new HashSet<String>();
		char start;
		
		for (int i = 0; i < sequence.length(); i++) {
			start = sequence.charAt(i);
			if (! visited.contains(start)) {
				String newSeq = ""+start;
				Character key = new Character(start);
				String remains = sequence.substring(0, i) + sequence.substring(i+1, sequence.length());
				if (dictionary.containsKey(key)) {
					getJumbleWordsHelper(jumbleWords, dictionary.get(key).getRoot(), newSeq, remains);
				}
				visited.add(start);
			}
		}
		return jumbleWords;
	}
	
	private void getJumbleWordsHelper(Set<String> jumbleWords, TrieNode currNode, String currSeq, String remains) {
		TrieNode key;
		String newRemains;
		List<TrieNode> children = currNode.getChildren();
		
		if (remains.length() == 0) {
			// check EOW
			key = new TrieNode(Trie.EOW);
			if (children.contains(key)) {
				if (!jumbleWords.contains(currSeq)) {
					jumbleWords.add(currSeq);
				}
				return;
			}
		}
		
		for (int i = 0; i < remains.length(); i++) {
			char currChar = remains.charAt(i);
			
			// check EOW
			key = new TrieNode(Trie.EOW);
			if (children.contains(key)) {
				if (!jumbleWords.contains(currSeq)) {
					jumbleWords.add(currSeq);
				}
			}
			
			// check children
			key = new TrieNode(currChar);
			if (children.contains(key)) {
				String newSeq = currSeq + currChar;
				currNode = children.get(children.indexOf(key));
				newRemains = remains.substring(0, i) + remains.substring(i+1, remains.length());
				getJumbleWordsHelper(jumbleWords, currNode, newSeq, newRemains);
			}
		}
		return;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] files = {"words/brit-a-z.txt"};
		//String[] files = {"words/american-words.80", "words/english-words.80"};
		//String[] files = {"words/testWords"};
		Jumble jumble = new Jumble(files);
		String input = "dog";
		Set<String> jumbleWords = jumble.getJumbleWords(input);
		System.out.println(input+": "+jumbleWords.toString());
	}

}
