import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int size;
	private int capacity;
	private Item[] queue;

	/**
	 * O(1) time complexity
	 * Initialize randomized queue 
	 */
	public RandomizedQueue() {
		queue = (Item[]) new Object[1];
		size = 0;
		capacity = 1;
	}
		
	/**
	 * 
	 * @return is randomized queue is empty or not
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * 
	 * @return the item inside randomized queue
	 */
	public int size() {
		return size;
	}
	/**
	 * if the queue is full, call expand() method to expand the queue in double size
	 * assign new item to queue, and add 1 to size
	 * @param item
	 */
	public void enqueue(Item item) {
		if (item == null)
			throw new IllegalArgumentException("");
		if (size == capacity)
			expand();
		queue[size++] = item;
	}
	/**
	 * use provided api StdRabdom to find a random value between 0 and size-1
	 * when removing item which isn't at the last index, we change to item in x to x+1
	 * then set item at size-1 to null, minus 1 to size
	 * @return
	 */
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("");
		int rd = StdRandom.uniform(size);
		Item item = queue[rd];

		for (int i = rd; i < (size - 1); i++) {
			queue[i] = queue[i + 1];
		}

		queue[size - 1] = null;
		size--;
		if (size == queue.length / 4)
			shrink();
		return item;
	}
	/**
	 * output random item
	 * @return
	 */
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException();
		int rd = StdRandom.uniform(size);
		Item item = queue[rd];
		return item;
	}
	/**
	 * 
	 */
	public Iterator<Item> iterator() {
		return new RQueueIterator();
	}
	private class RQueueIterator implements Iterator<Item> {
		private Item[] copy = (Item[]) new Object[capacity];
		private int copySize = size;

		public RQueueIterator() {
			for (int i = 0; i < capacity; i++) {
				copy[i] = queue[i];
			}
		}

		@Override
		public boolean hasNext() {
			return copySize > 0;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new NoSuchElementException("");
			}
			int rd = StdRandom.uniform(copySize);
			Item item = copy[rd];
			if (rd != copySize - 1)
				copy[rd] = copy[copySize - 1];
			copy[copySize - 1] = null;
			copySize--;
			return item;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	/**
	 * make a copy of original array
	 * assign a new array with double size, then paste the copy into array
	 */
	private void expand() {
		capacity = capacity * 2;
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++) {
			copy[i] = queue[i];
		}
		queue = copy;
		copy = null;
	}
	/**
	 * make a copy of original array
	 * assign a new array with 1/2 size, then paste the copy into array
	 */
	private void shrink() {
		capacity = capacity / 2;
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i <= size / 2; i++) {
			copy[i] = queue[i];
		}
		queue = copy;
		copy = null;

	}

	public static void main(String[] args) {
		;
	}
}
