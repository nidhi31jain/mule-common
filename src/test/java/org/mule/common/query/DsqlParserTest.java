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
import org.mule.common.query.expression.*;

public class DsqlParserTest {

	@Test
	public void testEasyParse() {
		QueryModel queryModel = parse("select * from users where name='alejo'");
		assertThat(queryModel.getFields().size(), is(1));
		assertThat(queryModel.getFields().get(0).getName(), is("*"));
		assertThat(queryModel.getTypes().size(), is(1));
		assertThat(queryModel.getTypes().get(0).getName(), is("users"));
		Expression filterExpression = queryModel.getFilterExpression();
		assertFieldComparation(filterExpression, EqualsOperator.class,"name", "alejo");
	}

    private void assertFieldComparation(Expression filterExpression, Class<? extends Operator> operatorClass, String fieldName, Object value) {
        assertThat(filterExpression, is(FieldComparation.class));
		FieldComparation fieldComparation = (FieldComparation) filterExpression;
		assertThat(fieldComparation.getField().getName(), is(fieldName));
		assertThat(fieldComparation.getValue().getValue(), is((Object) value));
		assertThat(fieldComparation.getOperator(), is(operatorClass));
	}

	@Test
	public void testParse1() {
		QueryModel queryModel = parse("select name, surname from users, addresses where name='alejo' and (apellido='abdala' and address='guatemala 1234') order by name limit 10 offset 200");

        assertThat(queryModel.getFields().size(), is(2));
        assertThat(queryModel.getFields().get(0).getName(), is("name"));
        assertThat(queryModel.getFields().get(1).getName(), is("surname"));

		assertThat(queryModel.getTypes().size(), is(2));
		assertThat(queryModel.getTypes().get(0).getName(), is("users"));
		assertThat(queryModel.getTypes().get(1).getName(), is("addresses"));

        assertThat(queryModel.getOrderByFields().size(), is(1));
        assertThat(queryModel.getOrderByFields().get(0).getName(), is("name"));
		
		assertThat(queryModel.getFilterExpression(), is(BinaryLogicalExpression.class));
		BinaryLogicalExpression andExpression = (BinaryLogicalExpression) queryModel.getFilterExpression();
		assertFieldComparation(andExpression.getLeft(), EqualsOperator.class, "name", "alejo");
		assertThat(andExpression.getRight(), is(BinaryLogicalExpression.class));
		
		BinaryLogicalExpression innerAnd = (BinaryLogicalExpression) andExpression.getRight();
		assertFieldComparation(innerAnd.getLeft(), EqualsOperator.class, "apellido", "abdala");
		assertFieldComparation(innerAnd.getRight(), EqualsOperator.class, "address", "guatemala 1234");
		
	}

	@Test
	public void testParse1b() {
		QueryModel queryModel = parse("select name, surname from users, addresses where (name='alejo' and apellido='abdala') and address='guatemala 1234' order by name desc limit 10 offset 200");
	}

