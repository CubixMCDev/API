package eu.cubixmc.com.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface ExceptionCallback {

    void accept(SQLException ex);
}
