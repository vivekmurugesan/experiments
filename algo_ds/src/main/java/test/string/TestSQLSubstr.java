package test.string;

public class TestSQLSubstr {

	public static void main(String[] args) {
		String testSql = "SELECT col_a, col_b from test_table where a>100";
		
		int selectStart = testSql.indexOf("SELECT");
		int selectEnd = selectStart + "SELECT".length();
		
		int fromStart = testSql.indexOf("from");
		int fromEnd = fromStart + "from".length();
		
		int whereStart = testSql.indexOf("where");
		int whereEnd = whereStart + "where".length();
		
		String projection = testSql.substring(selectEnd, fromStart);
		
		String tableName = testSql.substring(fromEnd, whereStart);
		
		String condition = testSql.substring(whereEnd);
		
		System.out.println("Projection:" + projection);
		System.out.println("TaleName:" + tableName);
		System.out.println("Condition:" + condition);
	}

}
