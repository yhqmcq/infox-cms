package com.infox.common.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;


@Repository
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDaoI<T> {

	private Logger logger = Logger.getLogger(BaseDaoImpl.class) ;
	
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T o) throws RuntimeException {
		try {
			if (o != null) {
				Serializable seri = this.getCurrentSession().save(o) ;
				return seri ;
			}
		} catch (Exception e) {
			logger.error("持久化数据发生异常={"+e+"}") ;
			throw new RuntimeException("持久化数据发生异常：" + e.getMessage()) ;
		}
		return null;
	}

	@Override
	public T get(Class<T> c, Serializable id) throws RuntimeException {
		try {
			return (T) this.getCurrentSession().get(c, id);
		} catch(RuntimeException e) {
			logger.error("根据ID加载数据异常={"+e+"}") ;
			throw new RuntimeException("根据ID加载数据异常：" + e.getMessage()) ;
		}
	}

	@Override
	public T get(String hql) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			List<T> l = q.list();
			if (l != null && l.size() > 0) {
				return l.get(0);
			}
		} catch(RuntimeException e) {
			logger.error("根据HQL检索单条记录异常，HQL："+hql+"}，异常：{"+e+"}") ;
			throw new RuntimeException("根据HQL检索单条记录异常：" + e.getMessage()) ;
		}
		
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			setParams(q, params) ;
			List<T> l = q.list();
			if (l != null && l.size() > 0) {
				return l.get(0);
			}
		} catch(RuntimeException e) {
			logger.error("根据HQL条件检索单条记录异常，HQL={"+hql+"\t 参数"+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL条件检索单条记录异常：" + e.getMessage()) ;
		}
		return null;
	}

	@Override
	@CacheEvict(value="eternalCache",allEntries=true)
	public void delete(T o) throws RuntimeException {
		try {
			if (o != null) {
				this.getCurrentSession().delete(o);
			}
		} catch(RuntimeException e) {
			logger.error("删除记录异常={"+e+"}") ;
			throw new RuntimeException("删除记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	@CacheEvict(value="eternalCache",allEntries=true)
	public void update(T o) throws RuntimeException {
		try {
			if (o != null) {
				this.getCurrentSession().update(o);
			}
		} catch(RuntimeException e) {
			logger.error("修改记录异常={"+e+"}") ;
			throw new RuntimeException("修改记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public void saveOrUpdate(T o) throws RuntimeException {
		try {
			if (o != null) {
				this.getCurrentSession().saveOrUpdate(o);
			}
		} catch(RuntimeException e) {
			logger.error("修改记录异常={"+e+"}") ;
			throw new RuntimeException("修改记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public List<T> find(String hql) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			return q.list();
		} catch(RuntimeException e) {
			logger.error("根据HQL检索记录异常，HQL={"+hql+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL检索记录异常：" + e.getMessage()) ;
		}
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			setParams(q, params) ;
			return q.list();
		} catch(RuntimeException e) {
			logger.error("根据HQL条件检索记录异常，HQL={"+hql+"\t 参数"+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL条件检索记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			setParams(q, params) ;
			return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch(RuntimeException e) {
			logger.error("根据HQL条件检索记录异常，HQL={"+hql+"\t 参数="+params + "\t 分页="+((page - 1) * rows)+"=="+rows+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL条件检索记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public List<T> find(String hql, int page, int rows) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch(RuntimeException e) {
			logger.error("根据HQL条件检索记录异常，HQL={"+hql+ "\t 分页="+((page - 1) * rows)+"=="+rows+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL条件检索记录异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public Long count(String hql) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			return (Long) q.uniqueResult();
		} catch(RuntimeException e) {
			logger.error("根据HQL统计记录数异常，HQL={"+hql+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL统计记录数异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public Long count(String hql, Map<String, Object> params) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			setParams(q, params) ;
			return (Long) q.uniqueResult();
		} catch(RuntimeException e) {
			logger.error("根据HQL统计记录数异常，HQL={"+hql+"\t 参数="+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("根据HQL统计记录数异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public int executeHql(String hql) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			return q.executeUpdate();
		} catch(RuntimeException e) {
			logger.error("执行本地HQL异常，HQL={"+hql+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地HQL异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) throws RuntimeException {
		try {
			Query q = this.getCurrentSession().createQuery(hql);
			setParams(q, params) ;
			return q.executeUpdate();
		} catch(RuntimeException e) {
			logger.error("执行本地HQL异常，HQL={"+hql+"\t 参数="+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地HQL异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public List<Object[]> findBySql(String sql) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			return q.list();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
	}

	@Override
	public List<Object[]> findBySql(String sql, int page, int rows) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+ "\t 分页="+((page - 1) * rows)+"=="+rows+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
		
	}

	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			return q.list();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+"\t 参数="+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
		
		
	}

	@Override
	public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+"\t 参数="+params+ "\t 分页="+((page - 1) * rows)+"=="+rows+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
	}

	@Override
	public int executeSql(String sql) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			return q.executeUpdate();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			return q.executeUpdate();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL异常，SQL={"+sql+"\t 参数="+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL异常：" + e.getMessage()) ;
		}
	}

	@Override
	public BigInteger countBySql(String sql) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			return (BigInteger) q.uniqueResult();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL统计记录数异常，SQL={"+sql+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL统计记录数异常：" + e.getMessage()) ;
		}
	}

	@Override
	public BigInteger countBySql(String sql, Map<String, Object> params) throws RuntimeException {
		try {
			SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					q.setParameter(key, params.get(key));
				}
			}
			return (BigInteger) q.uniqueResult();
		} catch(RuntimeException e) {
			logger.error("执行本地SQL统计记录数异常，SQL={"+sql+"\t 参数="+params+"}，异常={"+e+"}") ;
			throw new RuntimeException("执行本地SQL统计记录数异常：" + e.getMessage()) ;
		}
	}
	
	private void setParams(Query q, Map<String, Object> params) {
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
	}

}
