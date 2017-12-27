package io.renren.jstx.core.maintain;

import io.renren.jstx.core.Aio;
import io.renren.jstx.core.ChannelContext;
import io.renren.jstx.core.GroupContext;
import io.renren.jstx.core.intf.Packet;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MaintainUtils
{
	private static Logger log = LoggerFactory.getLogger(MaintainUtils.class);

	/**
	 * 彻底删除，不再维护
	 * @param channelContext
	 *
	 * @author: tanyaowu
	 *
	 */
	public static <SessionContext, P extends Packet, R> void removeFromMaintain(ChannelContext<SessionContext, P, R> channelContext)
	{
		GroupContext<SessionContext, P, R> groupContext = channelContext.getGroupContext();
		try
		{
			groupContext.getConnections().remove(channelContext);
			groupContext.getConnecteds().remove(channelContext);
			groupContext.getCloseds().remove(channelContext);
			if (StringUtils.isNotBlank(channelContext.getUserid()))
			{
				try
				{
					Aio.unbindUser(channelContext);
				} catch (Throwable e)
				{
					log.error(e.toString(), e);
				}
			}
			Aio.unbindGroup(channelContext);
		} catch (Exception e1)
		{
			log.error(e1.toString(), e1);
		}
	}

}
