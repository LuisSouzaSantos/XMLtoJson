package br.com.ftt.ec6.xmlToJson;

import java.util.List;

public class Stack {
	
    public List<String> stack;
    public int stackPosition;

    public Stack() {
        this.stackPosition = -1;
    }

    public boolean stackEmply() {
        return stack.isEmpty();
    }

    public int size() {
        if (this.stackEmply()) {
            return 0;
        }
        return this.stackPosition + 1;
    }

    public String showLastValue() {
        if (this.stackEmply()) {
            return null;
        }
        return this.stack.get(stackPosition);
    }

    public Object stackDown() {
        if (stackEmply()) {
            return null;
        }
        return this.stack.remove(stackPosition--);
    }

    public void stackUp(String value) {
        if (this.stackPosition < this.stack.size() - 1) {
            this.stack.add(value);
            ++stackPosition;
        }
    }

}
