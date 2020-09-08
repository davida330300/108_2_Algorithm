import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	/**
	 * A deque containing n items must use at most 48n + 192 bytes of memory, not
	 * including the memory for the items themselves. Each deque operation
	 * (including construction) must take constant time. Each iterator operation
	 * (including construction) must take constant time.
	 */
	private int size = 0;
	private Node head_ptr = new Node();
	private Node tail_ptr = new Node();

	private class Node {	// fundamental data structure of linked list
		Item data;
		Node next;
		Node prev;

		Node() {
		};

		Node(Item data) {
			this.data = data;
		}
	}

	/**
	 * O(1) construct an empty deque
	 * constructor with only head pointer and tail pointer
	 */
	public Deque() {
		head_ptr.prev = null;
		head_ptr.next = tail_ptr;

		tail_ptr.prev = head_ptr;
		tail_ptr.next = null;

		size = 0;
	}

	/**
	 * O(1)
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * O(1) return the number of items on the deque
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * O(1) add the item to the front
	 * we have to remeber the old headnode, insert the new headnode between head pointer and old headnode
	 * if the next node of head pointer is tail pointer, which shows that the dueue is empty
	 * this is a doubly linked list, make sure that all nodes next and prev are assigned to another node
	 * @param item
	 */
	public void addFirst(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("addFirst without item\n");
		}

		Node newNode = new Node(item);

		if (size == 0) {
			newNode.prev = head_ptr;
			newNode.next = tail_ptr;

			head_ptr.next = newNode;
			tail_ptr.prev = newNode;

			size = 1;
		} else {
			Node oldHead = head_ptr.next;
			newNode.prev = head_ptr;
			newNode.next = oldHead;

			head_ptr.next = newNode;
			oldHead.prev = newNode;
			size++;
		}
	}

	/**
	 * O(1) add the item to the back
	 * we have to remeber the old tailnode, insert the new tailnode between head pointer and old tailnode
	 * @param item
	 */
	public void addLast(Item item) {
		if (item == null) {
			throw new IllegalArgumentException("addLast without item\n");
		}

		Node newNode = new Node(item);

		if (size == 0) {
			newNode.next = tail_ptr;
			newNode.prev = head_ptr;

			head_ptr.next = newNode;
			tail_ptr.prev = newNode;

			size = 1;
		} else {
			Node oldTail = tail_ptr.prev;
			newNode.prev = oldTail;
			newNode.next = tail_ptr;

			oldTail.next = newNode;
			tail_ptr.prev = newNode;

			size++;
		}
	}

	//
	/**
	 * O(1) remove and return the item from the front
	 * due to java garbage collector, we don't have to delete an object to free the memory space like in C
	 * the new headnode is the next after the next of head pointer
	 * we first reassign the next and prev of new headnode and head pointer
	 * then disconnect the connection of old headnode
	 * @return
	 */
	public Item removeFirst() {
		if (isEmpty()) {
			throw new NoSuchElementException("the deque is empty\n");
		} else {
			Node oldHead = head_ptr.next;
			Node newHead = head_ptr.next.next;

			head_ptr.next = newHead;
			newHead.prev = head_ptr;

			oldHead.next = null;
			oldHead.prev = null;

			size--;

			return oldHead.data;
		}

	}

	//
	/**
	 * O(1) remove and return the item from the back
	 * 
	 * @return
	 */
	public Item removeLast() {
		if (isEmpty()) {
			throw new NoSuchElementException("the deque is empty\n");
		} else {
			Node oldTail = tail_ptr.prev;
			Node newTail = tail_ptr.prev.prev;

			tail_ptr.prev = newTail;
			newTail.next = tail_ptr;

			oldTail.next = null;
			oldTail.prev = null;

			size--;

			return oldTail.data;
		}

	}

	//
	/**
	 * O(1) return an iterator over items in order from front to back from textbook
	 * follow the instruction on textbook p.155
	 */
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = head_ptr.next;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (current != tail_ptr)
				return true;
			else
				return false;
		}

		@Override
		public Item next() {
			// TODO Auto-generated method stub
			if (hasNext()) {
				Item item = current.data;
				current = current.next;
				return item;
			} else
				throw new NoSuchElementException();

		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		;
	}

}