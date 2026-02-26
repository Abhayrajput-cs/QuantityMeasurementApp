package com.example;

public class App {
	public static void main(String[] args) {

	    QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
	    QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
	    System.out.println("Equality: " +w1.equals(w2));
	    System.out.println("Convert to Pound: "+w1.convertTo(WeightUnit.POUND));
	    System.out.println("Addition (kg): "+w1.add(w2));
	    System.out.println("Addition (grams): " + w1.add(w2, WeightUnit.GRAM));
	}

}
