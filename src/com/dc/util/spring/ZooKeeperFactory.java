package com.dc.util.spring;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.FactoryBean;

public class ZooKeeperFactory implements FactoryBean<ZooKeeper>, Watcher {
	
	private static final Class<ZooKeeper> CLAZZ = ZooKeeper.class;
	
	private static final Logger logger = Logger.getLogger(CLAZZ);

	private String connectStrings = "172.16.3.42:2181,172.16.3.65:2181,172.16.3.24:2181";

	private CountDownLatch connectedSignal = new CountDownLatch(1);

	private int sleepTime = 3000;

	@Override
	public ZooKeeper getObject() throws Exception {

		logger.debug("zookeeper:" + this.connectStrings);
		
		ZooKeeper zk = new ZooKeeper(this.connectStrings, this.sleepTime, this);

		connectedSignal.await(); 

		return zk;
	}

	@Override
	public Class<ZooKeeper> getObjectType() {

		return CLAZZ;
	}

	@Override
	public boolean isSingleton() {
		
		return true;
	}

	public String getConnectStrings() {
		return connectStrings;
	}

	public void setConnectStrings(String connectStrings) {
		this.connectStrings = connectStrings;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	synchronized public void process(WatchedEvent event) {
		if (event.getState() == KeeperState.SyncConnected) {
			connectedSignal.countDown(); 
		}
	}

}
