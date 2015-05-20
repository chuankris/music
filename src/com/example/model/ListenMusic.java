package com.example.model;

import android.content.Context;

public abstract class ListenMusic
{
	public abstract void stop();

	public abstract void play();

	public abstract void pause();

	public abstract void next();

	public abstract void pre();

	public abstract boolean isplaying();

	public abstract void initPlayer(Context mContext);

	public abstract void open(String url);

	public abstract long getDuration();
}
