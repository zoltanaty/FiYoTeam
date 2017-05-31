import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.fiyoteam.rest.UserService;

public class UserServiceTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		UserService userService = new UserService();
		assertNotNull(userService.testRest());
	}

}
