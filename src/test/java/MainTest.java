import DB.Executor;
import junit.framework.TestCase;
import org.junit.Test;
import readfile.*;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;

public class MainTest extends TestCase {

    @Test
    public void testGetList() throws SQLException {
        readfile foo2 = new readfile();
        Executor exc = new Executor(foo2.read(), "postgres", "postgres");
        assertNotEquals(null, exc.submit("SELECT * FROM sch.rel"));
    }
}