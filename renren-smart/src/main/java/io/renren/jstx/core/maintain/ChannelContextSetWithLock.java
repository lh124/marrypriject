package io.renren.jstx.core.maintain;

import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.ObjWithLock;
import io.renren.jstx.core.intf.Packet;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * 
 * @author tanyaowu 
 * 2017年4月1日 上午9:35:09
 */
public class ChannelContextSetWithLock<SessionContext, P extends Packet, R>
{

	/** remoteAndChannelContext key: "ip:port" value: ChannelContext. */
	private ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> setWithLock = new ObjWithLock<Set<ChannelContext<SessionContext, P, R>>>(new HashSet<ChannelContext<SessionContext, P, R>>());

	/**
	 * Adds the.
	 *
	 * @param channelContext the channel context
	 */
	public void add(ChannelContext<SessionContext, P, R> channelContext)
	{
		Lock lock = setWithLock.getLock().writeLock();

		try
		{
			lock.lock();
			Set<ChannelContext<SessionContext, P, R>> m = setWithLock.getObj();
			m.add(channelContext);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}

	/**
	 * Removes the.
	 *
	 * @param channelContext the channel context
	 * @return the channel context
	 */
	public boolean remove(ChannelContext<SessionContext, P, R> channelContext)
	{
		Lock lock = setWithLock.getLock().writeLock();

		try
		{
			lock.lock();
			Set<ChannelContext<SessionContext, P, R>> m = setWithLock.getObj();
			return m.remove(channelContext);
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}
	
	public int size()
	{
		Lock lock = setWithLock.getLock().readLock();

		try
		{
			lock.lock();
			Set<ChannelContext<SessionContext, P, R>> m = setWithLock.getObj();
			return m.size();
		} catch (Exception e)
		{
			throw e;
		} finally
		{
			lock.unlock();
		}
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public ObjWithLock<Set<ChannelContext<SessionContext, P, R>>> getSetWithLock()
	{
		return setWithLock;
	}

}
