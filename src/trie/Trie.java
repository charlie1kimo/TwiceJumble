package trie;

import java.util.*;

public class Trie {
	public static final char EOW = '0';
	private TrieNode root;
	
	public Trie(TrieNode root) {
		this.root = root;
	}
	
	public TrieNode getRoot() {
		return root;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TrieNode curr;
		Queue<TrieNode> bfsQ = new LinkedList<TrieNode>();
		
		bfsQ.add(root);		
		while (! bfsQ.isEmpty()) {
			curr = bfsQ.poll();
			sb.append(curr.toString()+",");
			List<TrieNode> children = curr.getChildren();
			for (int i = 0; i < children.size(); i++) {
				bfsQ.add(children.get(i));
			}
		}
		
		return sb.toString();
	}
}
