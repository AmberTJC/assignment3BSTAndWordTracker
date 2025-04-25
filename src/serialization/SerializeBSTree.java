package serialization;

import java.io.*;
import implementations.BSTree;


/**
 * Provides serialization and deserialization functionality for Binary Search Tree objects.
 * Allows saving and loading binary search trees to and from files.
 */
 
public class SerializeBSTree {

	/**
	 * Serializes a Binary Search Tree to a file.
	 * @param tree
	 * @param filename
	 * @throws IOException If an I/O errors occurs
     * Precondition: Tree object exists and filename is valid
     * Postcondition: The tree is serialized and saved to the specified file
	 */
    public static void serialize(BSTree<?> tree, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(tree);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

    /**
     * Deserializes a Binary Search Tree to a file.
     * @param filename
     * @return The deserialized BSTree or null if error occurs
     * @throws IOException If an I/O error occurs
     * @throws ClassNotFoundException If the class of the serialized object cannot be found
     * Precondition: The file exists and contains a serialized BSTree object
     * Postcondition: The BSTree object is created from the file, or null is returned if an error occurs
     */
    @SuppressWarnings("unchecked")
    public static BSTree<?> deserialize(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            return (BSTree<?>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization error: " + e.getMessage());
            return null;
        }
    }
}