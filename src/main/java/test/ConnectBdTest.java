package test;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import junit.framework.*;
import dao.ConnectBd;

class ConnectBdTest extends TestCase{

	@Test
    void testConnecte() throws SQLException {
        ConnectBd.connecte();
        assertNotNull(ConnectBd.con);
    }

}
