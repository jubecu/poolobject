/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Juan Francisco Benito Cuesta
 *
 */
public class ReusablePoolTest {

	/**
	 * Pool que usaremos para los test
	 */
	ReusablePool pool;
	/**
	 * Objetos de tipo Reusable que gestiona el Pool
	 */
	Reusable r1, r2, r3;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		pool = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		pool = null;
		r1 = null;
		r2 = null;
		r3 = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertEquals(pool, ReusablePool.getInstance());
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() throws Exception {
		r1 = pool.acquireReusable();
		r2 = pool.acquireReusable();
		try {
			r3 = pool.acquireReusable();
			assertEquals(r3, null);
		} catch (NotFreeInstanceException e) {

		}
		pool.releaseReusable(r1);
		pool.releaseReusable(r2);
		assertEquals(r2, pool.acquireReusable());
		assertEquals(r1, pool.acquireReusable());
	}

	/**
	 * Test method for
	 * {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() throws Exception {
		boolean saltoExcepcionR1 = false;
		boolean saltoExcepcionR2 = false;
		r1 = pool.acquireReusable();
		r2 = pool.acquireReusable();
		pool.releaseReusable(r1);
		try {
			pool.releaseReusable(r1);
		} catch (DuplicatedInstanceException excep) {
			saltoExcepcionR1 = true;
		}
		try {
			pool.releaseReusable(r2);
		} catch (DuplicatedInstanceException excep) {
			saltoExcepcionR2 = true;
		}
		assertTrue(saltoExcepcionR1);
		assertFalse(saltoExcepcionR2);
	}

}
