package com.example.demo.controller;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class WasherMachineControllerTest {
    //todo maybe we should pick random values in mode selection
    @Autowired
    private WasherMachineController washerMachineController = new WasherMachineController();
    private Map<String, String> testMode = new HashMap<String, String>() {{
        put("description", "Test mode description");
        put("time", "1920");
        put("temperature", "233");
    }};

    @Test
    void listOfModes() {
        Assert.assertNotNull(washerMachineController.listOfModes());
    }

    @Test
    void getMode() {
        Assert.assertNotNull(washerMachineController.getMode("1"));
        Exception exception = null;
        try {
            washerMachineController.getMode("1337");
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    void createCustomMode() {
        washerMachineController.createCustomMode(testMode);
        Assert.assertNotNull(washerMachineController.getMode("4"));
    }

    @Test
    void customiseMode() {
        Map<String, String> oldMode = washerMachineController.getMode("1");
        Map<String, String> newMode = washerMachineController.customiseMode("1", testMode);
        Assert.assertTrue(new ArrayList<>(oldMode.values()).equals(new ArrayList<>(newMode.values())));
    }

    @Test
    void deleteMode() {
        washerMachineController.deleteMode("3");
        Exception exception = null;
        try {
            washerMachineController.getMode("3");
        } catch (Exception e) {
            exception = e;
        }
        Assert.assertNotNull(exception);
    }

    @Test
    void turnOn() {
        washerMachineController.turnOn("1");
        Assert.assertTrue(washerMachineController.getSwitcherStatus());
    }

    @Test
    void turnOff() {
        washerMachineController.turnOff();
        Assert.assertFalse(washerMachineController.getSwitcherStatus());
    }

    @Test
    void timeLeft() {
        washerMachineController.turnOn("1");
        long firstTimeCheck = washerMachineController.timeLeft();
        Assert.assertNotEquals(0, washerMachineController.timeLeft());
        long secondTimeCheck = washerMachineController.timeLeft();
        Assert.assertNotEquals(firstTimeCheck, secondTimeCheck);
    }
}