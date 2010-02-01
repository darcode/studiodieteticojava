package common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.core.internal.runtime.FindSupport;
import org.eclipse.swt.widgets.Table;

/**
 * Le classi bean che estendono questa classe possono creare automaticamente la
 * lista nel form estendendo la classe ListForm chiamando il metodo protected
 * void riempiTabella(Table table, ArrayList<Object> lista)
 */
public class GenericBean {

	public GenericBean() {
		super();
	}

	public static String[] getFieldsNames(Object aClass) {
		Field[] campi = aClass.getClass().getDeclaredFields();
		String[] campiStringa = new String[campi.length];
		for (int i = 0; i < campi.length; i++) {
			campiStringa[i] = campi[i].getName();
		}
		return campiStringa;
	}

	public static ArrayList<String> getFieldsNamesPerQuery(Object aClass) {
		Field[] campi = aClass.getClass().getDeclaredFields();
		ArrayList<String> campiStringa = new ArrayList<String>();
		for (Field campo : campi) {
			if (!campo.getType().getSimpleName().equals("Set"))
				campiStringa.add(campo.getName());
		}
		return campiStringa;
	}

	public static int getFieldsNumber(Object aClass) {
		Field[] campi = aClass.getClass().getDeclaredFields();
		return campi.length;
	}

	/*
	 * Per avvalorare il field
	 */
	public static void setProperty(String name, Object target, Object value) {
		Method metodo = null;
		try {
			metodo = target.getClass().getMethod("set" + name,
					new Class[] { value.getClass() });
		} catch (NoSuchMethodException e) {
		}
		if (metodo != null)
			try {
				metodo.invoke(target, new Object[] { value });
			} catch (Exception ecc) {
			}
	}

	/*
	 * Per leggere il field
	 */
	public static Object getProperty(String name, Object target) {
		Object ritorno = new String();
		Method metodo = null;
		if (name != null) {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		try {
			metodo = target.getClass().getMethod("get" + name, null);
		} catch (NoSuchMethodException exc) {
			exc.printStackTrace();
		}
		if (metodo != null)
			try {
				ritorno = metodo.invoke(target, new Object[0]);
			} catch (Exception ecc) {
				ecc.printStackTrace();
			}
		return ritorno;
	}

	/*
	 * Per leggere il tipo del field
	 */
	public static String getPropertyClass(String name, Object target) {
		String ritorno = new String();
		try {
			Field declaredField = target.getClass().getDeclaredField(name);
			ritorno = declaredField.getType().getSimpleName();
		} catch (Exception ecc) {
			ecc.printStackTrace();
		}
		return ritorno;
	}

}