package org.mule.common.query;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mule.common.query.dsql.grammar.DsqlLexer;
import org.mule.common.query.dsql.grammar.DsqlParser;
import org.mule.common.query.dsql.grammar.DsqlParser.select_return;
import org.mule.common.query.dsql.parser.MuleDsqlParser;
import org.mule.common.query.dsql.parser.exception.DsqlParsingException;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.FieldComparation;

public class DsqlParserTest {

	@Test
	public void testEasyParse() {
		Query query = parse("select * from users where name='alejo'");
		assertThat(query.getFields().size(), is(1));
		assertThat(query.getFields().get(0).getName(), is("*"));
		assertThat(query.getTypes().size(), is(1));
		assertThat(query.getTypes().get(0).getName(), is("users"));
		assertThat(query.getFilterExpression(), is(FieldComparation.class));
		FieldComparation fieldComparation = (FieldComparation) query.getFilterExpression();
		assertThat(fieldComparation.getField().getName(), is("name"));
		assertThat(fieldComparation.getValue().getValue(), is((Object) "alejo"));
		assertThat(fieldComparation.getOperator(), is(EqualsOperator.class));
	}

	@Test
	public void testParse1() {
		Query query = parse("select name, surname from users, addresses where name='alejo' and (apellido='abdala' and address='guatemala 1234') order by name limit 10 offset 200");
	}

	@Test
	public void testParse1b() {
		Query query = parse("select name, surname from users, addresses where (name='alejo' and apellido='abdala') and address='guatemala 1234' order by name desc limit 10 offset 200");
	}

	@Test
	public void testParse2() {
		Query query = parse("select * from users, addresses where name='alejo' and apellido='abdala' or apellido='achaval' and name='mariano' and cp='1234'");
	}

	@Test
	public void testParse3() {
		Query query = parse("select * from users, addresses where name='alejo' and not (age > 25)");
	}

	@Test
	public void testLike() {
		Query query = parse("select * from users, addresses where name like '%alejo%'");
	}

	@Test
	public void testExpression() {
		Query query = parse("SELECT AccountNumber,AccountSource,Active__c FROM Account WHERE AccountNumber = '#[flowVars[\\'name\\']]'");
	}

	@Test
	public void testParse4() {
		Query query = parse("select * from users, addresses where name='alejo' and age <> 25");
	}

	@Test
	public void testParse5() {
		Query query = parse("select * from users, addresses where name='alejo' and (age >= 25 or age <= 40)");
	}

	@Test
	public void testParse6() {
		Query query = parse("SELECT AccountSource,AnnualRevenue FROM Account WHERE ((AnnualRevenue > 22222 AND BillingCity > 123) AND AnnualRevenue >= 222222) ORDER BY Active__c LIMIT 112 OFFSET 222");
	}

	@Test
	public void testParseAscending() {
		Query query = parse("select * from users, addresses where name='alejo' order by name ascending");
	}

	@Test
	public void testParseAscending2() {
		Query query = parse("select * from users, addresses where name='alejo' order by name asc");
	}

	@Test
	public void testParseDescending() {
		Query query = parse("select * from users, addresses where name='alejo' order by name descending");
	}

	@Test
	public void testParseDescending2() {
		Query query = parse("select * from users, addresses where name='alejo' order by name desc");
	}

	@Test
	public void testWithMuleExpression() {
		Query query = parse("select * from users, addresses where name='#[payload.name]' order by name desc");
	}

	@Test
	public void testWithMuleExpression2() {
		Query query = parse("select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
	}



	@Test
	public void testWithMuleExpression3() {
		Query query = parse("select * from users, addresses where name='#[flowVars[\"id\"]]' order by name desc");
	}

	@Test
	public void testWithMuleExpression4() {
		Query query = parse("select * from users, addresses where id > #[flowVars['pepe']] order by name");
	}

	@Test
	public void testWithMuleExpression5() {
		Query query = parse("select * from users, addresses where (id > #[flowVars['pepe']] and id < #[flowVars.get('id')]) order by name");
	}

	@Test
	public void testWithMuleExpression6() {
		Query query = parse("select * from users, addresses where id > #[flowVars['pepe']] and id < #[flowVars.get('id')] order by name");
	}

	@Test
	public void testWithMuleExpression7() {
		Query query = parse("select * from users, addresses where id > #[flowVars['pepe']] and id < #[[flowVars.get('[id')]] order by name");
	}

	@Test
	public void testWithMuleExpression9() {
		Query query = parse("select * from users, addresses where name='#[flowVars[\\'id\\']]' order by name desc");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail() {
		Query query = parse("select * from users, addresses where name='alejo' and ");
	}

	@Ignore
	@Test(expected = DsqlParsingException.class)
	public void testFail2() {
		Query query = parse("dsql:select from");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail3() {
		Query query = parse("*");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail4() {
		Query query = parse("SELECT *");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail5() {
		Query query = parse("select * from users, addresses where ");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailSelect() {
		Query query = parse("selecct users, addresses from Account where name = 123");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom() {
		Query query = parse("select users, addresses frrom Account where name = 123");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom2() {
		Query query = parse("select users, addresses *");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom3() {
		Query query = parse("select users, addresses ffrom");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailMissingFrom() {
		Query query = parse("select users, addresses where");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailWhere2() {
		Query query = parse("select users, addresses from Account where *");
	}

	@Test(expected = DsqlParsingException.class)
	@Ignore
	public void testWithMuleExpressionShouldFail() {
		Query query = parse("select * from users, addresses where name='#[flowVars[\'id\']]' order by name desc");
	}

	@Test(expected = DsqlParsingException.class)
	@Ignore
	public void testFailWhere() {
		Query query = parse("select users, addresses from Account whseree name = 123");
	}

	@Test
	public void testFieldsWithSpaces() {
		Query query = parse("select 'Field with spaces' from Account");
		Assert.assertThat(query.getFields().size(), is(1));

		Assert.assertThat(query.getFields().get(0).getName(), is("Field with spaces"));
	}

	@Test
	public void testNormalFieldsMixedWithFieldsWithSpaces() {
		Query query = parse("select NormalField,'Field with spaces',Underscored_Field from Account");
		Assert.assertThat(query.getFields().size(), is(3));

		Assert.assertThat(query.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(query.getFields().get(1).getName(), is("Field with spaces"));
		Assert.assertThat(query.getFields().get(2).getName(), is("Underscored_Field"));
	}

	@Test
	public void testNormalFieldWithQuotes() {
		Query query = parse("select 'NormalField' from Account");
		Assert.assertThat(query.getFields().size(), is(1));

		Assert.assertThat(query.getFields().get(0).getName(), is("NormalField"));
	}

	@Test
	public void testNormalFieldWithQuotesMixedWithFieldsWithSpaces() {
		Query query = parse("select 'NormalField','Field With Spaces' from Account");
		Assert.assertThat(query.getFields().size(), is(2));

		Assert.assertThat(query.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(query.getFields().get(1).getName(), is("Field With Spaces"));
	}

	public Query parse(final String string) {
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
			Query parse = parser.parse(string);
			parse.accept(visitor);
			System.out.println(visitor.dsqlQuery());
			return parse;
		} catch (RecognitionException e) {
			throw new DsqlParsingException(e);
		}
	}

	private void printTree(CommonTree tree) {
		printTree(tree, 0);
	}

	@SuppressWarnings("unchecked")
	private void printTree(CommonTree tree, int level) {
		List<CommonTree> children = (List<CommonTree>) tree.getChildren();
		System.out.println(tree.getText() + " - Type=" + tree.getType());
		if (children != null) {
			level += 2;
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
