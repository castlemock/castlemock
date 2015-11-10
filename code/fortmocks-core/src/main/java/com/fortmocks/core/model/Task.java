package com.fortmocks.core.model;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class Task<I extends Input> {

    private I input;
    private String executer;

    public I getInput() {
        return input;
    }

    public void setInput(I input) {
        this.input = input;
    }

    public String getExecuter() {
        return executer;
    }

    public void setExecuter(String executer) {
        this.executer = executer;
    }
}
