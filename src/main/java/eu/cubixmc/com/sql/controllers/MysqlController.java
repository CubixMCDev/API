package eu.cubixmc.com.sql.controllers;

import java.sql.SQLException;

@Deprecated
public interface MysqlController {

    void onSQLException(SQLException ex);
}
