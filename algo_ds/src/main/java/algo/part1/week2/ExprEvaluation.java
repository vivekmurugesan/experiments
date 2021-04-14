package algo.part1.week2;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Implementation of Dijkstra expression 
 * evaluation algorithm.
 * @author vivek
 *
 */
public class ExprEvaluation {

	public static void main(String[] args) {
		
		ExprEvaluation eval = new ExprEvaluation();
		String expr1 = "((1+2)*(5-2))";
		int result = eval.parseAndEval(expr1);
		
		System.out.printf("Expr: %s = %d\n", expr1, result);
		
		expr1 = "((10+20)*(50-20))";
		result = eval.parseAndEval(expr1);
		
		System.out.printf("Expr: %s = %d\n", expr1, result);
		
		expr1 = "((10+20)*(50-20)*(5+5))";
		result = eval.parseAndEval(expr1);
		
		System.out.printf("Expr: %s = %d\n", expr1, result);
	}
	
	/**
	 * 1. Iterate through the expression.
	 * 2. Ignore ( encountered.
	 * 3. Any operators (+, -, *, /) push into operator stack.
	 * 4. Any numbers encountered push into operand stack.
	 * 5. Any ) encountered pop the top operator and apply with the top 2
	 * operands and push the result back into the operator stack.
	 * @param expr
	 * @return
	 */
	public int parseAndEval(String expr) {
		Stack<Integer> operands = new Stack<>();
		Stack<Character> operators = new Stack<>();
		
		int n = expr.length();
		Set<Character> opers = new HashSet<>();
		opers.add('+');opers.add('-');opers.add('*');opers.add('/');
		
		//StringBuilder builder = null;
		int operand = -1;
		for(int i=0;i<n;i++) {
			char ch = expr.charAt(i);
			if(ch == '(')
				continue;
			if(ch >= '0' && ch <= '9') {
				if(operand<0)
					operand = 0;
				operand = (operand*10) + (ch-'0');
			}else if(opers.contains(ch)) {
				if(operand>=0) {
					operands.push(operand);
					operand=-1;
				}
				
				operators.push(ch);
				
			}else if(ch == ')') {
				if(operand>=0) {
					operands.push(operand);
					operand=-1;
				}
				evaluate(operands, operators);
				
			}
		}
		
		return operands.pop();
	}
	
	public void evaluate(Stack<Integer> operands, Stack<Character> operators) {
		char operator = operators.pop();
		int oper2 = operands.pop();
		int oper1 = operands.pop();
		
		switch(operator) {
		case '+':
			operands.push(oper1+oper2);
			break;
		case '-':
			operands.push(oper1-oper2);
			break;
		case '*':
			operands.push(oper1*oper2);
			break;
		case '/':
			operands.push(oper1/oper2);
			break;
		default:
			break;
		}
	}

}
