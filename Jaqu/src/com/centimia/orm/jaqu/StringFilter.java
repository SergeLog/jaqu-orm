/*
 * Copyright (c) 2007-2010 Centimia Ltd.
 * All rights reserved.  Unpublished -- rights reserved
 *
 * Use of a copyright notice is precautionary only, and does
 * not imply publication or disclosure.
 *  
 * Multiple-Licensed under the H2 License,
 * Version 1.0, and under the Eclipse Public License, Version 1.0
 * (http://h2database.com/html/license.html).
 * Initial Developer: H2 Group, Centimia Inc.
 */

/*
 * Update Log
 * 
 *  Date			User				Comment
 * ------			-------				--------
 * 03/02/2010		Shai Bentin			 create
 */
package com.centimia.orm.jaqu;

/**
 * To allow a SQL filter String inside a condition to amplify a condition based on runtime variables.
 * 
 * @author Shai Bentin
 */
interface StringFilter {

	String getConditionString(SelectTable<?> selectTable);
	
}