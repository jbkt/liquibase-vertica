package liquibase.ext.vertica.change;

import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.change.core.DropPrimaryKeyChange;
import liquibase.database.Database;
import liquibase.ext.vertica.database.VerticaDatabase;
import liquibase.ext.vertica.statement.DropPrimaryKeyStatementVertica;
import liquibase.statement.SqlStatement;

/**
 * Drop Primary Key Change for Vertica.
 *
 * author: Chris Brookes
 */
@DatabaseChange(name="dropPrimaryKey", description = "Drops an existing primary key", priority = ChangeMetaData.PRIORITY_DATABASE, appliesTo = "primaryKey")
public class DropPrimaryKeyChangeVertica extends DropPrimaryKeyChange
{
    @Override
    public SqlStatement[] generateStatements(Database database) {

        return new SqlStatement[]{
                new DropPrimaryKeyStatementVertica(getCatalogName(), getSchemaName(), getTableName(), getConstraintName()),
        };
    }

    @Override
    public boolean supports(Database database) {
        return database instanceof VerticaDatabase;
    }

    @Override
    public String getConfirmationMessage() {
        return "Primary key " + getConstraintName() + " dropped";
    }
}
