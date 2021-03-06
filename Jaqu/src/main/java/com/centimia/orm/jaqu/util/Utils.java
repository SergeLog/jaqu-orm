/*
 * Copyright (c) 2007-2010 Centimia Ltd.
 * All rights reserved.  Unpublished -- rights reserved
 *
 * Use of a copyright notice is precautionary only, and does
 * not imply publication or disclosure.
 *  
 * Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 2.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group, Centimia Inc.
 */
package com.centimia.orm.jaqu.util;

import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Clob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import com.centimia.orm.jaqu.annotation.Entity;

/**
 * Generic utility methods.
 */
public class Utils {
	private static final AtomicLong COUNTER = new AtomicLong(0);

	private static final boolean MAKE_ACCESSIBLE = true;

	public static <T> ArrayList<T> newArrayList() {
		return new ArrayList<T>();
	}

	public static <T> HashSet<T> newHashSet() {
		return new HashSet<T>();
	}

	public static <A, B> HashMap<A, B> newHashMap() {
		return new HashMap<A, B>();
	}

	public static <A, B> SortedMap<A, B> newSortedHashMap() {
		return new TreeMap<A, B>();
	}
	
	public static <A, B> Map<A, B> newSynchronizedHashMap() {
		HashMap<A, B> map = newHashMap();
		return Collections.synchronizedMap(map);
	}

	public static <A, B> WeakIdentityHashMap<A, B> newWeakIdentityHashMap() {
		return new WeakIdentityHashMap<A, B>();
	}

