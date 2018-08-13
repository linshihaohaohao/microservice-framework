package cn.com.bluemoon.zookeeper.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bluemoon.zookeeper.service.ZookeeperService;

@Service
public class ZookeeperServiceImpl implements ZookeeperService{

	@Autowired
	private CuratorFramework client;
	
	@Override
	public boolean createNode(String path, Object dataObj) {
		try {
			client.create().creatingParentContainersIfNeeded().forPath(path, this.toByteArray(dataObj));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean createNodeWithMode(String path, Object dataObj, CreateMode mode) {
		try {
			client.create().creatingParentContainersIfNeeded().withMode(mode).forPath(path, this.toByteArray(dataObj));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteNode(String path) {
		try {
			client.delete().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deleteNodeWithChildren(String path) {
		try {
			client.delete().deletingChildrenIfNeeded().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean setNodeData(String path, Object dataObj) {
		try {
			client.setData().forPath(path, this.toByteArray(dataObj));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public Stat checkNodeExists(String path) {
		try {
			Stat stat = client.checkExists().forPath(path);
			if(stat != null){  
                return stat;
            } 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Object getNodeData(String path) {
		Object dataObj = null;
		try {
			byte[] data = client.getData().forPath(path);
			dataObj = this.toObject(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> getNodeChildren(String path) {
		List<String> children = null;
		try {
			children = client.getChildren().forPath("path");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return children;
	}
	
	/**
	 * 描述 : <Object转byte[]>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param obj
	 * @return
	 */
	private byte[] toByteArray(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
			oos.close();
			bos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return bytes;
	}
	
	/**
	 * 描述 : <byte[]转Object>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param bytes
	 * @return
	 */
	private Object toObject(byte[] bytes) {
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bis);
			obj = ois.readObject();
			ois.close();
			bis.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
