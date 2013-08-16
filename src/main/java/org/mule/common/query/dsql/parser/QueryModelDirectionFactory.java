package org.mule.common.query.dsql.parser;

import org.mule.common.query.expression.Direction;

import java.util.HashMap;

public class QueryModelDirectionFactory {

    private static QueryModelDirectionFactory instance = new QueryModelDirectionFactory();

    private HashMap<String, Direction> directions = new HashMap<String, Direction>();

    private QueryModelDirectionFactory(){
        directions.put("asc", Direction.ASC);
        directions.put("ascending", Direction.ASC);
        directions.put("desc", Direction.DESC);
        directions.put("descending", Direction.DESC);
    }

    public static QueryModelDirectionFactory getInstance() {
        return instance;
    }

    public Direction getDirection(String direction) {
        return directions.get(direction);
    }
}
