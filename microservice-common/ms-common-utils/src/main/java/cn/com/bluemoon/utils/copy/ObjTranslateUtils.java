package cn.com.bluemoon.utils.copy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

public class ObjTranslateUtils {
	public static void ObjectAToB( Object A, Object B ) {
		Field[] fields = B.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
			String type = field.getGenericType().toString();
			try {
				Method m = A.getClass().getMethod("get" + name);
				if ( m == null ) {
					continue;
				}
				if ( m.invoke(A) == null ) {
					continue;
				}
				if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
	                String value =  m.invoke(A).toString(); // 调用getter方法获取属性值
	                if (value != null) {
	                    m = B.getClass().getMethod("set"+name,String.class);
	                    m.invoke(B, value);
	                }
	            } else if (type.equals("class java.lang.Boolean")) {
                    Boolean value = (Boolean) m.invoke(A);
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Boolean.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("boolean")) {
                    Boolean value = (Boolean) m.invoke(A);
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,boolean.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.util.Date")) {
                    Date value = (Date) m.invoke(A);
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Date.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.lang.Integer")) {
                    Integer value = Integer.parseInt( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("int")) {
                    Integer value = Integer.parseInt( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,int.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.lang.Long")) {                  
                    Long value = Long.parseLong( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Long.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("long")) {
                	Long value = Long.parseLong( m.invoke(A).toString() ) ;
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,long.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.lang.Short")) {
                    Short value = Short.parseShort( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Short.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.lang.Double")) {
                    Double value = Double.parseDouble( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Double.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("double")) {
                    Double value = Double.parseDouble( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,double.class);
                        m.invoke(B, value);
                    }
                } else if (type.equals("class java.sql.Timestamp")) {
                	Timestamp value = Timestamp.valueOf( m.invoke(A).toString() );
                    if (value != null) {
                        m = B.getClass().getMethod("set"+name,Timestamp.class);
                        m.invoke(B, value);
                    }
                }
			} catch (NoSuchMethodException e) {
	           // e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
		}
	}
}
