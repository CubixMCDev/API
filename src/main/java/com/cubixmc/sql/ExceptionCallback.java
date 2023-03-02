package com.cubixmc.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface ExceptionCallback {

    void accept(SQLException ex);
}
