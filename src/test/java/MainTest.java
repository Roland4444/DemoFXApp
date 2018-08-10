import DB.Executor;
import junit.framework.TestCase;
import org.junit.Test;
import readfile.*;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;

public class MainTest extends TestCase {

    @Test
    public void testGetList() throws SQLException {
        Main m = new Main();
        readfile foo2 = new readfile();
        m.setExec(new Executor(foo2.read(),"postgres","postgres"));
        assertNotEquals(null, m.getDataList());
    }
}