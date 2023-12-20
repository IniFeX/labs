import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import webapp.ee.FindAverageAge;

import static org.junit.Assert.assertNotNull;

public class FindAverageAgeTest {
    private FindAverageAge findAverageAge;

    @BeforeEach
    public void setUp() {
        this.findAverageAge = new FindAverageAge();
    }

    @Test
    public void findAverageAgeTest() {
        this.findAverageAge = new FindAverageAge();
        double averageAge = this.findAverageAge.getAge(200, 2);
        Assert.assertEquals(100, averageAge, 0.0001);
    }

    @Test
    public void divideByZero() {
        this.findAverageAge = new FindAverageAge();
        Assert.assertThrows(ArithmeticException.class, () -> this.findAverageAge.getAge(200, 0));
    }

    @After
    public void setNullObj() {
        this.findAverageAge = null;
    }
}

