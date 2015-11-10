package com.fortmocks.core.model;

/**
 * @author Karl Dahlgren
 * @since 1.0
 */
public class Result<O extends Output> {

    private O output;

    public Result(O output) {
        this.output = output;
    }

    public O getOutput() {
        return output;
    }

    public void setOutput(O output) {
        this.output = output;
    }
}
