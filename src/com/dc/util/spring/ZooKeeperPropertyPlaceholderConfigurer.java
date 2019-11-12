package com.dc.util.spring;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    
	private static final Logger logger = Logger.getLogger(ZooKeeperPropertyPlaceholderConfigurer.class);
	
    public static final String PATH = "/spring";
    
    private ZooKeeper zookeeper;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
            throws BeansException {

        
        try {
            fillCustomProperties(props);
            
            if(logger.isDebugEnabled()) {
            
            	logger.debug("props" + props);
            }
        }
        catch (Exception e) {
          
        	logger.error(e.getMessage(), e);
        }
        
        super.processProperties(beanFactoryToProcess, props);
    }

    private void fillCustomProperties(Properties props) throws Exception {
    	
    	
    	List<String> childs = this.zookeeper.getChildren(this.PATH, false);
    	
    	for(String child : childs) {
    	
    		byte[] data = getData(props,this.PATH + "/" +child);
    		fillProperties(props, data);
    	}
    }

    private void fillProperties(Properties props, byte[] data) throws UnsupportedEncodingException {
    	
    	InputStream sbs = new ByteArrayInputStream(data); 
    	
    	try {
			props.load(sbs);
			sbs.close();
		} catch (IOException e) {
			
			logger.error(e.getMessage(), e);
		}
    }

    private byte[] getData(Properties props,String path) throws Exception {

		return this.zookeeper.getData(path, null, null);
    }

	public ZooKeeper getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZooKeeper zookeeper) {
		this.zookeeper = zookeeper;
	}

}