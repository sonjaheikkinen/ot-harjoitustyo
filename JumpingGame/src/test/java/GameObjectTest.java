/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import domain.GameObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sonja
 */
public class GameObjectTest {
    
    public GameObjectTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void intersectsReturnsFalseIfObjectsDontIntersect() {
        GameObject rectangle1 = new GameObject(0, 0, 10, 10, 0, 0);
        GameObject rectangle2 = new GameObject(300, 300, 10, 10, 0, 0);
        assertTrue(rectangle1.intersects(rectangle2) == false);
    }
}
