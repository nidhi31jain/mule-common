package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.query.expression.Operator;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DefaultQueryCapability implements QueryCapability {

    private boolean isSelectCapable = true;

    private boolean isWhereCapable = true;

    private boolean isSortCapable = true;

    private List<Operator> supportedOperators;

    public DefaultQueryCapability()
    {
        this.isSelectCapable = true;
        this.isSortCapable = true;
        this.isWhereCapable = true;
        this.supportedOperators = new ArrayList<Operator>();
    }

    public DefaultQueryCapability(boolean isSelectCapable, boolean isSortCapable, List<Operator> supportedOperators )
    {
        this.isSelectCapable = isSelectCapable;
        this.isSortCapable = isSortCapable;
        this.setSupportedOperators(supportedOperators);
    }

    public DefaultQueryCapability(Boolean isSelectCapable, Boolean isSortCapable, Boolean isWhereCapable)
    {
        this.isSelectCapable=isSelectCapable;
        this.isSortCapable=isSortCapable;
        this.isWhereCapable = isWhereCapable;
        if (!this.isWhereCapable) {
            this.supportedOperators = new ArrayList<Operator>();
        }
    }

    public DefaultQueryCapability(List<Operator> supportedOperators )
    {
        this(true, true, true);
        this.setSupportedOperators(supportedOperators);
    }

    public void generateSupportedOperators(DataType dataType){
        this.setSupportedOperators(SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(dataType));
    }

    @Override
    public boolean isSelectCapable() {
        return isSelectCapable;
    }

    public void setSelectCapable(boolean selectCapable) {
        isSelectCapable = selectCapable;
    }

    @Override
    public boolean isWhereCapable() {
        return isWhereCapable;
    }

    public void setWhereCapable(boolean whereCapable) {
        isWhereCapable = whereCapable;
    }

    @Override
    public boolean isSortCapable() {
        return isSortCapable;
    }

    public void setSortCapable(boolean sortCapable) {
        isSortCapable = sortCapable;
    }

    @Override
    public List<Operator> getSupportedOperators() {
        return supportedOperators;
    }

    private void setSupportedOperators(List<Operator> supportedOperators){
        this.supportedOperators= supportedOperators;
        this.calculateWhereCapable();
    }

    /**
     * A field is 'where' capable if its operations aren't empty, otherwise there won't be able to operate
     */
    private void calculateWhereCapable() {
        this.isWhereCapable= ! this.getSupportedOperators().isEmpty();
    }
}
