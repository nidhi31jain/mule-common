package org.mule.common.metadata.test;

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

public class DsqlParserTest {
	
	@Test
	public void testParse1() {
		parse("select name, surname from users, addresses where name='alejo' and apellido='abdala' order by name limit 10 offset 200");
	}
	
	@Test
	public void testParse2() {
		parse("select * from users, addresses where name='alejo' and (apellido='abdala' or (apellido='achaval' and name='mariano')) and (cp='1234')");
	}

	@Test
	public void testParse3() {
		parse("select * from users, addresses where name='alejo' and not age > 25");
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
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void printTree(CommonTree tree) {
		printTree(tree, 0);
	}
	
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
