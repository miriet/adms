package postech.adms.util.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.set.ListOrderedSet;

abstract public class LazyUtil {
	private static final Factory LIST_FACTORY = new Factory(){
		public Object create() {
			return new ArrayList<String>();
		}};
	private static final Factory MAP_FACTORY = new Factory(){
		public Object create() {
			return LazyUtil.lazyMap();
		}};
	@SuppressWarnings("unchecked")
	public static final Map<String,List<String>> lazyStringListMap() {
		return LazyMap.decorate(new HashMap<String,List<String>>(), LIST_FACTORY);
	}
	@SuppressWarnings("unchecked")
	public static final Map<String,Object> lazyMap() {
		return LazyMap.decorate(new HashMap<String,Object>(), MAP_FACTORY);
	}
	@SuppressWarnings("unchecked")
	public static final List<String> lazyStringList() {
		return LazyList.decorate(new ArrayList<String>(), FactoryUtils.nullFactory());
	}
	public static final List<Map<String,Object>> lazyListMap() {
		return LazyList.decorate(new ArrayList<Map<String,Object>>(), new Factory(){
			
			public Object create() {
				return new HashMap<String,Object>();
			}}); 
	}
	public static final List<Map<String,Object>> lazyListMap(final int mapSize) {
		return LazyList.decorate(new ArrayList<Map<String,Object>>(), new Factory(){
			
			public Object create() {
				return new HashMap<String,Object>(mapSize);
			}}); 
	}
	public static final List<Map<String,String>> lazyListMapString() {
		return LazyList.decorate(new ArrayList<Map<String,String>>(), new Factory(){
			
			public Object create() {
				return new HashMap<String,Object>();
			}}); 
	}
	public static final List<Map<String,String>> lazyListMapString(final int mapSize) {
		return LazyList.decorate(new ArrayList<Map<String,String>>(), new Factory(){
			
			public Object create() {
				return new HashMap<String,Object>(mapSize);
			}}); 
	}
	@SuppressWarnings("unchecked")
	public static final <T> List<T> lazyList(Class<T> cls) {
		Factory factory = null;
		if (cls==null||String.class.equals(cls) || Number.class.isAssignableFrom(cls)||
				Character.class.equals(cls)) {
			factory = FactoryUtils.nullFactory();
		} else {
			factory = FactoryUtils.instantiateFactory(cls);
		}
		return LazyList.decorate(new ArrayList<T>(), 
				factory);
	}
	@SuppressWarnings("unchecked")
	public static final <T> Set<T> lazySet(Class<T> cls) {
		Factory factory = null;
		if (cls==null||String.class.equals(cls) || Number.class.isAssignableFrom(cls)||
				Character.class.equals(cls)) {
			factory = FactoryUtils.nullFactory();
		} else {
			factory = FactoryUtils.instantiateFactory(cls);
		}		
		return ListOrderedSet.decorate(LazyList.decorate(new ArrayList<T>(), 
				factory));
	}
	
	/**
	 * List 를 HashSet 으로 바꾼다.
	 * @param List
	 * @return HashSet
	 */
	@SuppressWarnings("unchecked")
	public static final <T> Set<T> convertSet(List<T> list) {
		return ListOrderedSet.decorate(list);
	}
	
	@SuppressWarnings("unchecked")
	public static final Map<String, Object> lazyNullObjectMap() {
		return LazyMap.decorate(new HashMap<String,Object>(), 
				FactoryUtils.nullFactory());
	}	
	@SuppressWarnings("unchecked")
	public static final Map<String, String> lazyStringMap() {
		return LazyMap.decorate(new HashMap<String,String>(), 
				FactoryUtils.nullFactory());
	}	
	
	@SuppressWarnings("unchecked")
	public static final <V> Map<String,V> lazyMap(Class<V> cls) {
		return LazyMap.decorate(new HashMap<String,V>(), 
				FactoryUtils.instantiateFactory(cls));
	}
	
}
