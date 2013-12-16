/*
 * x l S Q L  
 * (c) Jim Caprioli, NiLOSTEP.com
 * See xlSQL-license.txt for license details
 *
 */

package com.nilostep.xlsql.jdbc;

import java.io.InputStream;
import java.io.Reader;
import java.sql.*;
import java.util.Map;

public class xlCallableStatement extends xlPreparedStatement 
                                            implements CallableStatement {

    private xlConnection xlCon;
    private CallableStatement dbCstm;

    /**
    * Constructs a new CallableStatementImpl object.
    *
    */
    public xlCallableStatement(xlConnection con, CallableStatement clst, 
                                            String sql) throws SQLException {
        super (con, clst, sql);
        dbCstm = clst;
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getArray
    */
    public Array getArray(int i) throws SQLException {
        return dbCstm.getArray(i);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getArray
    */
    public Array getArray(String parameterName) throws SQLException {
        return dbCstm.getArray(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBigDecimal
    */
    public java.math.BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return dbCstm.getBigDecimal(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBigDecimal
    */
    public java.math.BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return dbCstm.getBigDecimal(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBigDecimal
    */
    public java.math.BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return dbCstm.getBigDecimal(parameterIndex,scale);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBlob
    */
    public Blob getBlob(int i) throws SQLException {
        return dbCstm.getBlob(i);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBlob
    */
    public Blob getBlob(String parameterName) throws SQLException {
        return dbCstm.getBlob(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBoolean
    */
    public boolean getBoolean(int parameterIndex) throws SQLException {
        return dbCstm.getBoolean(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBoolean
    */
    public boolean getBoolean(String parameterName) throws SQLException {
        return dbCstm.getBoolean(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getByte
    */
    public byte getByte(int parameterIndex) throws SQLException {
        return dbCstm.getByte(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getByte
    */
    public byte getByte(String parameterName) throws SQLException {
        return dbCstm.getByte(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBytes
    */
    public byte[] getBytes(int parameterIndex) throws SQLException {
        return dbCstm.getBytes(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getBytes
    */
    public byte[] getBytes(String parameterName) throws SQLException {
        return dbCstm.getBytes(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getClob
    */
    public Clob getClob(int i) throws SQLException {
        return dbCstm.getClob(i);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getClob
    */
    public Clob getClob(String parameterName) throws SQLException {
        return dbCstm.getClob(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDate
    */
    public java.sql.Date getDate(int parameterIndex) throws SQLException {
        return dbCstm.getDate(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDate
    */
    public java.sql.Date getDate(String parameterName) throws SQLException {
        return dbCstm.getDate(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDate
    */
    public java.sql.Date getDate(int parameterIndex, java.util.Calendar cal) throws SQLException {
        return dbCstm.getDate(parameterIndex, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDate
    */
    public java.sql.Date getDate(String parameterName, java.util.Calendar cal) throws SQLException {
        return dbCstm.getDate(parameterName, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDouble
    */
    public double getDouble(int parameterIndex) throws SQLException {
        return dbCstm.getDouble(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getDouble
    */
    public double getDouble(String parameterName) throws SQLException {
        return dbCstm.getDouble(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getFloat
    */
    public float getFloat(int parameterIndex) throws SQLException {
        return dbCstm.getFloat(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getFloat
    */
    public float getFloat(String parameterName) throws SQLException {
        return dbCstm.getFloat(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getInt
    */
    public int getInt(String parameterName) throws SQLException {
        return dbCstm.getInt(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getInt
    */
    public int getInt(int parameterIndex) throws SQLException {
        return dbCstm.getInt(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getLong
    */
    public long getLong(int parameterIndex) throws SQLException {
        return dbCstm.getLong(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getLong
    */
    public long getLong(String parameterName) throws SQLException {
        return dbCstm.getLong(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getObject
    */
    public Object getObject(int parameterIndex) throws SQLException {
        return dbCstm.getObject(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getObject
    */
    public Object getObject(String parameterName) throws SQLException {
        return dbCstm.getObject(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getObject
    */
    public Object getObject(int i, java.util.Map map) throws SQLException {
        return dbCstm.getObject(i, map);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getObject
    */
    public Object getObject(String parameterName, java.util.Map map) throws SQLException {
        return dbCstm.getObject(parameterName, map);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getRef
    */
    public Ref getRef(int i) throws SQLException {
        return dbCstm.getRef(i);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getRef
    */
    public Ref getRef(String parameterName) throws SQLException {
        return dbCstm.getRef(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getShort
    */
    public short getShort(String parameterName) throws SQLException {
        return dbCstm.getShort(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getShort
    */
    public short getShort(int parameterIndex) throws SQLException {
        return dbCstm.getShort(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getString
    */
    public String getString(String parameterName) throws SQLException {
        return dbCstm.getString(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getString
    */
    public String getString(int parameterIndex) throws SQLException {
        return dbCstm.getString(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTime
    */
    public java.sql.Time getTime(String parameterName) throws SQLException {
        return dbCstm.getTime(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTime
    */
    public java.sql.Time getTime(int parameterIndex) throws SQLException {
        return dbCstm.getTime(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTime
    */
    public java.sql.Time getTime(String parameterName, java.util.Calendar cal) throws SQLException {
        return dbCstm.getTime(parameterName, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTime
    */
    public java.sql.Time getTime(int parameterIndex, java.util.Calendar cal) throws SQLException {
        return dbCstm.getTime(parameterIndex, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTimestamp
    */
    public java.sql.Timestamp getTimestamp(String parameterName) throws SQLException {
        return dbCstm.getTimestamp(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTimestamp
    */
    public java.sql.Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return dbCstm.getTimestamp(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTimestamp
    */
    public java.sql.Timestamp getTimestamp(String parameterName, java.util.Calendar cal) throws SQLException {
        return dbCstm.getTimestamp(parameterName, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getTimestamp
    */
    public java.sql.Timestamp getTimestamp(int parameterIndex, java.util.Calendar cal) throws SQLException {
        return dbCstm.getTimestamp(parameterIndex, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getURL
    */
    public java.net.URL getURL(int parameterIndex) throws SQLException {
        return dbCstm.getURL(parameterIndex);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#getURL
    */
    public java.net.URL getURL(String parameterName) throws SQLException {
        return dbCstm.getURL(parameterName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        dbCstm.registerOutParameter(parameterIndex, sqlType);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        dbCstm.registerOutParameter(parameterName, sqlType);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        dbCstm.registerOutParameter(parameterName, sqlType, typeName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(int paramIndex, int sqlType, int scale) throws SQLException {
        dbCstm.registerOutParameter(paramIndex, sqlType, scale);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        dbCstm.registerOutParameter(parameterName, sqlType, scale);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#registerOutParameter
    */
    public void registerOutParameter(int paramIndex, int sqlType, String typeName) throws SQLException {
        dbCstm.registerOutParameter(paramIndex, sqlType, typeName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setAsciiStream
    */
    public void setAsciiStream(String parameterName, java.io.InputStream x, int length) throws SQLException {
        dbCstm.setAsciiStream(parameterName, x, length);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setBigDecimal
    */
    public void setBigDecimal(String parameterName, java.math.BigDecimal x) throws SQLException {
        dbCstm.setBigDecimal(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setBinaryStream
    */
    public void setBinaryStream(String parameterName, java.io.InputStream x, int length) throws SQLException {
        dbCstm.setBinaryStream(parameterName, x, length);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setBoolean
    */
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        dbCstm.setBoolean(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setByte
    */
    public void setByte(String parameterName, byte x) throws SQLException {
        dbCstm.setByte(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setBytes
    */
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        dbCstm.setBytes(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setCharacterStream
    */
    public void setCharacterStream(String parameterName, java.io.Reader reader, int length) throws SQLException {
        dbCstm.setCharacterStream(parameterName, reader, length);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setDate
    */
    public void setDate(String parameterName, java.sql.Date x) throws SQLException {
        dbCstm.setDate(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setDate
    */
    public void setDate(String parameterName, java.sql.Date x, java.util.Calendar cal) throws SQLException {
        dbCstm.setDate(parameterName, x, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setDouble
    */
    public void setDouble(String parameterName, double x) throws SQLException {
        throw new SQLException("not supported");
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setFloat
    */
    public void setFloat(String parameterName, float x) throws SQLException {
        dbCstm.setFloat(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setInt
    */
    public void setInt(String parameterName, int x) throws SQLException {
        dbCstm.setInt(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setLong
    */
    public void setLong(String parameterName, long x) throws SQLException {
        dbCstm.setLong(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setNull
    */
    public void setNull(String parameterName, int sqlType) throws SQLException {
        dbCstm.setNull(parameterName, sqlType);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setNull
    */
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        dbCstm.setNull(parameterName, sqlType, typeName);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setObject
    */
    public void setObject(String parameterName, Object x) throws SQLException {
        dbCstm.setObject(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setObject
    */
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        dbCstm.setObject(parameterName, x, targetSqlType);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setObject
    */
   public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        dbCstm.setObject(parameterName, x, targetSqlType);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setShort
    */
    public void setShort(String parameterName, short x) throws SQLException {
        dbCstm.setShort(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setString
    */
    public void setString(String parameterName, String x) throws SQLException {
        dbCstm.setString(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setTime
    */
    public void setTime(String parameterName, java.sql.Time x) throws SQLException {
        dbCstm.setTime(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setTime
    */
    public void setTime(String parameterName, java.sql.Time x, java.util.Calendar cal) throws SQLException {
        dbCstm.setTime(parameterName, x, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setTimestamp
    */
    public void setTimestamp(String parameterName, java.sql.Timestamp x) throws SQLException {
        dbCstm.setTimestamp(parameterName, x);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setTimestamp
    */
    public void setTimestamp(String parameterName, java.sql.Timestamp x, java.util.Calendar cal) throws SQLException {
        dbCstm.setTimestamp(parameterName, x, cal);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#setURL
    */
    public void setURL(String parameterName, java.net.URL val) throws SQLException {
        dbCstm.setURL(parameterName, val);
    }
    
    /**
    * Implements method in interface java.sql.CallableStatement
    * @see java.sql.CallableStatement#wasNull
    */
    public boolean wasNull() throws SQLException {
        return dbCstm.wasNull();
    }

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String value)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, Blob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(String arg0, InputStream arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Clob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, NClob arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(String arg0, Reader arg1, long arg2)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(String arg0, String arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRowId(String arg0, RowId arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(String arg0, SQLXML arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}