package org.mule.common.metadata.datatype;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataTypeFactory {
	
	private interface MetaDataModelEvaluator<T> {
		public boolean isEvaluatable(Class<T> c);
		public DataType evaluate(Class<T> c);
	}
	
	private static class AssignableMetaDataModelEvaluator<T> implements MetaDataModelEvaluator<T> {
		
		private Class<?>[] parentClasses;
		private DataType dataType;
		
		public AssignableMetaDataModelEvaluator(Class<?> parentClass, DataType dataType) {
			this(new Class[] {parentClass}, dataType);
		}
		public AssignableMetaDataModelEvaluator(Class<?>[] parentClasses, DataType dataType) {
			this.parentClasses = parentClasses;
			this.dataType = dataType;
		}
		
		@Override
		public DataType evaluate(Class<T> c) {
			if (isEvaluatable(c)) {
				return dataType;
			}
			return null;
		}

		@Override
		public boolean isEvaluatable(Class<T> c) {
			for (Class<?> parentClass : parentClasses) {
				if (parentClass.isAssignableFrom(c)) {
					return true;
				}
			}
			return false;
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}
	
	private static final MetaDataModelEvaluator<Boolean> BOOLEAN_EVALUATOR = new AssignableMetaDataModelEvaluator<Boolean>(
			new Class[] {boolean.class, Boolean.class}, DataType.BOOLEAN);
	
	private static final MetaDataModelEvaluator<String> STRING_EVALUATOR = new AssignableMetaDataModelEvaluator<String>(
			String.class, DataType.STRING);
	
	private static final MetaDataModelEvaluator<Number> NUMBER_EVALUATOR = new AssignableMetaDataModelEvaluator<Number>(
			new Class[] {int.class, double.class, float.class, Number.class}, DataType.NUMBER);
	
	private static final MetaDataModelEvaluator<Byte[]> BYTE_EVALUATOR = new AssignableMetaDataModelEvaluator<Byte[]>(
			new Class[] {byte[].class, Byte[].class}, DataType.BYTE_ARRAY);
	
	private static final MetaDataModelEvaluator<?> DATE_TIME_EVALUATOR = new AssignableMetaDataModelEvaluator<Object>(
			new Class[] {Date.class}, DataType.DATE_TIME); // || DateTime.class.isAssignableFrom(c);
	
	private static final MetaDataModelEvaluator<?> STREAM_EVALUATOR = new AssignableMetaDataModelEvaluator<Object>(
			new Class[] {InputStream.class, OutputStream.class}, DataType.STREAM);	
	
	private static final MetaDataModelEvaluator<List<?>> LIST_EVALUATOR = new AssignableMetaDataModelEvaluator<List<?>>(
			List.class, DataType.LIST);	
	
	private static final MetaDataModelEvaluator<Map<?, ?>> MAP_EVALUATOR = new AssignableMetaDataModelEvaluator<Map<?, ?>>(
			Map.class, DataType.MAP);	
	
	private static final MetaDataModelEvaluator<?> POJO_EVALUATOR = new AssignableMetaDataModelEvaluator<Object>(
			Object.class, DataType.POJO) {
		@Override
		public boolean isEvaluatable(Class<Object> c) {
			return !c.isPrimitive();
		}
	};
	
	private static final MetaDataModelEvaluator<?>[] evaluators = new MetaDataModelEvaluator[] {
		BOOLEAN_EVALUATOR,
		STRING_EVALUATOR,
		NUMBER_EVALUATOR,
		BYTE_EVALUATOR,
		DATE_TIME_EVALUATOR,
		STREAM_EVALUATOR,
		LIST_EVALUATOR,
		MAP_EVALUATOR,
		POJO_EVALUATOR
	};
	
	private static DataTypeFactory instance = new DataTypeFactory();
	
	private DataTypeFactory() {}
	
	public static DataTypeFactory getInstance() {
		return instance;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataType getDataType(Class<?> clazz) {
		DataType dataType = null;
		for (MetaDataModelEvaluator e : evaluators) {
			if (e.isEvaluatable(clazz)) {
				dataType = e.evaluate(clazz);
				if (dataType != null) break;
			}
		}
		return dataType;
	}
}
