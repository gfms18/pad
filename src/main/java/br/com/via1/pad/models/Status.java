package br.com.via1.pad.models;

public enum Status {
	OK ("ok"),
	A_CORRIGIR("a corrigir");
	
	
	private final String displayValue;
	
	private Status(String displayValue) {
        this.displayValue = displayValue;
    }
    
    public String getDisplayValue() {
        return displayValue;
    }
}
