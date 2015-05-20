package com.example.model;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;

public class OnlinePlayMusic extends ListenMusic
{
	private MediaPlayer mMainPlayer;
	private int mStreamVolume;
	private Context mContext;

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void play()
	{
		playorstart();
	}

	@Override
	public void pause()
	{
		playorstart();
	}

	@Override
	public void next()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pre()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isplaying()
	{
		if (mMainPlayer != null)
		{
			return mMainPlayer.isPlaying();
		}
		else
		{
			return false;
		}
	}

	@Override
	public void initPlayer(Context mContext)
	{
		this.mContext = mContext;
		mMainPlayer = new MediaPlayer();
		AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		mStreamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}

	private void playorstart()
	{
		if (isplaying())
		{
			mMainPlayer.pause();
		}
		else
		{
			mMainPlayer.start();
		}
	}

	private void startPlayer(final String url)
	{
		try
		{
			mMainPlayer.reset();
		}
		catch (Exception e)
		{
			mMainPlayer.release();
			mMainPlayer = new MediaPlayer();
			e.printStackTrace();
		}
		try
		{
			mMainPlayer.setDataSource(url);
			mMainPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMainPlayer.setVolume(mStreamVolume, mStreamVolume);
			mMainPlayer.prepareAsync();
			mMainPlayer.setOnCompletionListener(mOnCompletionListener);
			mMainPlayer.setOnErrorListener(mOnErrorListener);
			mMainPlayer.setOnPreparedListener(mOnpreParedListener);
			mMainPlayer.setOnInfoListener(mOnInfoListener);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private OnCompletionListener mOnCompletionListener = new OnCompletionListener()
	{
		@Override
		public void onCompletion(MediaPlayer mp)
		{
			//todo
		}
	};

	private OnInfoListener mOnInfoListener = new OnInfoListener()
	{

		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra)
		{
			//todo
			if (what == 703) // without cache data
			{
			}
			else if (what == 702) //cache data finish
			{
			}
			else if (what == 701)//cacheing data
			{
			}
			return true;
		}
	};

	private OnPreparedListener mOnpreParedListener = new OnPreparedListener()
	{
		@Override
		public void onPrepared(MediaPlayer mp)
		{
			mp.start();
		}
	};
	private OnErrorListener mOnErrorListener = new OnErrorListener()
	{
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra)
		{
			return false;
		}
	};

	@Override
	public void open(String url)
	{
		startPlayer(url);
	}

	@Override
	public long getDuration()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
