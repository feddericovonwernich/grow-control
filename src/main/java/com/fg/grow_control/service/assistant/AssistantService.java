package com.fg.grow_control.service.assistant;

public interface AssistantService {

    /**
     * Is able to process a user request using underlying AI model.
     *
     * @param userInput The input provided by the user.
     * @return The processed string response.
     */
    String processRequest(String userInput);

    /**
     * Is able to process a user request using underlying AI model. Follow the conversation from the given thread id.
     *
     * @param userInput The input provided by the user.
     * @param threadId  The identifier for the processing thread.
     * @return The processed string response.
     */
    String processRequest(String userInput, String threadId);
}
