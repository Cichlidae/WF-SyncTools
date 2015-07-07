package com.philipp.tools.best;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Stack;

import com.philipp.tools.best.db.IMetaDataExtractor;
import com.philipp.tools.best.db.JDBCConnector;
import com.philipp.tools.best.db.JDBCMetaDataExtractor;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.log.Logger;

public class JDBCSQLManager extends AbstractSQLManager {

	Connection connection = null;	
	
	private Stack<String> commandStack = new Stack<String>();
	
	protected JDBCSQLManager(Output out) {
		super(out);
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	protected void openConnection(StdinCommand incom) throws SQLException {
		connection = JDBCConnector.getJDBCConnection(incom);
	}

	@Override
	protected void closeConnection() throws SQLException {
		if (connection != null) connection.close();
	}

	@Override
	protected void doQuery(String sql, String table) throws SQLException, IOException {
		
		sql = sql.trim();
		if ("".equals(sql)) return;
		
		Logger.debug(sql + "\n");
		
		Statement statement = connection.createStatement();
		
		while (true) {	
			switch (checkResultSelect(sql)) {
				case COMMENT: {
					String[] matched = matchComment(sql);
					if (matched != null) {						
						commandStack.push(COMMENT_MARKER + matched[0]);
						if (matched[1] != null) {
							sql = matched[1];
							continue;
						}			
						break;
					}	
					else {									
						commandStack.push(sql);
					}
					break;
				}
				case DROP: {	
					if (exists(table)) {					
						statement.executeUpdate(sql);				
					}	
					break;
				}
				case UPDATE: { 
					int count = statement.executeUpdate(sql);
					String note = DEFAULT_RESULT_NAME;				
					
					if (!commandStack.empty()) { 
						note = parseComment(commandStack.pop(), null);
					}
										
					out.printRS(note, String.valueOf(count));
					commandStack.clear();
				    break;
				}
				case SELECT:
				default: {
					ResultSet rs = statement.executeQuery(sql);
					String note = DEFAULT_RESULT_NAME;				
					
					if (!commandStack.empty()) { 
						note = parseComment(commandStack.pop(), null);
					}
					
					if (!cancelComment(note)) out.printRS(note, rs);
					commandStack.clear();
				}
			}
			break;
		}
		statement.close();
	}

	@Override
	protected boolean exists(String table) throws SQLException {
		List<String> tables = new JDBCMetaDataExtractor(connection).getTables("*");
		if (tables.contains(table)) return true;
		return false;
	}

	@Override
	protected IMetaDataExtractor getMetaDataExtractor() throws SQLException {		
		return new JDBCMetaDataExtractor(connection);
	}

}
