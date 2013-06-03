package org.mule.common.query;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.junit.Test;
import org.mule.common.query.dsql.grammar.DsqlLexer;
import org.mule.common.query.dsql.grammar.DsqlParser;
import org.mule.common.query.dsql.grammar.DsqlParser.select_return;
import org.mule.common.query.dsql.parser.MuleDsqlParser;

public class DsqlParserTest {
	
	@Test
	public void testEasyParse() {
		parse("select * from users where name='alejo'");
	}

	@Test
	public void testParse1() {
		parse("select name, surname from users, addresses where name='alejo' and (apellido='abdala' and address='guatemala 1234') order by name limit 10 offset 200");
	}
	
	@Test
	public void testParse1b() {
		parse("select name, surname from users, addresses where (name='alejo' and apellido='abdala') and address='guatemala 1234' order by name limit 10 offset 200");
	}
	
	@Test
	public void testParse2() {
		parse("select * from users, addresses where name='alejo' and apellido='abdala' or apellido='achaval' and name='mariano' and cp='1234'");
	}

	@Test
	public void testParse3() {
		parse("select * from users, addresses where name='alejo' and not (age > 25)");
	}
	
	public void parse(final String string) {
		CharStream antlrStringStream = new ANTLRStringStream(string);
		DsqlLexer dsqlLexer = new DsqlLexer(antlrStringStream);
		CommonTokenStream dsqlTokens = new CommonTokenStream();
		dsqlTokens.setTokenSource(dsqlLexer);
		
		DsqlParser dsqlParser = new DsqlParser(dsqlTokens);
		try {
			select_return select = dsqlParser.select();
			CommonTree tree = (CommonTree) select.getTree();
			printTree(tree);
			
			MuleDsqlParser parser = new MuleDsqlParser();
	        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
	        parser.parse(string).accept(visitor);
	        System.out.println(visitor.dsqlQuery());
			
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void printTree(CommonTree tree) {
		printTree(tree, 0);
	}
	
	@SuppressWarnings("unchecked")
	private void printTree(CommonTree tree, int level) {
		List<CommonTree> children = (List<CommonTree>)tree.getChildren();
		System.out.println(tree.getText() + " - Type=" + tree.getType());
		if (children != null) {
			level+=2;
			for (CommonTree t : children) {
				if (t != null) {
					printLevel(level);
					printTree(t, level);
				}
			}
		}
	}

	private void printLevel(int level) {
		for (int i = 0; i < level; i++) {
			System.out.print("-");
		}
		System.out.print("-> ");
		
	}

}
