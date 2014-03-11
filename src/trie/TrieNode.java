package trie;

import java.util.*;


public class TrieNode {
	private Character character;
	private List<TrieNode> children;
	
	public TrieNode(char character) {
		this.character = new Character(character);
		children = new ArrayList<TrieNode>();
	}
	
	public Character getChar() {
		return character;
	}
	
	public List<TrieNode> getChildren() {
		return children;
	}
	
	public void addChild(TrieNode child) {
		children.add(child);
	}
	
	public String toString() {
		return character.toString();
	}
	
	/*
	 * Override implementation for using Set.contains
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TrieNode) {
			return character.equals(((TrieNode)obj).character);
		}
		return false;
	}
	
	/*
	 * Override implementation for using Set.contains
	 */
	public int hashCode() {
		return character.hashCode();
	}
}
