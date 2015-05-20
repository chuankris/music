package com.example.smoll;

public class FactoryOperation
{
	public static Operation CreateOperation(String operation)
	{
		Operation op = null;
		switch (operation)
		{
		case "+":
			op = new OperAtionAdd();
			break;
		case "-":
			op = new OperAtionSub();
			break;
		default:
			break;
		}
		return op;
	}

}
