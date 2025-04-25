package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Stack;

import utilities.BSTreeADT;
import utilities.Iterator;
/**
 * Class for the BST data structure
 * @param <E> Elements that the list holds.
 */
public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;
	private BSTreeNode<E> root;
	private int size;
	
	
	
	public BSTree () 
	{
		root = null;
		size = 0;
	}
	public BSTree(E rootElement) 
	{
		this();
		add(rootElement);
	}
	
/**
 * gets the root node
 * Precondition: the tree exists.
 * Postcondition: Root node returned
 * @return root node
 */
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException
	{
		if (isEmpty()) 
		{
			throw new NullPointerException("The tree is empty");
		}
		return root;
	}

	
	/**
	 * gets the height of the tree
	 * Precondition: tree exists
	 * Postcondition: height returned
	 * @return height of the tree
	 */
	@Override
	public int getHeight()
	{
		return getHeight(root);
	}
	
	
	/**
	 * Helper method to getHeight
	 * Precondition: none
	 * Postcondition: Height calculated for the tree
	 * @param node node on the tree
	 * @return calculated height of the tree
	 */
	private int getHeight(BSTreeNode<E> node) 
	{
		if(node == null) 
		{
			return 0;
		}
		
		return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
	}

	
	/**
	 * Calculate size of the tree
	 * Precondition: none
	 * Postcondition: size returned
	 * @return size of tree
	 */
	@Override
	public int size()
	{
		return size;
	}

	
	/**
	 * tells if tree is empty
	 * Precondition: none
	 * Postcondition: returns true if empty, false otherwise
	 * @return true if empty, false otherwise
	 */
	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	
	/**
	 * clears all nodes from the tree
	 * Precondition: none
	 * Postcondition: tree will be empty
	 */
	@Override
	public void clear()
	{
		root = null;
		size = 0;
		
	}

	
	/**
	 * shows what a given node contains
	 * Preconditions: tree exists and entry cannot be null
	 * Postconditions: entry on a given node is returned
	 * @return element attached to a given node
	 */
	@Override
	public boolean contains(E entry) throws NullPointerException
	{
		if (entry == null) 
		{
			throw new NullPointerException("Cannot search for null");
		}
		return search(entry) != null;
	}

	
	/**
	 * search for the element attached to a node
	 * Precondition: tree must exists and entry cannot be null
	 * Postcondition: returns the node with that element attached
	 * @return node with the searched element
	 */
	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException
	{
		if (entry == null) 
		{
			throw new NullPointerException("Cannot search for null");
		}
		return search(root, entry);
	}
	
	
	/**
	 * helper method for search
	 * @param node the node searched for
	 * @param entry the element attached to the node
	 * @return the searched for node
	 */
	private BSTreeNode<E> search(BSTreeNode<E> node, E entry)
	{
		if (node == null) 
		{
			return null;
		}
		
		int comp = entry.compareTo(node.getElement());
		if (comp == 0) 
		{
			return node;
		}
		
		if (comp < 0) 
		{
			return search(node.getLeft(), entry);
		}
		
		return search(node.getRight(), entry);
	}

	
	/**
	 * adds a node and element pair to the tree
	 * Precondition: tree must exist and the new node cannot be null
	 * Postcondition: node added to the tree
	 * @return returns true if node is added
	 */
	@Override
	public boolean add(E newEntry) throws NullPointerException
	{
		if (newEntry == null) 
		{
			throw new NullPointerException("Cannot add null");
		}
		
		if (root == null) 
		{
			root = new BSTreeNode<>(newEntry);
			size++;
			return true;
		}
		return add(root, newEntry);
	}
	
	/**
	 * helper method to add
	 * @param node node to be added
	 * @param entry element attached to node to be added
	 * @return returns true if node is added
	 */
	private boolean add(BSTreeNode<E> node, E entry) 
	{
		int comp = entry.compareTo(node.getElement());
		if (comp == 0) 
		{
			return false;
		}
		
		if (comp < 0) 
		{
			if (node.getLeft() == null) 
			{
				node.setLeft(new BSTreeNode<>(entry));
				size++;
				return true;
			}
			else 
			{
				return add(node.getLeft(), entry);
			}
		}
		else 
		{
			if (node.getRight()== null) 
			{
				node.setRight(new BSTreeNode<>(entry));
				size++;
				return true;
			}
			else 
			{
				return add(node.getRight(), entry);
			}
		}
	}

	
	/**
	 * removes smallest element/node pair from the tree
	 * Precondition: tree must exist
	 * Postcondition: returns node removed
	 * @return node removed
	 */
	@Override
	public BSTreeNode<E> removeMin()
	{
		if (root == null) 
		{
			return null;
		}
		
		final BSTreeNode<E>[] result = removeMin(root, null);
		root = result[1];
		size--;
		return result[0];
	}
	
	/**
	 * helper method to removeMin
	 * @param node node to be removed
	 * @param parent parent node of the node to be removed
	 * @return node to be removed
	 */
	private BSTreeNode<E>[] removeMin(BSTreeNode<E> node, BSTreeNode<E> parent)
	{
		if (node.getLeft()== null) 
		{
			if (parent == null) 
			{
				return new BSTreeNode[] {node, node.getRight()};
			}
			
			parent.setLeft(node.getRight());
			return new BSTreeNode[] {node, root};
		}
		
		return removeMin(node.getLeft(), node);
	}

	
	/**
	 * removes largest node in the tree
	 * Precondition: tree must exist
	 * Postcondition: largest node removed
	 * @return returns removed node
	 */
	@Override
	public BSTreeNode<E> removeMax()
	{
		if (root == null) 
		{
			return null;
		}
		final BSTreeNode<E>[] result = removeMax(root, null);
		root = result[1];
		size--;
		return result[0];
		
	}
	
	
	/**
	 * helper method to removeMax
	 * @param node node to be removed
	 * @param parent parent of the node to be removed
	 * @return node removed
	 */
	private BSTreeNode<E>[] removeMax(BSTreeNode<E> node, BSTreeNode<E> parent)
	{
		if(node.getRight() == null) 
		{
			if (parent == null) 
			{
				return new BSTreeNode[] {node, node.getLeft()};
			}
			parent.setRight(node.getLeft());
			return new BSTreeNode[] {node, root};
		}
		return removeMax(node.getRight(), node);
	}

	
	/**
	 * generates an inorder iterator, resulting elements are in their natural order.
	 * Precondition: tree exists
	 * Postcondition: elements are presented in the order that they are entered into the tree
	 * @return an Iterator with elements in natural order
	 */
	@Override
	public Iterator<E> inorderIterator()
	{
		Stack <BSTreeNode<E>> stack = new Stack<>();
		return new Iterator<E>() 
		{
			BSTreeNode<E> current = root;

			@Override
			public boolean hasNext()
			{
				return !stack.isEmpty() || current != null;			
			}

			@Override
			public E next() throws NoSuchElementException
			{
				while(current != null) 
				{
					stack.push(current);
					current = current.getLeft();
				}
				if (!stack.isEmpty()) 
				{
					BSTreeNode<E> node = stack.pop();
					current = node.getRight();
					return node.getElement();
				}
				throw new NoSuchElementException();
			}
		};
	}

	
	/**
	 * Returns an iterator with elements in order of root element first.
	 * Precondition: tree exists
	 * Postcondition: returns iterator with root element first order
	 * @return root element first Iterator
	 */
	@Override
	public Iterator<E> preorderIterator()
	{
		Stack <BSTreeNode<E>> stack = new Stack<>();
		if (root != null) 
		{
			stack.push(root);
		}
		return new Iterator<E>() 
		{

			@Override
			public boolean hasNext()
			{
				return !stack.isEmpty();
				
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (stack.isEmpty()) 
				{
					throw new NoSuchElementException();
				}
				BSTreeNode<E> node = stack.pop();
				
				if(node.getRight() != null) 
				{
					stack.push(node.getRight());
				}
				if(node.getLeft() != null) 
				{
					stack.push(node.getLeft());
				}
				
				return node.getElement();
			}
			
		};
	}

	
	/**
	 * Generates Iterator that orders the elements with root element last
	 * Precondition: tree exists
	 * Postcondition: elements ordered with root last
	 * @return iterator that orders elements with root last.
	 */
	@Override
	public Iterator<E> postorderIterator()
	{
		Stack <BSTreeNode<E>> stack = new Stack<>();
		Stack<E> output = new Stack<>();
		if (root != null) 
		{
			stack.push(root);
		}
		
		while (!stack.isEmpty()) 
		{
			BSTreeNode<E> node = stack.pop();
			output.push(node.getElement());
			if (node.getLeft() != null) 
			{
				stack.push(node.getLeft());
			}
			if (node.getRight() != null) 
			{
				stack.push(node.getRight());
			}
		}
		return new Iterator<E>() 
		{

			@Override
			public boolean hasNext()
			{
				
				return !output.isEmpty();
			}

			@Override
			public E next() throws NoSuchElementException
			{
				if (output.isEmpty()) 
				{
					throw new NoSuchElementException();
				}
				return output.pop();
			}
			
		};
	}

}
