/*---------------------------------------------------------------------------------------------------------------
 *
 * VER : v0.1
 * PROJ : OPEN API
 * Copyright 2020 SITHA.RUPP All rights reserved
 *---------------------------------------------------------------------------------------------------------------
 *                               H      I      S      T      O      R      Y
 *---------------------------------------------------------------------------------------------------------------
 *   DATE            AUTHOR               DESCRIPTION
 *-----------    ------------------    --------------------------------------------------------------------------
 * 2020-06-01        SOPHEAP SITHA         Creation
 *---------------------------------------------------------------------------------------------------------------
 */

package sitha.rupp.platform.collection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PMultiData extends ArrayList<LinkedHashMap<String, Object>> implements PDataProtocol {

	private static final long serialVersionUID = 1L;
	
	private boolean nullToInitialize = false;

	public boolean isNullToInitialize() {
		return this.nullToInitialize;
	}
	
	public void setNullToInitialize( boolean nullToInitialize ) {
		this.nullToInitialize = nullToInitialize;
	}
	
	/**
	 * Constructor for PMultiData
	 */
	public PMultiData() {
		super();
	}
	
	/**
	 * Constructor for PMultiData
	 * @param map
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PMultiData( List map ) {
		super( map );
	}
	
	public Object[] getKeys() {
		Object[] keyArr = new Object[0];
		if ( size() > 0 ) {
			keyArr = get( 0 ).keySet().toArray();
		}
		return keyArr;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void put( Object key, List l ) {
		for ( int i = 0; i < l.size(); i++ ) {
			if ( size() < i + 1 ) {
				addPData( new PData() );
			}
			( (Map) get( i ) ).put( key, l.get( i ) );
		}
	}
	
	@SuppressWarnings("unchecked")
	public void putAll( @SuppressWarnings("rawtypes") List m ) {
		clear();
		addAll( m );
	}
	
	/**
	 * Delete the data (column) for a given key.
	 * 
	 * @param key Key in column to delete
	 * @see #removeColumn(String)
	 */
	public void removeColumn( String key ) {
		for(int i=0; i < this.size(); i++) {
			
			if(this.get(i).containsKey(key)) {
				this.get(i).remove(key);
			}
		}
	}

	/**
	 * Delete the data (row) for a given key.
	 * 
	 * @param key Key in row to delete
	 * @see #removeRow(int)
	 */
	public void removeRow( int keyIndex ) {
		remove( keyIndex );
	}

	public Object getKeyWithIndex( int keyIndex ) {
		return getKeyWithIndex( keyIndex, 0 );
	}
	
	/**
	 * Returns the key object corresponding to the index.
	 * 
	 * @param keyIndex
	 * @return int
	 */
	public Object getKeyWithIndex( int keyIndex, int index ) {
		Object retObj = null;
		Set<String> tempSet = this.get( index ).keySet();

		if ( keyIndex >= tempSet.size() ) {
            return null;
		}

		Iterator<String> iterator = tempSet.iterator();
		for ( int inx = 0; inx <= keyIndex; inx++ ) {
			retObj = iterator.next();
		}
		return retObj;        
	}
	
	@SuppressWarnings("unchecked")
	public List<PData> get( Object key ) {
		@SuppressWarnings("rawtypes")
		ArrayList list = new ArrayList();  
		for ( int i = 0; i < size(); i++ ) {
			list.add( get( i ).get( key ) );
		}
		return list;
	}
	
	public List<PData> toListPData() {
		List<PData> listPData = new ArrayList<PData>();
		for ( LinkedHashMap<String, Object> map : this ) {
			listPData.add( new PData( map ) );
		}
		return listPData;
	}
	
	public void addPData( PData PData ) {
		addPData( new PData( PData ) );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue Object
	 */
	public void modify( String key, int index, Object replaceValue ) {
		if ( !( size() > index ) ) {
	        
		}
		if ( !get( index ).containsKey( key ) ) {
	        
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue boolean
	 */
	public void modifyBoolean( String key, int index, boolean replaceValue ) {
		if ( !( size() > index ) ) {
	        //something
		}
		if ( !get( index ).containsKey( key ) ) {
	       //something
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue String
	 * @throws Exception 
	 */
	public void modifyString( String key, int index, String replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
	       throw new Exception("modify string error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue int
	 * @throws Exception 
	 */
	public void modifyInt( String key, int index, int replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
			throw new Exception("modif integer error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue double
	 * @throws Exception 
	 */
	public void modifyDouble( String key, int index, double replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
	        throw new Exception("modify double error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue float
	 * @throws Exception 
	 */
	public void modifyFloat( String key, int index, float replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
			throw new Exception("modify float error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue long
	 * @throws Exception 
	 */
	public void modifyLong( String key, int index, long replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
			throw new Exception("modify long error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue short
	 * @throws Exception 
	 */
	public void modifyShort( String key, int index, short replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
			throw new Exception("modify short error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Replace the value at the index end corresponding to the corresponding key value with the value passed to the parameter.
	 * 
	 * @param key String
	 * @param index int
	 * @param replaceValue BigDecimal
	 * @throws Exception 
	 */
	public void modifyBigDecimal( String key, int index, BigDecimal replaceValue ) throws Exception {
		if ( !( size() > index ) ) {
	        throw new Exception("modify bigdecimal error occur");
		}
		get( index ).put( key, replaceValue );
	}
	
	/**
	 * Set the value of the object type in the corresponding key value.
	 * 
	 * @param key Object
	 * @param value Object
	 */
	public void add( String key, Object value ) {
		boolean add = false;
		for ( int i = 0; i < size(); i++ ) {
			if ( !get( i ).containsKey( key ) ) {
				get( i ).put( key, value );
				add = true;
				break;
			}
		}
		if ( !add ) {
			PData row = new PData();
			//row.set( key, value );
			addPData( row );
		}
	}
	
	private Object getObject( Object key, int index ) {
		
		try {
			if ( size() <= index ) {
				return null;
			} else {
				return get( index ).get( key );
			}
		} catch ( IndexOutOfBoundsException ioe ) {
           ioe.printStackTrace();
		}
		return null;
	}

	/**
	 * Return the value corresponding to the key and index in the form of an object.
	 * Returns a null or empty string (if value is null and isNullToInitialise() is true)
	 * if the key or value does not exist.
	 * 
	 * @param key Object
	 * @param index int
	 * @return Object
	 */
	public Object get( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null && isNullToInitialize() ) {
			return "";
		}
		
		return o;
	}

	/**
	 * Return the value corresponding to the keyIndex and index to the object.
	 * Return a null or empty string (if value is null and isNullToInitialise () is true)
	 * if the key or value does not exist.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return Object
	 * @see #getKeyWithIndex(int)
	 * @see #get(Object, int)
	 */
	public Object get( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return get( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in String format.
	 * Returns a null or empty string (if value is null and isNullToInitialise() is true)
	 * if the key or value does not exist.
	 * 
	 * @param key String
	 * @param index int
	 * @return String
	 */
	public String getString( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return "";
			}
			return null;
		} else {
			// If the key type is BigDecimal, it is represented as 0E-8 when the decimal point is set at least 8 digits.
			if ( o instanceof BigDecimal ) {
				return ( (BigDecimal) o ).toPlainString();
			} else {
				return o.toString();
			}
		}
	}

	/**
	 * Return the value corresponding to the keyIndex and index in String format.
	 * Returns a null or empty string (if value is null and isNullToInitialise() is true)
	 * if the key or value does not exist.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return String
	 * @see #getKeyWithIndex(int)
	 * @see #getString(Object, int)
	 */
	public String getString( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getString( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in boolean form.
	 * Returns LRuntimeException or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting will occur only if value is Boolean or String,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return boolean
	 */
	public boolean getBoolean( Object key, int index ) {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return false;
			}
		} else {
			if ( o instanceof Boolean ) {
				return ( (Boolean) o ).booleanValue();
			}
			if ( o instanceof String ) {
				try {
					return Boolean.valueOf( o.toString() ).booleanValue();
				} catch ( Exception e ) {
                    e.printStackTrace();
				}
			}
		}
		return false; // prevent compile error line. unreachable block.
	}

	/**
	 * Return the value corresponding to the keyIndex and index in boolean form.
	 * Returns LRuntimeException or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting occurs only if value is Boolean or String,
	 * otherwise exception will occur.
	 *  
	 * @param keyIndex int
	 * @param index int
	 * @return boolean
	 * @see #getKeyWithIndex(int)
	 * @see #getBoolean(Object, int)
	 */
	public boolean getBoolean( int keyIndex, int index ) {
		Object key = getKeyWithIndex( keyIndex, index );
		return getBoolean( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in int.
	 * Returns exception or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting will occur only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return int
	 * @throws Exception 
	 */
	public int getInt( Object key, int index ) throws Exception {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return 0;
			}
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).intValue();
			}
			if ( o instanceof String ) {
				try {
					return Integer.parseInt( o.toString() );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
			
		}
		return 0;
	}

	/**
	 * Return the value corresponding to the keyIndex and index in int form.
	 * Returns LRuntimeException or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting will occur only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return int
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getInt(Object, int)
	 */
	public int getInt( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getInt( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in double.
	 * Returns exception or 0.0D (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting occurs only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return double
	 * @throws Exception 
	 */
	public double getDouble( Object key, int index ) throws Exception {
		Object o = getObject( key, index );

		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return 0.0D;
			}
			
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).doubleValue();
			}
			if ( o instanceof String ) {
				try {
					return Double.parseDouble( o.toString() );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}

	/**
	 * Return the value corresponding to the keyIndex and index in double.
	 * Returns exception or 0.0D (value is null and isNullToInitialise() is true.)
	 * if the key or value does not exist <br>
	 * If value exists, type-casting will occur only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return double
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getDouble(Object, int)
	 */
	public double getDouble( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getDouble( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in float form.
	 * Returns exception or 0.0F (if value is null and isNullToInitialize() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting occurs only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return float
	 * @throws Exception 
	 */
	public float getFloat( Object key, int index ) throws Exception {
		Object o = getObject( key, index );

		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return 0.0F;
			}

		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).floatValue();
			}
			if ( o instanceof String ) {
				try {
					return Float.parseFloat( o.toString() );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}

	/**
	 * Return the value corresponding to the keyIndex and index in float.
	 * Return exception or 0.0F (if value is null and isNullToInitialise() is true).
	 * if no key or value exists<br>
	 * If value exists, type-casting occurs only if value is Number or String,
	 * otherwise LRuntimeException will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return float
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getFloat(Object, int)
	 */
	public float getFloat( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getFloat( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in long format.
	 * Returns exception or 0L (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting occurs only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return long
	 * @throws Exception 
	 */
	public long getLong( Object key, int index ) throws Exception {
		Object o = getObject( key, index );

		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return 0L;
			}
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).longValue();
			}
			if ( o instanceof String ) {
				try {
					return Long.parseLong( o.toString() );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}

	/**
	 * Return the value corresponding to the keyIndex and index in long format.
	 * Returns exception or 0L (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting occurs only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return long
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getLong(Object, int)
	 */
	public long getLong( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getLong( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in a short form.
	 * Returns exception or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting will occur only briefly
	 * if value is Number or String, otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return short
	 * @throws Exception 
	 */
	public short getShort( Object key, int index ) throws Exception {
		Object o = getObject( key, index );

		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return 0;
			}
		} else {
			if ( o instanceof Number ) {
				return ( (Number) o ).shortValue();
			}
			
			if ( o instanceof String ) {
				try {
					return Short.parseShort( o.toString() );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	/**
	 * Return the value corresponding to the keyIndex and index in a short format.
	 * Returns exception or 0 (if value is null and isNullToInitialise() is true)
	 * if key or value does not exist.<br>
	 * If value exists, type-casting will occur only if value is Number or String,
	 * otherwise exception will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return short
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getShort(Object, int)
	 */
	public short getShort( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getShort( key, index );
	}

	/**
	 * Return the value corresponding to the key and index in the BegDecimal format.
	 * If the key or value does not exist, then exception or BigDecimal (0)
	 * (if value is null and isNullToInitialize() is true).< br >
	 * If value is present, type-casting will occur only if value is BigDecimal,
	 * otherwise exception will occur.
	 * 
	 * @param key Object
	 * @param index int
	 * @return BigDecimal
	 * @throws Exception 
	 */
	public BigDecimal getBigDecimal( Object key, int index ) throws Exception {
		Object o = getObject( key, index );
		
		if ( o == null ) {
			if ( isNullToInitialize() ) {
				return new BigDecimal( 0 );
			}
			return null;
		} else {
			if ( o instanceof BigDecimal ) {
				return (BigDecimal) o;
			}
			
			// NOTE
			if ( o instanceof Number ) {
				return new BigDecimal( ( (Number) o ).doubleValue() );
			}
			
			if ( o instanceof String ) {
				try {
					return new BigDecimal( (String) o );
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		return new BigDecimal(0);
	}

	/**
	 * Return the value corresponding to the keyIndex and index in BegDecimal format.
	 * If the key or value does not exist, then  or BigDecimal(0)
	 * (if value is null and isNullToInitialize () is true).< br >
	 * If value is present, type-casting will occur only if value is BigDecimal,
	 * otherwise exception will occur.
	 * 
	 * @param keyIndex int
	 * @param index int
	 * @return BigDecimal
	 * @throws Exception 
	 * @see #getKeyWithIndex(int)
	 * @see #getBigDecimal(Object, int)
	 */
	public BigDecimal getBigDecimal( int keyIndex, int index ) throws Exception {
		Object key = getKeyWithIndex( keyIndex, index );
		return getBigDecimal( key, index );
	}
	
	public int getDataCount() {
		return this.size();
	
	}
	
	@SuppressWarnings( { "unchecked" } )
	public PData getPData( int key ) {
		Object obj = get( key );
		if ( obj instanceof PData ) {
			return (PData) obj;
		} else if ( obj instanceof LinkedHashMap ) {
			return new PData( (LinkedHashMap<String, Object>) obj );
		} else {
			return obj == null ? new PData() : (PData) obj;
		}
	}
	
	public void addPMultiData( PMultiData PData ) {
		int cnt = PData.size();
		for ( int i = 0; i < cnt; i++ ) {
			this.addPData( new PData( PData.get( i ) ) );
		}
	}
	
	public void addPMultiDataNoClone( PMultiData PData ) {
		int cnt = PData.size();
		for ( int i = 0; i < cnt; i++ ) {
			this.add( PData.get( i ) );
		}
	}
	
	public void addPDataNoClone(PData data) {
		addPData (data);
	}

	/**
	 * This method returns the number of keys that make up the PMultiData.
	 * 
	 * @return int
	 * */
	public int getKeyCount() {
		int keyCount = 0;
		if(size() > 0 ) keyCount = get(0).keySet().size();
		return keyCount;
	}

	
	/**
	 * Returns the size (total number of rows) corresponding 
	 * to the key present in the PMultiData. 
	 * If the key does not exist, it returns 0.
	 *   @return int
	 *   @see #getDataCount()
	 * 
	 * */
	public int getDataCount(Object key) {
		return this.size();
	}

	/**
	 * Check if key exists
	 * 
	 * @param key target key
	 * @return true - exist
	 *         false - not exist
	 * */
	public boolean containsKey(Object key) {
		if( this.size() == 0 ) {
			return false; 
		} else {
			return containsKey(0, key);
		}
	}

	/**
	 * Check if key exists
	 * 
	 * @param index index
	 * @param key target key
	 * @return true - exist
	 *         false - not exist
	 * */
	public boolean containsKey(int index, Object key) {
		if( index >= this.size() ) {
			return false;
		} else {
			return get(index).containsKey(key);
		}
	}

}
