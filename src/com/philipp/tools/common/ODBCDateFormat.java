/**
 * 
 */
package com.philipp.tools.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author User
 *
 */
public class ODBCDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -1504934041529642836L;

	public ODBCDateFormat() {		
		super("yyyy-MM-dd");
	}
	
	public final String formatAsODBC (Date date) {
		String formatted = this.format(date);
		return "{d '" + formatted + "'}";		
	}

}
