package com.example.demo.controller;

import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WasherMachineController {
    private int modesNumber = 4;
    private String currentMode = "0"; //let's say first mode is default one and zero means there is no current mode and machine is off.
    private long startingTime = 0;
    private boolean switcher = false;
    private List<Map<String, String>> modes = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("description", "First mode description");
            put("time", "1");
            put("temperature", "30");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("description", "Second mode description");
            put("time", "2");
            put("temperature", "40");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("description", "Third mode description");
            put("time", "3");
            put("temperature", "50");
        }});
    }}; //in perfect world this is db.

    //api's
    @GetMapping("mode")
    public List<Map<String, String>> listOfModes() {
        return modes;
    }

    @GetMapping("mode/{id}")
    public Map<String, String> getMode(@PathVariable String id) {
        return findModeById(id);
    }

    @PostMapping("mode")
    public Map<String, String> createCustomMode(@RequestBody Map<String, String> mode) {
        mode.put("id", String.valueOf(modesNumber++));
        modes.add(mode);
        return mode;
    }

    @PutMapping("mode/{id}")
    public Map<String, String> customiseMode(@PathVariable String id, @RequestBody Map<String, String> mode) {
        Map<String, String> modeFromDB = getMode(id);
        modeFromDB.putAll(mode);
        if (modeFromDB.get("id") != id) {
            modeFromDB.put("id", id); //not sure this is the right approach, but we need to make sure we will not broke other modes with bad request.
        }
        return modeFromDB;
    }

    @DeleteMapping("mode/{id}")
    public void deleteMode(@PathVariable String id) {
        Map<String, String> mode = getMode(id);
        modes.remove(mode);
        //it is possible we want to throw exceptions here, but not sure about it.
    }

    @PutMapping("power/{id}")
    public void turnOn(@PathVariable String id) {
        switcher = true;
        currentMode = id;
        startingTime = System.currentTimeMillis();
    }

    @PutMapping("power")
    public void turnOff() {
        switcher = false;
        currentMode = "0";
    }

    @GetMapping("time")
    public long timeLeft() {
        long timeNeed = Long.valueOf(getMode(currentMode).get("time")) * 3600000; //hours to milliseconds
        long timeLeft = timeNeed - (System.currentTimeMillis() - startingTime);
        return Math.max(0, timeLeft); //if needed - we can transform time in hours + minutes.
    }


    //i'm not sure we need something like open the door api or emergency drainage in this task.

    private Map<String, String> findModeById(String id) {
        return modes.stream()
                .filter(modes -> modes.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public boolean getSwitcherStatus() {
        return switcher;
    }
}
