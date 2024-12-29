package com.southpaw.calculator.model;

public class Queue<T>
{
	private LinkedList<T> linkedList = new LinkedList<>();

	public void enqueue(T dataBeingAdded) {
		linkedList.addLast(dataBeingAdded);
	}

	public T dequeue() {
		return linkedList.removeFirst();
	}

	public boolean isEmpty() {
		return linkedList.isEmpty();
	}

	@Override
	public String toString() {
		return linkedList.toString();
	}
}