/**
 *
 */
package org.mule.common.query;

import org.mule.common.query.dsql.parser.MuleDsqlParser;
import org.mule.common.query.expression.And;
import org.mule.common.query.expression.Expression;
import org.mule.common.query.expression.FieldComparation;
import org.mule.common.query.expression.Or;
import org.mule.common.query.expression.SearchByExpression;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DsqlFullTextSearchTest
{

    @Test
    public void simpleCase()
    {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where searchBy 'de Achaval'");
        Expression filterExpression = parse.getFilterExpression();
        Assert.assertThat(filterExpression, CoreMatchers.is(SearchByExpression.class));
        Assert.assertThat(((SearchByExpression) filterExpression).getValue().getValue(), CoreMatchers.is("de Achaval"));
    }


    @Test
    public void nestedAndSearchByWithAnd()
    {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where searchBy 'de Achaval' or searchBy 'Abdala'");
        Expression filterExpression = parse.getFilterExpression();
        Assert.assertThat(filterExpression, CoreMatchers.is(Or.class));
        Or logicalExpression = (Or) filterExpression;
        Assert.assertThat(logicalExpression.getLeft(), CoreMatchers.is(SearchByExpression.class));
        Assert.assertThat(logicalExpression.getRight(), CoreMatchers.is(SearchByExpression.class));

    }

    @Test
    public void fullTextSearch()
    {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select * from contacts where searchBy 'de Achaval'");
        Expression filterExpression = parse.getFilterExpression();
        Assert.assertThat(filterExpression, CoreMatchers.is(SearchByExpression.class));
        Assert.assertThat(((SearchByExpression) filterExpression).getValue().getValue(), CoreMatchers.is("de Achaval"));

    }

    @Test
    public void nestedAndSearchByWithFieldComparation()
    {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where searchBy 'de Achaval' and birth >  13/06/2001");
        Expression filterExpression = parse.getFilterExpression();
        Assert.assertThat(filterExpression, CoreMatchers.is(And.class));
        And logicalExpression = (And) filterExpression;
        Assert.assertThat(logicalExpression.getLeft(), CoreMatchers.is(SearchByExpression.class));
        Assert.assertThat(logicalExpression.getRight(), CoreMatchers.is(FieldComparation.class));

    }

}
