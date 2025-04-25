package serialization;

import java.io.*;
import implementations.BSTree;

public class SerializeBSTree {

    public static void serialize(BSTree<?> tree, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(tree);
        } catch (IOException e) {
            System.err.println("Serialization error: " + e.getMessage());
        }
    }

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