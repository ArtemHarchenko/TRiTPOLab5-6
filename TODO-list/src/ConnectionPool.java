
import org.apache.commons.dbcp.*;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.sql.Connection;


public class ConnectionPool {
    protected GenericObjectPool connectionPool;

    public void initConnectionPool(String driver, String dbURL, String dbUser, String dbPswd) {
        try {
            Class.forName(driver).newInstance();                          //создаем новый объект класса драйвера,
            //тем самым инициализируя его

            connectionPool = new GenericObjectPool(null);                   //создаем GenericObjectPool
            //создаем connection factory ("фабрика соединений" - объект который будет создавать соединения)

            ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(dbURL, dbUser, dbPswd);
            new PoolableConnectionFactory(connectionFactory, connectionPool, null, "SELECT 1", false, true);        //создаем PoolableConnectionFactory
            new PoolingDataSource(connectionPool);
            connectionPool.setMaxIdle(20);                                 //устанавливаем максимальное кол-во простаивающих соединений
            connectionPool.setMaxIdle(300);                                //устанавилваем макс. кол-во активных соединений
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Функция получения connection из пула
     *
     * @return Connection
     */
    public final Connection getConnection() {
        try {
            if (connectionPool.getMaxIdle() <= connectionPool.getNumActive()) {
                System.err.println("Connections limit is over!!!");
            }

            Connection con = (Connection) connectionPool.borrowObject();
            return con;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Функция возвращения connection в пул
     */
    public final void returnConnection(Connection con) {
        if (con == null) {
            System.err.println("Returning NULL to pool!!!");
            return;
        }

        try {
            connectionPool.returnObject(con);
        } catch (Exception ex) {
        }
    }
}