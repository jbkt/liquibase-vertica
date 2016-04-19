package liquibase.ext.vertica.sqlgenerator;

import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.ext.vertica.database.VerticaDatabase;
import liquibase.ext.vertica.statement.DropPrimaryKeyStatementVertica;
import liquibase.ext.vertica.statement.DropProjectionStatement;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;
import liquibase.statement.core.DropPrimaryKeyStatement;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Table;

/**
 * Drop Primary key for Vertica.
 *
 * author: Chris Brookes
 */
public class DropPrimaryKeyGenerator extends AbstractSqlGenerator<DropPrimaryKeyStatementVertica>
{
    @Override
    public ValidationErrors validate(DropPrimaryKeyStatementVertica dropPrimaryKeyStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField("tableName", dropPrimaryKeyStatement.getTableName());
        validationErrors.checkRequiredField("constraintName", dropPrimaryKeyStatement.getConstraintName());

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(DropPrimaryKeyStatementVertica statement, Database database, SqlGeneratorChain sqlGeneratorChain)
    {
        String sql;

        sql = "ALTER TABLE " + database.escapeTableName(statement.getCatalogName(), statement.getSchemaName(),
                statement.getTableName()) + " DROP CONSTRAINT " + database.escapeConstraintName(statement.getConstraintName());
        return new Sql[] {
                new UnparsedSql(sql, getAffectedPrimaryKey(statement))
        };
    }

    protected PrimaryKey getAffectedPrimaryKey(DropPrimaryKeyStatement statement) {
        return new PrimaryKey().setName(statement.getConstraintName()).setTable((Table) new Table().setName(statement.getTableName()).setSchema(statement.getCatalogName(), statement.getSchemaName()));
    }

    @Override
    public boolean supports(DropPrimaryKeyStatementVertica statement, Database database)
    {
        return database instanceof VerticaDatabase;
    }
}