	public static <A, B> IdentityHashMap<A, B> newIdentityHashMap() {
		return new IdentityHashMap<A, B>();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T newObject(Class<T> clazz) {
		// must create new instances
		if (clazz == Integer.class) {
			return (T) new Integer((int) COUNTER.incrementAndGet());
		}
		else if (clazz == String.class) {
			return (T) ("" + COUNTER.incrementAndGet());
		}
		else if (clazz == Character.class) {
			return (T) ("" + COUNTER.incrementAndGet());
		}
		else if (clazz == Long.class) {
			return (T) new Long(COUNTER.incrementAndGet());
		}
		else if (clazz == Short.class) {
			return (T) new Short((short) COUNTER.incrementAndGet());
		}
		else if (clazz == Byte.class) {
			return (T) new Byte((byte) COUNTER.incrementAndGet());
		}
		else if (clazz == Float.class) {
			return (T) new Float(COUNTER.incrementAndGet());
		}
		else if (clazz == Double.class) {
			return (T) new Double(COUNTER.incrementAndGet());
		}
		else if (clazz == Boolean.class) {
			return (T) new Boolean(false);
		}
		else if (clazz == BigDecimal.class) {
			return (T) new BigDecimal(COUNTER.incrementAndGet());
		}
		else if (clazz == BigInteger.class) {
			return (T) new BigInteger("" + COUNTER.incrementAndGet());
		}
		else if (clazz == java.sql.Date.class) {
			return (T) new java.sql.Date(COUNTER.incrementAndGet());
		}
		else if (clazz == java.sql.Time.class) {
			return (T) new java.sql.Time(COUNTER.incrementAndGet());
		}
		else if (clazz == java.sql.Timestamp.class) {
			return (T) new java.sql.Timestamp(COUNTER.incrementAndGet());
		}
		else if (clazz == java.util.Date.class) {
			return (T) new java.util.Date(COUNTER.incrementAndGet());
		}
		else if (clazz == LocalDate.class) {
			return (T) LocalDate.MIN.plus(COUNTER.incrementAndGet(), ChronoUnit.MICROS);
		}
		else if (clazz == LocalDateTime.class) {
			return (T) LocalDateTime.MIN.plus(COUNTER.incrementAndGet(), ChronoUnit.MICROS);
		}
		else if (clazz == ZonedDateTime.class) {
			return (T) ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault()).plus(COUNTER.incrementAndGet(), ChronoUnit.MICROS);
		}
		else if (clazz == LocalTime.class) {
			return (T) LocalTime.MIN.plus(COUNTER.incrementAndGet(), ChronoUnit.MICROS);
		}
		else if (clazz == List.class) {
			return (T) new ArrayList();
		}
		else if (clazz == Set.class) {
			return (T) new HashSet();
		}
		else if (clazz.isEnum()) {
			return newEnum(clazz, 0);
		}
		try {
			return clazz.newInstance();
		}
		catch (Throwable e) {
			if (MAKE_ACCESSIBLE) {
				Constructor[] constructors = clazz.getDeclaredConstructors();
				// try 0 length constructors
				for (Constructor c : constructors) {
					if (c.getParameterTypes().length == 0) {
						c.setAccessible(true);
						try {
							return clazz.newInstance();
						}
						catch (Exception e2) {
							// system out because logger is not available here
							System.out.println(e2.getMessage());
						}
						finally {
							c.setAccessible(false);
						}
					}
				}
				// try 1 length constructors
				for (Constructor c : constructors) {
					if (c.getParameterTypes().length == 1) {
						c.setAccessible(true);
						try {
							return (T) c.newInstance(new Object[1]);
						}
						catch (Exception e2) {
							// ignore
						}
					}
				}
			}
			throw new RuntimeException("Exception trying to create " + clazz.getName() + ": " + e, e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <E> E newEnum(Class<E> clazz, int ordinal) {
		if (clazz.isEnum()) {
			Object[] objs = null;
			try {
				Method m = clazz.getMethod("values");
				objs = (Object[]) m.invoke(null);
			}
			catch (Exception e) {
				return null;
			}
			if (null != objs && objs.length > ordinal)
				return (E)objs[ordinal];
			else
				return null;
		}
		throw new RuntimeException(clazz.getName() + ": is not an enum type"); 
	}
	
	/**
	 * returns true when the object represents a DB simple type and could be held in a column.
	 * This method is called for selectable fields, however because of the way blobs work it will not work for
	 * Objects that are stored as blobs, clobs
	 * 
	 * @param clazz
	 * @return boolean
	 */
	public static <T> boolean isSimpleType(Class<T> clazz) {
		if (Number.class.isAssignableFrom(clazz) || 
			String.class.isAssignableFrom(clazz) || 
			Date.class.isAssignableFrom(clazz) ||
			Boolean.class.isAssignableFrom(clazz) ||
			Character.class.isAssignableFrom(clazz))  {
			
			return true;
		}
		return false;
	}

	public static Object convert(Object o, Class<?> targetType) {
		if (o == null) {
			return null;
		}
		Class<?> currentType = o.getClass();
		if (targetType.isAssignableFrom(currentType)) {
			return o;
		}
		if (targetType == String.class) {
			if (Clob.class.isAssignableFrom(currentType)) {
				Clob c = (Clob) o;
				try {
					Reader r = c.getCharacterStream();
					return IOUtils.readStringAndClose(r, -1);
				}
				catch (Exception e) {
					throw new RuntimeException("Error converting CLOB to String: " + e.toString(), e);
				}
			}
			return o.toString();
		}
		if (targetType.getAnnotation(Entity.class) != null) {
			// the current value is a primary key for a related table.
			try {
				return targetType.newInstance();
			}
			catch (Exception e) {
				throw new RuntimeException("Can not convert the value " + o + " from " + currentType + " to " + targetType);
			}
		}
		if (Number.class.isAssignableFrom(currentType)) {
			Number n = (Number) o;
			if (targetType == Integer.class) {
				return n.intValue();
			}
			else if (targetType == Long.class) {
				return n.longValue();
			}
			else if (targetType == Double.class) {
				return n.doubleValue();
			}
			else if (targetType == Float.class) {
				return n.floatValue();
			}
		}
		throw new RuntimeException("Can not convert the value " + o + " from " + currentType + " to " + targetType);
	}
}
