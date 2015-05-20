package com.example.smoll;

public class OperAtionAdd implements Operation
{
	private int a = 0;
	private int b = 0;

	@Override
	public void setA(int a)
	{
		this.a = a;
	}

	@Override
	public void setB(int b)
	{
		this.b = b;

	}

	@Override
	public int getRusult()
	{
		int result = 1;
		result = a + b;
		return result;
	}

}
