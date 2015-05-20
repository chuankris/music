package com.example.smoll;

public class OperAtionSub implements Operation
{

	private int a;
	private int b;

	@Override
	public void setA(int a)
	{
		this.a = a;
	}

	@Override
	public void setB(int b)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getRusult()
	{
		return a - b;
	}

}
