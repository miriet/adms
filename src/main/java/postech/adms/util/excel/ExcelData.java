package postech.adms.util.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

public class ExcelData extends HashMap<Object, Object> {
    /**
     * 
     */
    private static final long serialVersionUID = 4879532842222600420L;
    

    public String getColumn( String columnName ) {
    	if(super.get( columnName ) == null)
    		return "";
    	return super.get( columnName ).toString();
    	
    }
    public List<Object> getValues() {
    	
    	List<Object> values = new ArrayList<Object>();
        Set<?> keySet = super.keySet();
        Iterator<?> i = keySet.iterator();
        Object value;
        while( i.hasNext() ) {
            value = super.get( i.next() );
            values.add(value);
        }
    	return values;
    }
    
    public float getFloatValue( String columnName ){        
        return new Float((String)super.get( columnName )).floatValue();
    }
    
    public boolean isEmpty() {
        boolean isEmpty = true;
        Set<?> keySet = super.keySet();
        Iterator<?> i = keySet.iterator();
        Object value;
        while( i.hasNext() ) {
            value = super.get( i.next() );
            
            if( value != null && StringUtils.hasText( value.toString() ) ) {
                isEmpty = false;
                break;
            }
        }
        
        return isEmpty;
    }
}
