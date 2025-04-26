package utilities;

import java.io.*;
import java.util.*;
import domain.Word;
import implementations.BSTree;

/**
 * WordTracker
 * 
 * This program reads one or more text files and stores all unique words
 * into a Binary Search Tree (BST) along with their occurrence details
 * (filename and line numbers). It supports loading from and saving to 
 * a serialized repository, and generates output reports sorted either by
 * alphabetical order, total frequency, or number of files.
 * 
 * Usage:
 *   java -jar WordTracker.jar <file1> [<file2> ...] <option> [-f<outputFile>]
 * 
 * Options:
 *   -pf : Print words alphabetically with files they appear in.
 *   -pl : Print words alphabetically with files and line numbers.
 *   -po : Print words alphabetically with files, line numbers, and frequency.
 *   -f<outputFile> : (Optional) Save output to the specified file instead of printing to console.
 * 
 * Dependencies:
 * - domain.Word
 * - implementations.BSTree
 * - implementations.BSTreeNode
 * 
 * Author: [Team Mineru]
 * Date: [April 25, 2025]
 */

public class WordTracker {

    private static final String REPO_FILENAME = "repository.ser";

    public static void main(String[] args) {
        BSTree<Word> tree = loadRepository();

        List<String> filesToProcess = new ArrayList<>();
        boolean sortAlpha = false, sortFreq = false, sortFiles = false;
        String outputFilename = null;

        // Parse command-line args
        for (String arg : args) {
            if (arg.startsWith("-")) {
                if (arg.equals("-po")) sortAlpha = true;
                else if (arg.equals("-pf")) sortFreq = true;
                else if (arg.equals("-pl")) sortFiles = true;
                else if (arg.startsWith("-f")) outputFilename = arg.substring(2);
            } else {
                filesToProcess.add(arg);
            }
        }

        if (!filesToProcess.isEmpty()) {
            for (String filename : filesToProcess) {
                File file = new File(filename);
                if (file.exists()) {
                    processFile(file, tree);
                } else {
                    System.out.println("File not found: " + filename);
                }
            }
        }

        saveRepository(tree);
        printOutput(tree, sortAlpha, sortFreq, sortFiles, outputFilename);
    }

    private static BSTree<Word> loadRepository() {
        File file = new File(REPO_FILENAME);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                BSTree<Word> tree = (BSTree<Word>) in.readObject();
                System.out.println("Repository loaded.");
                return tree;
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading repository: " + e.getMessage());
            }
        }
        return new BSTree<>();
    }

    private static void saveRepository(BSTree<Word> tree) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(REPO_FILENAME))) {
            out.writeObject(tree);
            System.out.println("Repository saved.");
        } catch (IOException e) {
            System.err.println("Error saving repository: " + e.getMessage());
        }
    }

    private static void processFile(File file, BSTree<Word> tree) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().split("[^a-zA-Z]+");
                for (String wordText : words) {
                    if (wordText.isEmpty()) continue;

                    Word word = new Word(wordText);
                    Word existing = tree.search(word) != null ? tree.search(word).getElement() : null;

                    if (existing == null) {
                        word.addOccurrence(file.getPath(), lineNumber);
                        tree.add(word);
                    } else {
                        existing.addOccurrence(file.getPath(), lineNumber);
                    }
                }
                lineNumber++;
            }

        } catch (IOException e) {
            System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
        }
    }

    private static void printOutput(BSTree<Word> tree, boolean sortAlpha, boolean sortFreq, boolean sortFiles, String outputFile) {
        List<Word> words = new ArrayList<>();
        utilities.Iterator<Word> iterator = tree.inorderIterator();

        while (iterator.hasNext()) {
            words.add(iterator.next());
        }

        if (sortFreq) {
            words.sort((a, b) -> Integer.compare(b.getTotalFrequency(), a.getTotalFrequency()));
        } else if (sortFiles) {
            words.sort((a, b) -> Integer.compare(b.getFiles().size(), a.getFiles().size()));
        } else {
            Collections.sort(words); // alphabetical
        }

        PrintStream out = System.out;
        if (outputFile != null) {
            try {
                out = new PrintStream(new FileOutputStream(outputFile));
            } catch (FileNotFoundException e) {
                System.err.println("Error opening output file: " + e.getMessage());
                return;
            }
        }

        //message print to user
        if (sortAlpha) {
            out.println("Words in alphabetical order:");
        } else if (sortFreq) {
            out.println("Words sorted by total frequency:");
        } else if (sortFiles) {
            out.println("Words sorted by number of files:");
        }

        //print words and file with line info
        for (Word word : words) {
            out.println(word.getWord());
            for (Map.Entry<String, List<Integer>> entry : word.getFileOccurrences().entrySet()) {
                out.println("  " + entry.getKey() + " -> lines: " + entry.getValue());
            }
        }

        if (outputFile != null) {
            out.close();
        }
    }
}
