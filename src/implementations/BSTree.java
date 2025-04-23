package implementations;

import java.io.Serializable;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable
{
	private static final long serialVersionUID = 1L;
	private BSTreeNode<E> root;
	private int size;
	
	public BSTree() 
	{
		root = null;
		size = 0;
	}
	
	
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException
	{
		if (isEmpty()) 
		{
			throw new NullPointerException("The tree is empty");
		}
		return root;
	}

	@Override
	public int getHeight()
	{
		return getHeight(root);
	}
	
	private int getHeight(BSTreeNode<E> node) 
	{
		if(node == null) 
		{
			return -1;
		}
		
		return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
	}

	@Override
	public int size()
	{
		return size;
	}

	@Override
	public boolean isEmpty()
	{
		return size == 0;
	}

	@Override
	public void clear()
	{
		root = null;
		size = 0;
		
	}

	@Override
	public boolean contains(E entry) throws NullPointerException
	{
		if (entry == null) 
		{
			throw new NullPointerException("Cannot search for null");
		}
		return search(entry) != null;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException
	{
		if (entry == null) 
		{
			throw new NullPointerException("Cannot search fo null");
		}
		return search(root, entry);
	}
	
	
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

	@Override
	public Iterator<E> inorderIterator()
	{
		
		return null;
	}

	@Override
	public Iterator<E> preorderIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> postorderIterator()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
