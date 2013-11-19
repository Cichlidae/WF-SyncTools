package com.philipp.tools.best;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.jacob.impl.ado.Command;
import com.jacob.impl.ado.CommandTypeEnum;
import com.jacob.impl.ado.Connection;
import com.jacob.impl.ado.Recordset;
import com.philipp.tools.best.db.ADOConnector;
import com.philipp.tools.best.db.ADOMetaDataExtractor;
import com.philipp.tools.best.db.IMetaDataExtractor;
import com.philipp.tools.best.in.StdinCommand;
import com.philipp.tools.best.out.Output;
import com.philipp.tools.common.log.Logger;

public class ADOSQLManager extends AbstractSQLManager {
	
	private Connection connection = null;
	
	private Stack<String> commandStack = new Stack<String>();
	
	public ADOSQLManager (Connection connection, Output out) {
		super(out);
		this.connection = connection;	
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	protected void openConnection(StdinCommand incom) {
		
		connection = ADOConnector.getADOConnection(incom);
		Logger.debug("Driver version: " + connection.getVersion());
	}

	@Override
	protected void closeConnection() {
		if (connection != null) connection.Close();
	}

	@Override
	protected void doQuery(String sql, String table) {
		
		sql = sql.trim();
		if ("".equals(sql)) return;
		
		Logger.debug(sql + "\n");
		
		Command comm = new Command();
		comm.setActiveConnection(connection);
		comm.setCommandType(CommandTypeEnum.adCmdText);

		while (true) {
			comm.setCommandText(sql);
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
						comm.Execute();
					}	
					break;
				}
				case UPDATE: comm.Execute(); break;
				case SELECT:
				default: {
					Recordset rs = comm.Execute();	
					String note = DEFAULT_RESULT_NAME;
					List<String> handlers = new ArrayList<String>(0);
				
					if (!commandStack.empty()) { 						
						note = parseComment(commandStack.pop(), handlers);						
					}
					
					if (!cancelComment(note)) out.printRS(note, rs, handlers);
					commandStack.clear();
				}
			}
			break;
		}
		comm.Cancel();
	}
	
	@Override
	protected boolean exists (String table) {	
		List<String> tables = new ADOMetaDataExtractor(connection).getTables("");
		if (tables.contains(table)) return true;		
		return false;
	}

	@Override
	protected IMetaDataExtractor getMetaDataExtractor() {		
		return new ADOMetaDataExtractor(connection);
	}

}
