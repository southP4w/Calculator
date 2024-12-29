package com.southpaw.calculator.model;

public class LinkedList<T>
{
	private static class Node<T> {
		private T data;
		private Node<T> next;

		Node(T data) {
			this.data = data;
		}
	}

	private Node<T> head, tail;
	private int size;

//	public LinkedList() {
//		head = null;
//		tail = null;
//		size = 0;
//	}

	public void addFirst(T dataBeingAdded) {
		if (isEmpty())
			addToEmptyList(dataBeingAdded);
		else {
			Node<T> nodeBeingAdded = new Node<T>(dataBeingAdded);
			nodeBeingAdded.next = head;
			head = nodeBeingAdded;
		}
		size++;
	}

	public void addLast(T dataBeingAdded) {
		if (isEmpty())
			addToEmptyList(dataBeingAdded);
		else {
			Node<T> nodeBeingAdded = new Node<>(dataBeingAdded);
			tail.next = nodeBeingAdded;
			tail = nodeBeingAdded;
		}
		size++;
	}

	public T removeFirst() {
		if (isEmpty())
			throw new RuntimeException("Cannot remove from an empty list");

		Node<T> temp = head;
		head = head.next;
		if (head == null)
			tail = null;
		size--;

		return temp.data;
	}

	public T getFirst() {
		if (isEmpty())
			throw new RuntimeException("List is empty");

		return head.data;
	}

	private void addToEmptyList(T dataBeingAdded) {
		Node<T> nodeBeingAdded = new Node<>(dataBeingAdded);
		head = nodeBeingAdded;
		tail = nodeBeingAdded;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {
		Node<T> currentNode = head;

		StringBuilder sb = new StringBuilder("[");
		while (currentNode != null) {
			sb.append(currentNode.data);
			if (currentNode.next != null)
				sb.append(", ");
			currentNode = currentNode.next;
		}
		sb.append("]");

		return sb.toString();
	}
}