/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class LogInControllerTest {
    
    public LogInControllerTest() {
    }

    /**
     * Test of initStage method, of class LogInController.
     */
    @org.junit.Test
    public void testInitStage() {
        System.out.println("initStage");
        Parent root = null;
        LogInController instance = new LogInController();
        instance.initStage(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStage method, of class LogInController.
     */
    @org.junit.Test
    public void testGetStage() {
        System.out.println("getStage");
        LogInController instance = new LogInController();
        Stage expResult = null;
        Stage result = instance.getStage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStage method, of class LogInController.
     */
    @org.junit.Test
    public void testSetStage() {
        System.out.println("setStage");
        Stage stage = null;
        LogInController instance = new LogInController();
        instance.setStage(stage);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
