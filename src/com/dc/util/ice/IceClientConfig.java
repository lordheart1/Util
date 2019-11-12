package com.dc.util.ice;

/**
 * ice节点路由控制器
 * @author 小俊俊
 *
 */
public interface IceClientConfig {

	/**
	 * 获取ice节点数
	 * @param iceName ice节点名
	 * @param busiId 分流id
	 * @return ice节点配置
	 */
	public String getIceNodeCount(String iceName,long busiId);

	
	public void init();
	
}
