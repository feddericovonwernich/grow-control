package com.fg.grow_control.service.assistant;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ToolParameterAware {

    List<Object> getParametersForFunction(String functionName, String parametersString);

    @NotNull
    static Long getIdParameter(String parametersString) {
        Gson gson = new Gson();
        JsonObject jsonObj = gson.fromJson(parametersString, JsonObject.class);
        Long id = jsonObj.get("id").getAsLong();
        return id;
    }

}
