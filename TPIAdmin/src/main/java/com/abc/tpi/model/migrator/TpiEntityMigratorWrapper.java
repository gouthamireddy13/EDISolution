package com.abc.tpi.model.migrator;

import java.util.ArrayList;
import java.util.List;

import com.abc.tpi.model.poc.TpiManagedEntity;

public abstract class TpiEntityMigratorWrapper<T extends TpiManagedEntity> {

	private T t;
	private ObjectTracker tracker;
	private List<T> children;
	
	public TpiEntityMigratorWrapper(T entity) {
		t = entity;
		tracker = new ObjectTracker();
		tracker.setClassName(entity.getClass().getName());
		tracker.setSourceId(entity.getId());
		tracker.setSourceVersionNum(entity.getVersionNum());
	}
	
	public TpiEntityMigratorWrapper(T entity, T parent)
	{
		this(entity);
		tracker.setParentClassName(parent.getClass().getName());
		tracker.setParentId(parent.getId());
		tracker.setParentVersionNum(parent.getVersionNum());
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public ObjectTracker getTracker() {
		return tracker;
	}

	public void setTracker(ObjectTracker tracker) {
		this.tracker = tracker;
	}
	
	public void addChild(T child)
	{
		if (children==null)
		{
			children = new ArrayList<T>();
		}
		children.add(child);
	}
	
	public List<T> getChildren()
	{
		return children;
	}

}
