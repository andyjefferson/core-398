package mydomain.model;

import javax.jdo.annotations.*;
import java.util.*;
import org.datanucleus.util.*;

@PersistenceCapable(detachable="true")
public class Person
{
    @PrimaryKey
    Long id;

    String name;

    @Join
    Map<String, String> details = new HashMap<>();

    public Person(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setDetails(Map newValue)
    {
//      this.details.clear();
//      this.details.putAll(newValue);

        this.details = new HashMap(newValue);
    }
    public Map getDetails()
    {
        return details;
    }
}
