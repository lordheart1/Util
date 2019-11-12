package com.dc.util.ice.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.zeroc.Ice.Communicator;

import com.dc.util.ice.IceClientConfig;

public class IceClientConfigImpl implements IceClientConfig ,Watcher{

	private static final Logger logger = Logger.getLogger(IceClientConfigImpl.class);
	
	private static final String ICE = "/ice/bill";
	
	private static final String COUNT_FIX = "_count";
	
	private Properties ICE_PROPERTIES;
	
	private ZooKeeper zooKeeper;
	
	@Resource(name = "ice")
	private  Communicator ic = null;
	
	
	@Override
	public String getIceNodeCount(String iceName, long busiId) {
		
		String serviceCountName = iceName + COUNT_FIX;
		
		String countString = ICE_PROPERTIES.getProperty(serviceCountName);
		
		logger.debug("iceName:" + iceName + " countString:" + countString);
		
		long count = Long.parseLong(countString);
		
		int nodeId = (int)(busiId % count);
		
		String nodeName = iceName+ nodeId;
		
		logger.debug("busiId:" + busiId + " nodeName:" + nodeName);
		
		return ICE_PROPERTIES.getProperty(nodeName);
		
	}

	@Override
	public void init() {
	
		InputStream sbs = null; 
		
		try {
			
			byte[] data = this.zooKeeper.getData(ICE, this, null);
			
			sbs = new ByteArrayInputStream(data); 
			
			Properties props = new Properties();
			
			props.load(sbs);
			
			ICE_PROPERTIES = props;
			
		} catch (KeeperException | InterruptedException | IOException e) {
			
			logger.error(e.getMessage(), e);
			return;
		} finally {
			
			if(sbs != null) {
				
				try {
					sbs.close();
				} catch (IOException e) {
					
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	public void process(WatchedEvent arg0) {
		
		this.init();
	}

	public ZooKeeper getZooKeeper() {
		return zooKeeper;
	}

	public void setZooKeeper(ZooKeeper zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

}
