package org.datanucleus.test;

import java.util.*;
import org.junit.*;
import javax.jdo.*;

import static org.junit.Assert.*;
import mydomain.model.*;
import org.datanucleus.util.*;

public class SimpleTest
{
    @Test
    public void testSimple()
    {
        NucleusLogger.GENERAL.info(">> test START");
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("MyTest");

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try
        {
            tx.begin();

            Person p = new Person(1, "First");
            Map<String, String> theMap = new HashMap();
            theMap.put("1", "First");
            theMap.put("2", "Second");
            p.setDetails(theMap);
            NucleusLogger.GENERAL.info(">> pm.makePersistent");
            pm.makePersistent(p);

            NucleusLogger.GENERAL.info(">> tx.commit");
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        }
        finally 
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        pmf.getDataStoreCache().evictAll();

        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try
        {
            tx.begin();

            NucleusLogger.GENERAL.info(">> pm.getObjectById");
            Person p = pm.getObjectById(Person.class, 1);
            Map<String, String> theMap = new HashMap();
            theMap.put("3", "Third");
            theMap.put("4", "Fourth");
            NucleusLogger.GENERAL.info(">> p.setDetails");
            p.setDetails(theMap);

            NucleusLogger.GENERAL.info(">> tx.commit");
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        }
        finally 
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }
        pmf.getDataStoreCache().evictAll();

        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();
        try
        {
            tx.begin();

            NucleusLogger.GENERAL.info(">> pm.getObjectById");
            Person p = pm.getObjectById(Person.class, 1);
            Map<String, String> details = p.getDetails();
            NucleusLogger.GENERAL.info(">> p.details=" + StringUtils.mapToString(details));
            assertEquals("Number of entries in the map is wrong", 2, details.size());
            assertTrue("Map should have key 3 but doesnt", details.containsKey("3"));
            assertTrue("Map should have key 4 but doesnt", details.containsKey("4"));

            NucleusLogger.GENERAL.info(">> tx.commit");
            tx.commit();
        }
        catch (Throwable thr)
        {
            NucleusLogger.GENERAL.error(">> Exception in test", thr);
            fail("Failed test : " + thr.getMessage());
        }
        finally 
        {
            if (tx.isActive())
            {
                tx.rollback();
            }
            pm.close();
        }

        pmf.close();
        NucleusLogger.GENERAL.info(">> test END");
    }
}
