package com.jacob.impl.ado;

public interface DataTypeEnum {
	
	public static final int adBigInt = 20; //Indicates an eight-byte signed integer (DBTYPE_I8)
	public static final int adBinary = 128; //Indicates a binary value (DBTYPE_BYTES)
	public static final int adBoolean = 11; //Indicates a Boolean value (DBTYPE_BOOL)
	public static final int adBSTR = 8; //Indicates a null-terminated character string (Unicode) (DBTYPE_BSTR)
	public static final int adChapter = 136; //Indicates a four-byte chapter value that identifies rows in a child rowset (DBTYPE_HCHAPTER)
	public static final int adChar = 129; //Indicates a string value (DBTYPE_STR)
	public static final int adCurrency = 6; //Indicates a currency value (DBTYPE_CY). Currency is a fixed-point number with four digits to the right of the decimal point. It is stored in an eight-byte signed integer scaled by 10,000
	public static final int adDate = 7; //Indicates a date value (DBTYPE_DATE). A date is stored as a double, the whole part of which is the number of days since December 30, 1899, and the fractional part of which is the fraction of a day
	public static final int adDBDate = 133; //Indicates a date value (yyyymmdd) (DBTYPE_DBDATE)
	public static final int adDBTime = 134; //Indicates a time value (hhmmss) (DBTYPE_DBTIME)
	public static final int adDBTimeStamp = 135; //Indicates a date/time stamp (yyyymmddhhmmss plus a fraction in billionths) (DBTYPE_DBTIMESTAMP)
	public static final int adDecimal = 14; //Indicates an exact numeric value with a fixed precision and scale (DBTYPE_DECIMAL)
	public static final int adDouble = 5; //Indicates a double-precision floating-point value (DBTYPE_R8)
	public static final int adEmpty = 0; //Specifies no value (DBTYPE_EMPTY)
	public static final int adError = 10; //Indicates a 32-bit error code (DBTYPE_ERROR)
	public static final int adFileTime = 64; //Indicates a 64-bit value representing the number of 100-nanosecond intervals since January 1, 1601 (DBTYPE_FILETIME)
	public static final int adGUID = 72; //Indicates a globally unique identifier (GUID) (DBTYPE_GUID)
	public static final int adIDispatch = 9; //Indicates a pointer to an IDispatch interface on a COM object (DBTYPE_IDISPATCH). This data type is currently not supported by ADO. Usage may cause unpredictable results.
	public static final int adInteger = 3; //Indicates a four-byte signed integer (DBTYPE_I4)
	public static final int adLongVarBinary = 205; //Indicates a long binary value
	public static final int adLongVarChar = 201; //Indicates a long string value.
	public static final int adLongVarWChar = 203; //Indicates a long null-terminated Unicode string value
	public static final int adNumeric = 131; //Indicates an exact numeric value with a fixed precision and scale (DBTYPE_NUMERIC)
	public static final int adPropVariant = 138; //Indicates an Automation PROPVARIANT (DBTYPE_PROP_VARIANT)
	public static final int adSingle = 4; //Indicates a single-precision floating-point value (DBTYPE_R4)
	public static final int adSmallInt = 2; //Indicates a two-byte signed integer (DBTYPE_I2)
	public static final int adTinyInt = 16; //Indicates a one-byte signed integer (DBTYPE_I1)
	public static final int adUnsignedBigInt = 21; //Indicates an eight-byte unsigned integer (DBTYPE_UI8)
	public static final int adUnsignedInt = 19; //Indicates a four-byte unsigned integer (DBTYPE_UI4)
	public static final int adUnsignedSmallInt = 18; //Indicates a two-byte unsigned integer (DBTYPE_UI2)
	public static final int adUnsignedTinyInt = 17; //Indicates a one-byte unsigned integer (DBTYPE_UI1)
	public static final int adUserDefined = 132; //Indicates a user-defined variable (DBTYPE_UDT)
	public static final int adVarBinary = 204; //Indicates a binary value
	public static final int adVarChar = 200; //Indicates a string value
	public static final int adVariant = 12; //Indicates an Automation Variant (DBTYPE_VARIANT). This data type is currently not supported by ADO. Usage may cause unpredictable results.
	public static final int adVarNumeric = 139; //Indicates a numeric value
	public static final int adVarWChar = 202; //Indicates a null-terminated Unicode character string
	public static final int adWChar = 130; //Indicates a null-terminated Unicode character string (DBTYPE_WSTR)
 
}
