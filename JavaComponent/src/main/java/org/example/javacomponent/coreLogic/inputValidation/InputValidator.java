package org.example.javacomponent.coreLogic.inputValidation;

import java.util.List;

public abstract class InputValidator {
    /* implemented using abstraction */
    abstract boolean validate(String inputValue, List errorArrayLst, int counter);
}
