package liquibase.ext.vertica.statement;

import liquibase.statement.core.DropPrimaryKeyStatement;

/**
 * Drop Primary Key Statement for Vertica.
 *
 * author: Chris Brookes
 */
public class DropPrimaryKeyStatementVertica extends DropPrimaryKeyStatement
{
    public DropPrimaryKeyStatementVertica(String catalogName, String schemaName, String tableName, String constraintName)
    {
        super(catalogName, schemaName, tableName, constraintName);
    }
}
