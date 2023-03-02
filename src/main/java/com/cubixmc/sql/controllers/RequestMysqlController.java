package com.cubixmc.sql.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

@Deprecated
public interface RequestMysqlController extends MysqlController {

    void onCallback(ResultSet set) throws SQLException;
}
