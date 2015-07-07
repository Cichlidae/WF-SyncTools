package com.jacob.impl.ado;

public interface ParameterDirectionEnum {
	
	public static final int adParamInput = 1; //Default. Indicates that the parameter represents an input parameter.
	public static final int adParamInputOutput = 3; //Indicates that the parameter represents both an input and output parameter.
	public static final int adParamOutput = 2; //Indicates that the parameter represents an output parameter.
	public static final int adParamReturnValue = 4; //Indicates that the parameter represents a return value.
	public static final int adParamUnknown = 0; //Indicates that the parameter direction is unknown.

}
