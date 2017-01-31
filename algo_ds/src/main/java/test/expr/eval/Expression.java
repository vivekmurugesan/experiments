package test.expr.eval;

import java.util.LinkedList;

/**
 * 
 * @author vivek
 *
 */
public class Expression {
	public enum Operator {
		add, subtract, divide, multiply, open_paranth, close_paranth, unsupported
	}
	
	public double perform(Operator op, double o1, double o2){
		double result=Integer.MIN_VALUE;
		switch(op){
		case add:
			result = o1+o2;
			break;
		case subtract:
			result = o1 - o2;
			break;
		case multiply:
			result = o1 * o2;
			break;
		case divide:
			result = o1/o2;
			break;
		}
		
		return result;
	}
	
	public double eval(String expr){
		if(expr == null || expr.length() <= 0)
			return Integer.MIN_VALUE;
		
		LinkedList<Double> operandStack = new LinkedList<>();
		LinkedList<Operator> operatorStack = new LinkedList<>();
		
		parseAndEval(expr, operandStack, operatorStack);
		
		System.out.println(operandStack);
		System.out.println(operatorStack);
		
		return operandStack.removeFirst();
	}
	
	/**
	 * Implementation of Dikjstra's two stack algorithm.
	 * 1. Ignore any open parenthesis.
	 * 2. Push any operator into operator stack.
	 * 3. Push any operand into operand stack.
	 * 4. When encountering a close parenthesis pop out the top 
	 * two operands and pop out the operator, perform the operation
	 * and push the result back into the operand stack.
	 * 5. At the end of evaluation the value at the operand stack is the
	 * result.
	 * 
	 * @param expr
	 * @return
	 */
	public void parseAndEval(String expr, LinkedList<Double> operandStack,
			LinkedList<Operator> operatorStack){
		String[] tokens = expr.split(" ");
		
		for(String token : tokens){
			if(token.length() == 1){
				if(isSingleDigit(token.charAt(0)))
					operandStack.addFirst(getSingleDigit(token.charAt(0)));
				else{ 
					Operator oper = parseOperator(token.charAt(0));
					if(!oper.equals(Operator.open_paranth) && 
							!oper.equals(Operator.close_paranth))
						operatorStack.addFirst(oper);
					else if(oper.equals(Operator.close_paranth)){
						evalCurrentOper(operandStack, operatorStack);
					}
				}
			}else
				operandStack.addFirst(Double.parseDouble(token));
		}
	}
	
	public void evalCurrentOper(LinkedList<Double> operandStack,
			LinkedList<Operator> operatorStack){
		double o2 = operandStack.removeFirst();
		double o1 = operandStack.removeFirst();
		Operator oper = operatorStack.removeFirst();
		operandStack.addFirst(perform(oper, o1, o2));
	}
	
	public boolean isSingleDigit(char token){
		if(token >= '0' && token <= '9')
			return true;
		else 
			return false;
	}
	
	public double getSingleDigit(char token){
		return (double)token - '0';
	}
	
	public Operator parseOperator(char token){
		Operator result = Operator.unsupported;
		switch(token){
		case '(':
			result = Operator.open_paranth;
			break;
		case ')':
			result = Operator.close_paranth;
			break;
		case '+':
			result = Operator.add;
			break;
		case '-':
			result = Operator.subtract;
			break;
		case '*':
			result = Operator.multiply;
			break;
		case '/':
			result = Operator.divide;
			break;
		}
		return result;
	}
	
	public static void main(String[] args){
		String expr = "( ( ( ( 3 + 2 ) * ( 6 / 2 ) ) * 10 ) - 3 )";
		String expr1 = "( ( 3 + 2 ) * ( 6 / 2 ) )";
		
		Expression evaluator = new Expression();
		double result = evaluator.eval(expr);
		
		System.out.printf("Result of expr: %s \t is:%f\n", expr, result );
	}
}
