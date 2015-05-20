package com.example.model;

import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class LocalPlayMusic extends ListenMusic
{

	public final int ERROR_OCCUERED = 0;
	public final int TRACK_ENDED = 1;
	public final int PREPARED_ENDED = 2;
	private Context mContext;
	private MultiPlayer mPlayer;
	FileInputStream fis = null;

	@Override
	public void open(String url)
	{
		if (mPlayer != null)
		{
			mPlayer.open(url);
		}
	}

	@Override
	public void stop()
	{
		mPlayer.stop();
	}

	@Override
	public void play()
	{
		mPlayer.start();
	}

	@Override
	public void pause()
	{
		mPlayer.pause();
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
	public long getDuration()
	{
		return mPlayer.duration();
	}

	@Override
	public boolean isplaying()
	{
		return mPlayer.isPlaying();
	}

	@Override
	public void initPlayer(Context mContext)
	{
		// TODO Auto-generated method stub
		this.mContext = mContext;
		mPlayer = new MultiPlayer(mContext);
	}

	/**
	* palyer
	* @author chuankris
	*
	*/
	private class MultiPlayer
	{
		private MediaPlayer mMediaPlayer = null;
		private Handler mHandler;

		public MultiPlayer(Context context)
		{
			mMediaPlayer = new MediaPlayer();
			if (mMediaPlayer != null)
			{
				mMediaPlayer.setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK);
			}
			mContext = context;

		}

		/**
		 * 设置流媒体的播放
		 * @param path
		 */
		public void setDataSource(String path, long offset)
		{
			mMediaPlayer.reset();
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnBufferingUpdateListener(bufferingListener);
			mMediaPlayer.setOnCompletionListener(completeListener);
			mMediaPlayer.setOnErrorListener(errorlistener);
			mMediaPlayer.setOnPreparedListener(preparedListener);
			try
			{
				fis = new FileInputStream(path);
				if (fis != null)
				{
					mMediaPlayer.setDataSource(fis.getFD());
				}
				mMediaPlayer.prepare();
			}
			catch (IOException e)
			{
				return;
			}
			catch (IllegalArgumentException ex)
			{
				return;
			}
			catch (java.lang.RuntimeException e)
			{
				return;
			}

		}

		/**
		 * start to play file
		 */
		public void open(String path)
		{
			synchronized (this)
			{
				if (path == null)
				{
					return;
				}
				if (mPlayer != null)
				{
					mPlayer.stop();
					mPlayer.setHandler(mMediaPlayerHandler);
					mPlayer.setDataSource(path, 0);
				}
			}
		}

		private Handler mMediaPlayerHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case TRACK_ENDED:
					if (mContext != null)
					{
						TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
						if (tm.getCallState() != TelephonyManager.CALL_STATE_IDLE)
						{
							pause();
						}

					}
					break;
				case PREPARED_ENDED:
				case ERROR_OCCUERED:
					break;
				default:
					break;
				}
			}
		};

		/**
		 * pause to play file 
		 */
		private void pause()
		{
			if (mMediaPlayer != null)
				mMediaPlayer.pause();
		}

		/**
		 * stop to play file
		 */
		private void stop()
		{
			if (mMediaPlayer != null)
			{
				mMediaPlayer.stop();
				mMediaPlayer.reset();
			}
		}

		/**
		 * set the handler
		 */
		private void setHandler(Handler handler)
		{
			mHandler = handler;
		}

		private boolean isPlaying()
		{
			boolean flag = false;
			flag = mMediaPlayer.isPlaying();
			return flag;
		}

		private long duration()
		{
			long duration = 0;
			try
			{
				if (mMediaPlayer != null)
					duration = mMediaPlayer.getDuration();
			}
			catch (Exception e)
			{
				duration = 0;
			}
			return duration;
		}

		private int currentPosition()
		{
			int currentPosition = 0;
			try
			{
				if (mMediaPlayer != null)
					currentPosition = mMediaPlayer.getCurrentPosition();
			}
			catch (Exception e)
			{
				currentPosition = 0;
			}
			return currentPosition;
		}

		private long seekTo(long whereTo)
		{
			if (mMediaPlayer != null)
				mMediaPlayer.seekTo((int) whereTo);
			return whereTo;
		}

		private void release()
		{
			if (mMediaPlayer != null)
				mMediaPlayer.release();
		}

		private void start()
		{
			if (mPlayer != null)
			{
				mPlayer.start();
			}
		}

		MediaPlayer.OnBufferingUpdateListener bufferingListener = new MediaPlayer.OnBufferingUpdateListener()
		{

			@Override
			public void onBufferingUpdate(MediaPlayer mp, int percent)
			{
			}
		};

		MediaPlayer.OnCompletionListener completeListener = new MediaPlayer.OnCompletionListener()
		{

			@Override
			public void onCompletion(MediaPlayer mp)
			{
				if (mHandler != null)
				{
					mHandler.removeMessages(TRACK_ENDED);
					mHandler.sendMessage(mHandler.obtainMessage(TRACK_ENDED));
				}
			}

		};
		MediaPlayer.OnErrorListener errorlistener = new MediaPlayer.OnErrorListener()
		{
			@Override
			public boolean onError(MediaPlayer mp, int what, int extra)
			{
				if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED)
				{
					if (mMediaPlayer != null)
						mPlayer.release();
					mPlayer = new MultiPlayer(mContext);
					if (mContext != null)
					{
						Toast.makeText(mContext, "网路不给力", Toast.LENGTH_SHORT).show();
					}
					if (mHandler != null)
					{
						mHandler.sendMessage(mHandler.obtainMessage(ERROR_OCCUERED));
					}
					return true;
				}

				if (mContext != null)
				{
					Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
				}

				if (mHandler != null)
				{
					mHandler.sendMessage(mHandler.obtainMessage(ERROR_OCCUERED));
				}
				return false;
			}

		};

		MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener()
		{

			@Override
			public void onPrepared(MediaPlayer mp)
			{
				if (mHandler != null)
				{
					mHandler.removeMessages(PREPARED_ENDED);
					mHandler.sendMessage(mHandler.obtainMessage(PREPARED_ENDED));
				}
			}

		};
	}

}
