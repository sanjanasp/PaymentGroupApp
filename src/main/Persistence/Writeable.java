package Persistence;

import org.json.JSONObject;

// Modeled from sample application provided by CPSC 210
public interface Writeable {
    JSONObject toJson();
}