	@Test
	public void testParse2() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' and apellido='abdala' or apellido='achaval' and name='mariano' and cp='1234'");
	}

	@Test
	public void testParse3() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' and not (age > 25)");
	}

	@Test
	public void testLike() {
		QueryModel queryModel = parse("select * from users, addresses where name like '%alejo%'");
	}

	@Test
	public void testExpression() {
		QueryModel queryModel = parse("SELECT AccountNumber,AccountSource,Active__c FROM Account WHERE AccountNumber = '#[flowVars[\\'name\\']]'");
	}

	@Test
	public void testParse4() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' and age <> 25");
	}

	@Test
	public void testParse5() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' and (age >= 25 or age <= 40)");
	}

	@Test
	public void testParse6() {
		QueryModel queryModel = parse("SELECT AccountSource,AnnualRevenue FROM Account WHERE ((AnnualRevenue > 22222 AND BillingCity > 123) AND AnnualRevenue >= 222222) ORDER BY Active__c LIMIT 112 OFFSET 222");
	}

	@Test
	public void testParseAscending() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' order by name ascending");
	}

	@Test
	public void testParseAscending2() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' order by name asc");
	}

	@Test
	public void testParseDescending() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' order by name descending");
	}

	@Test
	public void testParseDescending2() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' order by name desc");
	}

	@Test
	public void testWithMuleExpression() {
		QueryModel queryModel = parse("select * from users, addresses where name='#[payload.name]' order by name desc");
	}

	@Test
	public void testWithMuleExpression2() {
		QueryModel queryModel = parse("select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
	}



	@Test
	public void testWithMuleExpression3() {
		QueryModel queryModel = parse("select * from users, addresses where name='#[flowVars[\"id\"]]' order by name desc");
	}

	@Test
	public void testWithMuleExpression4() {
		QueryModel queryModel = parse("select * from users, addresses where id > #[flowVars['pepe']] order by name");
	}

	@Test
	public void testWithMuleExpression5() {
		QueryModel queryModel = parse("select * from users, addresses where (id > #[flowVars['pepe']] and id < #[flowVars.get('id')]) order by name");
	}

	@Test
	public void testWithMuleExpression6() {
		QueryModel queryModel = parse("select * from users, addresses where id > #[flowVars['pepe']] and id < #[flowVars.get('id')] order by name");
	}

	@Test
	public void testWithMuleExpression7() {
		QueryModel queryModel = parse("select * from users, addresses where id > #[flowVars['pepe']] and id < #[[flowVars.get('[id')]] order by name");
	}

	@Test
	public void testWithMuleExpression9() {
		QueryModel queryModel = parse("select * from users, addresses where name='#[flowVars[\\'id\\']]' order by name desc");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail() {
		QueryModel queryModel = parse("select * from users, addresses where name='alejo' and ");
	}

	@Ignore
	@Test(expected = DsqlParsingException.class)
	public void testFail2() {
		QueryModel queryModel = parse("dsql:select from");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail3() {
		QueryModel queryModel = parse("*");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail4() {
		QueryModel queryModel = parse("SELECT *");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFail5() {
		QueryModel queryModel = parse("select * from users, addresses where ");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailSelect() {
		QueryModel queryModel = parse("selecct users, addresses from Account where name = 123");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom() {
		QueryModel queryModel = parse("select users, addresses frrom Account where name = 123");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom2() {
		QueryModel queryModel = parse("select users, addresses *");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailFrom3() {
		QueryModel queryModel = parse("select users, addresses ffrom");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailMissingFrom() {
		QueryModel queryModel = parse("select users, addresses where");
	}

	@Test(expected = DsqlParsingException.class)
	public void testFailWhere2() {
		QueryModel queryModel = parse("select users, addresses from Account where *");
	}

	@Test(expected = DsqlParsingException.class)
	@Ignore
	public void testWithMuleExpressionShouldFail() {
		QueryModel queryModel = parse("select * from users, addresses where name='#[flowVars[\'id\']]' order by name desc");
	}

	@Test(expected = DsqlParsingException.class)
	@Ignore
	public void testFailWhere() {
		QueryModel queryModel = parse("select users, addresses from Account whseree name = 123");
	}

	@Test
	public void testFieldsWithSpaces() {
		QueryModel queryModel = parse("select 'Field with spaces' from Account");
		Assert.assertThat(queryModel.getFields().size(), is(1));

		Assert.assertThat(queryModel.getFields().get(0).getName(), is("Field with spaces"));
	}

	@Test
	public void testNormalFieldsMixedWithFieldsWithSpaces() {
		QueryModel queryModel = parse("select NormalField,'Field with spaces',Underscored_Field from Account");
		Assert.assertThat(queryModel.getFields().size(), is(3));

		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(queryModel.getFields().get(1).getName(), is("Field with spaces"));
		Assert.assertThat(queryModel.getFields().get(2).getName(), is("Underscored_Field"));
	}

	@Test
	public void testNormalFieldWithQuotes() {
		QueryModel queryModel = parse("select 'NormalField' from Account");
		Assert.assertThat(queryModel.getFields().size(), is(1));

		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
	}

	@Test
	public void testNormalFieldWithQuotesMixedWithFieldsWithSpaces() {
		QueryModel queryModel = parse("select 'NormalField','Field With Spaces' from Account");
		Assert.assertThat(queryModel.getFields().size(), is(2));

		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(queryModel.getFields().get(1).getName(), is("Field With Spaces"));
	}

	@Test
	public void testTypeWithSpacesInFrom() {
		QueryModel queryModel = parse("select NormalField from 'Account Reps'");
		Assert.assertThat(queryModel.getFields().size(), is(1));
		Assert.assertThat(queryModel.getTypes().size(), is(1));
		
		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(queryModel.getTypes().get(0).getName(), is("Account Reps"));
	}
	
	@Test
	public void testTypeWithSpacesMixedWithNormalInFrom() {
		QueryModel queryModel = parse("select NormalField from NormalType,'Account Reps'");
		Assert.assertThat(queryModel.getFields().size(), is(1));
		Assert.assertThat(queryModel.getTypes().size(), is(2));
		
		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(queryModel.getTypes().get(0).getName(), is("NormalType"));
		Assert.assertThat(queryModel.getTypes().get(1).getName(), is("Account Reps"));
	}
	
	@Test
	public void testNormalTypeWithQuotes() {
		QueryModel queryModel = parse("select NormalField from 'NormalType'");
		Assert.assertThat(queryModel.getFields().size(), is(1));
		Assert.assertThat(queryModel.getTypes().size(), is(1));
		
		Assert.assertThat(queryModel.getFields().get(0).getName(), is("NormalField"));
		Assert.assertThat(queryModel.getTypes().get(0).getName(), is("NormalType"));
	}
	
	@Test
	public void testTypeWithSpacesInOrderBy() {
		QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces'");
		Assert.assertThat(queryModel.getOrderByFields().size(), is(1));
		
		
		Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
	}
	
	@Test
	public void testTypeWithSpacesMixedWithNormalInOrderBy() {
		QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces',NormalField");
		Assert.assertThat(queryModel.getOrderByFields().size(), is(2));
		
		
		Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
		Assert.assertThat(queryModel.getOrderByFields().get(1).getName(), is("NormalField"));
	}

    @Test
    public void testTypeWithSpacesInOrderByAscending() {
        QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces' asc");
        Assert.assertThat(queryModel.getOrderByFields().size(), is(1));


        Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
        Assert.assertThat(queryModel.getDirection(), is(Direction.ASC));
    }

    @Test
    public void testTypeWithSpacesMixedWithNormalInOrderByAscending() {
        QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces',NormalField ascending");
        Assert.assertThat(queryModel.getOrderByFields().size(), is(2));


        Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
        Assert.assertThat(queryModel.getOrderByFields().get(1).getName(), is("NormalField"));
        Assert.assertThat(queryModel.getDirection(), is(Direction.ASC));
    }

    @Test
    public void testTypeWithSpacesInOrderByDescending() {
        QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces' desc");
        Assert.assertThat(queryModel.getOrderByFields().size(), is(1));


        Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
        Assert.assertThat(queryModel.getDirection(), is(Direction.DESC));
    }

    @Test
    public void testTypeWithSpacesMixedWithNormalInOrderByDescending() {
        QueryModel queryModel = parse("select NormalField from Account ORDER BY 'Field With Spaces',NormalField descending");
        Assert.assertThat(queryModel.getOrderByFields().size(), is(2));


        Assert.assertThat(queryModel.getOrderByFields().get(0).getName(), is("Field With Spaces"));
        Assert.assertThat(queryModel.getOrderByFields().get(1).getName(), is("NormalField"));
        Assert.assertThat(queryModel.getDirection(), is(Direction.DESC));
    }
	
	@Test
	public void testTypeWithSpacesInFilters() {
		QueryModel queryModel = parse("select NormalField from Account WHERE 'Field With Spaces' = 1");
		assertFieldComparation(queryModel.getFilterExpression(), EqualsOperator.class, "Field With Spaces", 1.0);
	}
	
	@Test
	public void testFieldWithSpacesInFiltersWithParenthesis() {
		QueryModel queryModel = parse("select NormalField from Account WHERE ('Field With Spaces' = 1)");
		assertFieldComparation(queryModel.getFilterExpression(), EqualsOperator.class, "Field With Spaces", 1.0);
	}
	
	@Test
	public void testFieldWithSpacesInComparison() {
		QueryModel queryModel = parse("select NormalField from Account WHERE ('Field With Spaces' < 1)");
        assertFieldComparation(queryModel.getFilterExpression(), LessOperator.class, "Field With Spaces", 1.0);
    }

	public QueryModel parse(final String string) {
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
			QueryModel parse = parser.parse(string);
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
