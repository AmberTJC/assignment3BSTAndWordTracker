package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Word implements Comparable<Word>, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String word;
    private Map<String, List<Integer>> fileOccurrences;
    
    public Word(String word) {
        this.word = word;
        this.fileOccurrences = new HashMap<>();
    }
    
    public void addOccurrence(String filename, int lineNumber) {
        if (!fileOccurrences.containsKey(filename)) {
            fileOccurrences.put(filename, new ArrayList<>());
        }
        fileOccurrences.get(filename).add(lineNumber);
    }
    
    public String getWord() {
        return word;
    }
    
    public Map<String, List<Integer>> getFileOccurrences() {
        return fileOccurrences;
    }
    
    public int getTotalFrequency() {
        int total = 0;
        for (List<Integer> lines : fileOccurrences.values()) {
            total += lines.size();
        }
        return total;
    }
    
    public List<String> getFiles() {
        return new ArrayList<>(fileOccurrences.keySet());
    }
    
    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }
    
    @Override
    public String toString() {
        return word;
    }
}