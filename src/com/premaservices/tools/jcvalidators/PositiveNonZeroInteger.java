/**
 * Copyright (C) 2015 premaservices.com
 */

package com.premaservices.tools.jcvalidators;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

/**
 * A validator that makes sure the value of the parameter is a positive and non-zero integer.
 *
 * @author Anna Semyonova <support@premaservices.com>
 */
public class PositiveNonZeroInteger implements IParameterValidator {

  public void validate(String name, String value) throws ParameterException {
	  
    int n = Integer.parseInt(value);
    if (n <= 0) {
      throw new ParameterException("Parameter " + name
          + " should be positive and non-zero (found " + value +")");
    }
    
  }

}
