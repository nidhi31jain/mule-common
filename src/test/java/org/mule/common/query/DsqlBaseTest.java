/**
 *
 */
package org.mule.common.query;

import org.mule.common.query.dsql.parser.MuleDsqlParser;
import org.mule.common.query.expression.BooleanValue;
import org.mule.common.query.expression.FieldComparation;
import org.mule.common.query.expression.NumberValue;
import org.mule.common.query.expression.StringValue;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class DsqlBaseTest
{

    @Test
    public void parseStringValue(){
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where lastName = 'de Achaval'");
        Assert.assertThat(parse.getFilterExpression(), CoreMatchers.is(FieldComparation.class));
        FieldComparation fieldComparation = (FieldComparation) parse.getFilterExpression();
        Assert.assertThat(fieldComparation.getValue(), CoreMatchers.is(StringValue.class));
    }


    @Test
    public void parseIntValue(){
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where age = 30");
        Assert.assertThat(parse.getFilterExpression(), CoreMatchers.is(FieldComparation.class));
        FieldComparation fieldComparation = (FieldComparation) parse.getFilterExpression();
        Assert.assertThat(fieldComparation.getValue(), CoreMatchers.is(NumberValue.class));
    }

    @Test
    public void parseBooleanValue(){
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query parse = muleDsqlParser.parse("select name from account where registered = true");
        Assert.assertThat(parse.getFilterExpression(), CoreMatchers.is(FieldComparation.class));
        FieldComparation fieldComparation = (FieldComparation) parse.getFilterExpression();
        Assert.assertThat(fieldComparation.getValue(), CoreMatchers.is(BooleanValue.class));
    }

}
