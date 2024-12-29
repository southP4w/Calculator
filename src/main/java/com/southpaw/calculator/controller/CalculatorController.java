package com.southpaw.calculator.controller;

import com.southpaw.calculator.model.Queue;
import com.southpaw.calculator.model.Stack;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CalculatorController
{
	@FXML
	private Button buttonClearAll, buttonClear, buttonBackspace;
	@FXML
	private Button buttonLeftParen, buttonRightParen, buttonAdd, buttonSubtract, buttonMultiply, buttonDivide, buttonModulo, buttonEquals;
	@FXML
	private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
		buttonPeriod;
	@FXML
	private TextField textFieldHistory, textFieldInput;

	private Queue<String> tokenizeExpression(String expr) {
		Queue<String> queue = new Queue<>();
		StringBuilder numberBuilder = new StringBuilder();

		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if (Character.isDigit(c) || c == '.')
				numberBuilder.append(c);
			else {
				if (!numberBuilder.isEmpty()) {
					queue.enqueue(numberBuilder.toString());
					numberBuilder.setLength(0);
				}
				if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/')
					queue.enqueue(String.valueOf(c));
			}
		}
		if (!numberBuilder.isEmpty())
			queue.enqueue(numberBuilder.toString());

		return queue;
	}

	private Queue<String> infixToPostfix(Queue<String> infixQueue) {
		Queue<String> outputQueue = new Queue<>();
		Stack<String> operatorStack = new Stack<>();

		while (!infixQueue.isEmpty()) {
			String token = infixQueue.dequeue();
			if (isNumber(token))
				outputQueue.enqueue(token);
			else if (token.equals("("))
				operatorStack.push(token);
			else if (token.equals(")")) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("("))
					outputQueue.enqueue(operatorStack.pop());
				if (operatorStack.isEmpty())
					throw new RuntimeException("Mismatched parentheses: no matching '('");
				operatorStack.pop();
			}
			else {
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") && precedenceOf(operatorStack.peek()) >= precedenceOf(token))
					outputQueue.enqueue(operatorStack.pop());
				operatorStack.push(token);
			}
		}
		while (!operatorStack.isEmpty()) {
			String top = operatorStack.pop();
			if (top.equals("(") | top.equals(")"))
				throw new RuntimeException("Mismatched parentheses");
			outputQueue.enqueue(top);
		}

		return outputQueue;
	}

	private double evaluatePostfix(Queue<String> postfixQueue) {
		Stack<Double> stack = new Stack<>();

		while (!postfixQueue.isEmpty()) {
			String token = postfixQueue.dequeue();
			if (isNumber(token))
				stack.push(Double.parseDouble(token));
			else {
				double b = stack.pop();
				double a = stack.pop();
				double result = applyOperator(a, b, token);
				stack.push(result);
			}
		}

		return stack.pop();
	}

	private double applyOperator(double a, double b, String op) {
		return switch (op) {
			case "+" -> a + b;
			case "-" -> a - b;
			case "*" -> a*b;
			case "/" -> {
				if (b == 0)
					throw new ArithmeticException("Error: Division by Zero");
				yield a/b;
			}
			default -> throw new IllegalArgumentException("Unknown operator: " + op);
		};
	}

	private int precedenceOf(String op) {
		return switch (op) {
			case "*", "/" -> 2;
			case "+", "-" -> 1;
			default -> 0;
		};
	}

	private boolean isNumber(String token) {
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	@FXML
	void button0Clicked(MouseEvent event) {
		if (textFieldInput.getText().isEmpty())
			return;
		textFieldInput.setText(textFieldInput.getText() + '0');
	}

	@FXML
	void button1Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '1');
	}

	@FXML
	void button2Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '2');
	}

	@FXML
	void button3Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '3');
	}

	@FXML
	void button4Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '4');
	}

	@FXML
	void button5Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '5');
	}

	@FXML
	void button6Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '6');
	}

	@FXML
	void button7Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '7');
	}

	@FXML
	void button8Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '8');
	}

	@FXML
	void button9Clicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '9');
	}

	@FXML
	void buttonLeftParenClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '(');
	}

	@FXML
	void buttonRightParenClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + ')');
	}

	@FXML
	void buttonAddClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '+');
	}

	@FXML
	void buttonSubtractClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '-');
	}

	@FXML
	void buttonMultiplyClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '*');
	}

	@FXML
	void buttonDivideClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '/');
	}

	@FXML
	void buttonPeriodClicked(MouseEvent event) {
		textFieldInput.setText(textFieldInput.getText() + '.');
	}

	@FXML
	void buttonBackspaceClicked(MouseEvent event) {

	}

	@FXML
	void buttonClearAllClicked(MouseEvent event) {

	}

	@FXML
	void buttonClearClicked(MouseEvent event) {

	}

	@FXML
	void buttonEqualsClicked(MouseEvent event) {
		try {
			String expression = textFieldInput.getText();
			if (expression.isEmpty())
				return;

			Queue<String> infixQueue = tokenizeExpression(expression);
			Queue<String> postfixQueue = infixToPostfix(infixQueue);
			double result = evaluatePostfix(postfixQueue);
			textFieldInput.setText(String.valueOf(result));
			textFieldHistory.setText(expression + " = " + result);
			textFieldInput.clear();
		} catch (Exception exception) {
			System.out.println("Error: " + exception.getMessage());
		}
	}

	@FXML
	void buttonEqualsMouseEntered(MouseEvent event) {

	}

	@FXML
	void buttonEqualsMouseExited(MouseEvent event) {

	}

	@FXML
	void buttonModuloClicked(MouseEvent event) {

	}
}