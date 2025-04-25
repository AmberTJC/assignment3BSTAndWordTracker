package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a word and tracks its occurrences across different files.
 */
public class Word implements Comparable<Word>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String word;
    private Map<String, List<Integer>> fileOccurrences;
    
    /**
     * Creates a new Word with the specified text.
     * @param word
     * Precondition: word is not null
     * Postcondition: A new Word object is created with the specified text and empty occurrences
     */
    public Word(String word) {
        this.word = word;
        this.fileOccurrences = new HashMap<>();
    }
    
    /**
     * Record the occurrence of the word at specified filename and line occurrence.
     * @param filename
     * @param lineNumber
     * Precondition: filename is not null
     * Postcondition: The occurrence is recorded in the fileOccurrences map
     */
    public void addOccurrence(String filename, int lineNumber) {
        fileOccurrences.computeIfAbsent(filename, k -> new ArrayList<>()).add(lineNumber);
    }
    
    /**
     * Gets the text of this word.
     * @return The word's text
     * Precondition: The Word object is initialized
     * Postcondition: The text of the word is returned
     */
    public String getWord() {
        return word;
    }
    
    /**
     * Gets the map of file occurrences for the word.
     * @return A map with filenames as keys and lists of line numbers as values
     * Precondition: The Word object is initialized
     * Postcondition: The map of file occurrences is returned
     */
    public Map<String, List<Integer>> getFileOccurrences() {
        return fileOccurrences;
    }
    
  
    /**
     * Calculates the words total number of occurrences.
     * @return total frequency count of the word
     * Precondition: The word object is initialized
     * Postcondition: The total number of occurrences is returned
     */
    public int getTotalFrequency() {
        int total = 0;
        for (List<Integer> lines : fileOccurrences.values()) {
            total += lines.size();
        }
        return total;
    }
    
  
    /**
     * Gets a list of all files where this word occurs.
     * @return A list of filenames
     * Precondition: The word object is initialized
     * Postcondition: A list containing the names of all files where this word occurs is returned
     */
    public List<String> getFiles() {
        return new ArrayList<>(fileOccurrences.keySet());
    }
    
    /**
     * Compares this word to another word alphabetically.
     * @return the compared result
     * Precondition: other is not null
     * Postcondition: The result is returned
     */
    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(": ");
        fileOccurrences.forEach((file, lines) -> sb.append(file).append(" -> ").append(lines).append("  "));
        return sb.toString().trim();
    }


    
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (fileOccurrences == null) {
            fileOccurrences = new HashMap<>();
        }
    }

}
