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
						//System.out.println(currNode.toString()+" "+currNode.getChildren().toString());
						if (currNode.getChildren().contains(new TrieNode(curr))) {
							List<TrieNode> l = currNode.getChildren();
							currNode = l.get(l.indexOf(new TrieNode(curr)));
						}
						else {
							newNode = new TrieNode(curr);
							currNode.addChild(newNode);
							currNode = newNode;
						}
					}
					// put and EOW character
					//System.out.println(currNode.toString()+" "+currNode.getChildren().toString());
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
	
	public void getJumbleWords(String sequence) {
		for (int i = 0; i < sequence.length(); i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(sequence.charAt(i));
			Character key = new Character(sequence.charAt(i));
			String remains = sequence.substring(0, i) + sequence.substring(i+1, sequence.length());
			getJumbleWordsHelper(dictionary.get(key).getRoot(), sb, remains);
		}
	}
	
	private void getJumbleWordsHelper(TrieNode currNode, StringBuilder currSeq, String remains) {
		TrieNode key;
		String newRemains;
		List<TrieNode> children = currNode.getChildren();
		
		if (remains.length() == 0) {
			// check EOW
			key = new TrieNode(Trie.EOW);
			if (children.contains(key)) {
				System.out.print(currSeq+",");
				return;
			}
		}
		
		for (int i = 0; i < remains.length(); i++) {
			char currChar = remains.charAt(i);
			
			// check EOW
			key = new TrieNode(Trie.EOW);
			if (children.contains(key)) {
				System.out.print(currSeq+",");
			}
			
			// check children
			key = new TrieNode(currChar);
			if (children.contains(key)) {
				currSeq.append(currChar);
				currNode = children.get(children.indexOf(key));
				newRemains = remains.substring(0, i) + remains.substring(i+1, remains.length());
				getJumbleWordsHelper(currNode, currSeq, newRemains);
			}
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] files = {"words/brit-a-z.txt"};
		//String[] files = {"words/american-words.80", "words/english-words.80"};
		//String[] testFiles = {"words/testWords"};
		Jumble jumble = new Jumble(files);
		jumble.getJumbleWords("dog");
	}

}
