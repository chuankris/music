package com.example.model;

import android.content.Context;

public class StreContext extends ListenMusic
{
	private ListenMusic mstrategy = null;

	public StreContext(ListenMusic mstrategy)
	{
		this.mstrategy = mstrategy;
	}

	@Override
	public void stop()
	{
		if (mstrategy != null)
		{
			mstrategy.stop();
		}

	}

	@Override
	public void pause()
	{
		if (mstrategy != null)
		{
			mstrategy.pause();
		}
	}

	@Override
	public void next()
	{

	}

	@Override
	public void pre()
	{

	}

	@Override
	public boolean isplaying()
	{
		// TODO Auto-generated method stub
		if (mstrategy != null)
		{
			return mstrategy.isplaying();
		}
		else
		{
			return false;
		}
	}

	@Override
	public void initPlayer(Context mContext)
	{
		if (mstrategy != null)
		{
			mstrategy.initPlayer(mContext);
		}

	}

	@Override
	public void open(String url)
	{
		if (mstrategy != null)
		{
			mstrategy.open(url);
		}

	}

	@Override
	public long getDuration()
	{
		if (mstrategy != null)
		{
			return mstrategy.getDuration();
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void play()
	{
		if (mstrategy != null)
		{
			mstrategy.play();
		}

	}
}
