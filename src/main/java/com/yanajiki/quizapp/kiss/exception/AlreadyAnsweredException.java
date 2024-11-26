package com.yanajiki.quizapp.kiss.exception;

public class AlreadyAnsweredException extends RuntimeException {
    public AlreadyAnsweredException(String message) {
        super(message);
    }

    public AlreadyAnsweredException() {
        super();
    }
}
