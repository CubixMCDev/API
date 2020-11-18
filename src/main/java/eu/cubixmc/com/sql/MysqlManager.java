package eu.cubixmc.com.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.cubixmc.com.CubixAPI;
import eu.cubixmc.com.sql.controllers.ManipulationMysqlController;
import eu.cubixmc.com.sql.controllers.MysqlController;
import eu.cubixmc.com.sql.controllers.RequestMysqlController;

import javax.sql.rowset.CachedRowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlManager {

    private final CubixAPI plugin;
    private HikariDataSource source;

    public MysqlManager(CubixAPI plugin) {
        this.plugin = plugin;
        try {
            String host = plugin.getConfig().getString("data.host");
            String user = plugin.getConfig().getString("data.username");
            String pass = plugin.getConfig().getString("data.password");
            String database = plugin.getConfig().getString("data.database");
            int port = plugin.getConfig().getInt("data.port");
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&allowMultiQueries=true&characterEncoding=utf-8&serverTimezone=UTC&useSSL=false");
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.setUsername(user);
            config.setPassword(pass);
            config.setConnectionTimeout(8000);
            config.addDataSourceProperty("cachePrepStmts", "true");
            source = new HikariDataSource(config);
            System.out.println("CONNECTION TO DATABASE IS SUCCESSFUL");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        this.source.close();
    }

    public void performAsyncQuery(String query, QueryCallback successCallback, Object... replacements) {
        performAsyncQuery(query, successCallback, null, replacements);
    }

    public void performAsyncQuery(String query, QueryCallback successCallback, ExceptionCallback exceptionCallback, Object... replacements) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> performQuery(query, successCallback, exceptionCallback, replacements));
    }

    public CachedRowSet performQuery(String query, Object... replacements) {
        return performQuery(query, null, replacements);
    }

    public void performQuery(String query, QueryCallback successCallback, ExceptionCallback exceptionCallback, Object... replacements) {
        CachedRowSet set = performQuery(query, exceptionCallback, replacements);

        if (set != null) {
            try {
                successCallback.accept(set);
            } catch (SQLException ex) {
                try {
                    if (exceptionCallback != null) {
                        exceptionCallback.accept(ex);
                    }

                    ex.printStackTrace();
                } catch (CancelExceptionSignal ex2) {

                }
            }
        }
    }

    public CachedRowSet performQuery(String query, ExceptionCallback exceptionCallback, Object... replacements) {
        try (Connection con = source.getConnection()) {
            PreparedStatement s = con.prepareStatement(query);

            int i = 0;

            for (Object replacement : replacements) {
                s.setObject(++i, replacement);
            }

            ResultSet set = s.executeQuery();
            CachedRowSet cachedSet = new FixedCachedRowSetImpl();

            cachedSet.populate(set);

            return cachedSet;
        } catch (SQLException ex) {
            try {
                if (exceptionCallback != null) {
                    exceptionCallback.accept(ex);
                }

                ex.printStackTrace();
            } catch (CancelExceptionSignal ex2) {

            }
        }

        return null;
    }

    public void performAsyncUpdate(String query, Object... replacements) {
        performAsyncUpdate(query, null, null, replacements);
    }

    public void performAsyncUpdate(String query, Runnable successCallback, Object... replacements) {
        performAsyncUpdate(query, successCallback, null, replacements);
    }

    public void performAsyncUpdate(String query, ExceptionCallback exceptionCallback, Object... replacements) {
        performAsyncUpdate(query, null, exceptionCallback, replacements);
    }

    public void performAsyncUpdate(String query, Runnable successCallback, ExceptionCallback exceptionCallback, Object... replacements) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> performUpdate(query, successCallback, exceptionCallback, replacements));
    }

    public void performUpdate(String query, Object... replacements) {
        performUpdate(query, null, null, replacements);
    }

    public void performUpdate(String query, Runnable successCallback, Object... replacements) {
        performUpdate(query, successCallback, null, replacements);
    }

    public void performUpdate(String query, ExceptionCallback exceptionCallback, Object... replacements) {
        performUpdate(query, null, exceptionCallback, replacements);
    }

    public void performUpdate(String query, Runnable successCallback, ExceptionCallback exceptionCallback, Object... replacements) {
        try (Connection con = source.getConnection()) {
            PreparedStatement s = con.prepareStatement(query);

            int i = 0;

            for (Object replacement : replacements) {
                s.setObject(++i, replacement);
            }

            s.executeUpdate();

            if (successCallback != null) {
                successCallback.run();
            }
        } catch (SQLException ex) {
            try {
                if (exceptionCallback != null) {
                    exceptionCallback.accept(ex);
                }

                ex.printStackTrace();
            } catch (CancelExceptionSignal ex2) {

            }
        }
    }

    @Deprecated
    public Map<Integer, List<Object>> executeQuery(String query, Object... replacements) {
        return executeQuery(query, null, replacements);
    }

    @Deprecated
    public Map<Integer, List<Object>> executeQuery(String query, MysqlController controller, Object... replacements) {
        Map<Integer, List<Object>> data = new HashMap<>();

        PreparedStatement s = null;
        Connection con = null;

        try {
            con = source.getConnection();
            s = con.prepareStatement(query);

            int i = 0;

            for (Object replacement : replacements) {
                s.setObject(++i, replacement);
            }

            if (query.startsWith("SELECT") || query.startsWith("SHOW COLUMNS")) {
                ResultSet set = null;

                try {
                    set = s.executeQuery();

                    if (controller == null) {
                        ResultSetMetaData metadata = set.getMetaData();

                        int columnCount = metadata.getColumnCount();

                        for (i = 0; i < columnCount; i++) {
                            data.put(i, new ArrayList<>());
                        }

                        while (set.next()) {
                            for (i = 1; i <= columnCount; i++) {
                                data.get(i - 1).add(set.getObject(i));
                            }
                        }
                    } else {
                        if (controller instanceof RequestMysqlController) {
                            RequestMysqlController requestController = (RequestMysqlController) controller;

                            requestController.onCallback(set);
                        }
                    }
                } catch (SQLException ex) {
                    if (controller == null) {
                        ex.printStackTrace();
                    } else {
                        controller.onSQLException(ex);
                    }
                } finally {
                    try {
                        if (set != null) {
                            set.close();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                s.execute();

                if (controller instanceof ManipulationMysqlController) {
                    ManipulationMysqlController manipulationController = (ManipulationMysqlController) controller;

                    manipulationController.onSuccess();
                }
            }
        } catch (SQLException ex) {
            if (controller == null) {
                ex.printStackTrace();
            } else {
                controller.onSQLException(ex);
            }
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return data;
    }

    @Deprecated
    public void executeAsyncQuery(String query, Object... replacements) {
        executeAsyncQuery(query, null, replacements);
    }

    @Deprecated
    public void executeAsyncQuery(String query, MysqlController controller, Object... replacements) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> executeQuery(query, controller, replacements));
    }

    public static class CancelExceptionSignal extends RuntimeException {

        public final CancelExceptionSignal INSTANCE = new CancelExceptionSignal();
        private final long serialVersionUID = -653468396254880100L;

        private CancelExceptionSignal() {
        }
    }
}
