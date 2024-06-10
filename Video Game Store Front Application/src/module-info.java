/**
 * 
 */
/**
 * 
 */
module CST239_Milestone {
    opens App to com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.annotation;
	requires junit;
	exports App;
	
}