package com.southpaw.calculator.model;

public class Stack<E>
{
	private E[] data;
	private int top, capacity;

	@SuppressWarnings("unchecked")
	public Stack() {
		capacity = 50;
		data = (E[]) new Object[capacity];
		top = -1;
	}

	@SuppressWarnings("unchecked")
	public Stack(int capacity) {
		if (capacity < 1)
			throw new IllegalArgumentException();
		this.capacity = capacity;
		data = (E[]) new Object[capacity];
		top = -1;
	}

	/**
	 * push()
	 * @return true if the element can be added, false otherwise
	 */
	public void push(E elementBeingAdded) {
		if (isFull())
			throw new IllegalStateException();
		data[++top] = elementBeingAdded;
	}

	/**
	 * pop()
	 * @return AND remove the top element from this stack
	 */
	public E pop() {
		if (isEmpty())
			throw new IllegalStateException();
		E elementBeingRemoved = data[top];
		data[top--] = null;

		return elementBeingRemoved;
	}

	/**
	 * peek()
	 * @return but do not remove the top element from this stack
	 */
	public E peek() {
		if (isEmpty())
			throw new IllegalStateException();
		return data[top];
	}

	public boolean isFull() {
		return top == capacity - 1;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public int getSize() {
		return top + 1;
	}
}