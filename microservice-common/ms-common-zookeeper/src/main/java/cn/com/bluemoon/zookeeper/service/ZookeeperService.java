package cn.com.bluemoon.zookeeper.service;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public interface ZookeeperService {

	/**
	 * 创建节点
	 * @param path
	 * @param dataObj
	 * @return
	 */
	boolean createNode(String path, Object dataObj);

	/**
	 * 创建节点（可选择创建模式）
	 * @param path
	 * @param dataObj
	 * @param mode
	 * @return
	 */
	boolean createNodeWithMode(String path, Object dataObj, CreateMode mode);

	/**
	 * 删除节点（只能删除叶子节点）
	 * @param path
	 * @return
	 */
	boolean deleteNode(String path);

	/**
	 * 删除节点（包含节点下的子节点）
	 * @param path
	 * @return
	 */
	boolean deleteNodeWithChildren(String path);

	/**
	 * 更新节点存储的值
	 * @param path
	 * @param dataObj
	 * @return
	 */
	boolean setNodeData(String path, Object dataObj);

	/**
	 * 检查节点是否存在，如果存在则返回节点状态
	 * @param path
	 * @return
	 */
	Stat checkNodeExists(String path);

	/**
	 * 获取节点存储的值
	 * @param path
	 * @return
	 */
	Object getNodeData(String path);

	/**
	 * 获取节点下子节点path数组
	 * @param path
	 * @return
	 */
	List<String> getNodeChildren(String path);

}
