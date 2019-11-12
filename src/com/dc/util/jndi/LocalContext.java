package com.dc.util.jndi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.apache.log4j.Logger;

/**
 * 本第jndi
 * @author 小俊俊
 *
 */
public class LocalContext implements javax.naming.Context,
		InitialContextFactory {

	private static final Logger logger = Logger.getLogger(LocalContext.class);

	private static final Context CONTEXT = new LocalContext();

	private static Map<String, Object> map = new HashMap<String, Object>();

	/**
	 * 初始化jndi
	 */
	public void init() {
		
		if(System.getProperty(Context.INITIAL_CONTEXT_FACTORY) != null) {
			
			return;
		}
		
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				LocalContext.class.getName());

		InputStream input = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("jndi.properties");

		Properties prop = new Properties();

		try {
			prop.load(input);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);

			System.exit(-1);
		}

		for (Object key : prop.keySet()) {

			map.put(key.toString(), prop.get(key));
		}
	}

	@Override
	public Object lookup(Name name) throws NamingException {

		return this.lookup(name.toString());
	}

	@Override
	public Object lookup(String name) throws NamingException {

		return this.map.get(name);
	}

	@Override
	public void bind(Name name, Object obj) throws NamingException {

		this.map.put(name.toString(), obj);
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {

		this.map.put(name, obj);
	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {

		this.rebind(name.toString(), obj);
	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {

		this.map.put(name, obj);
	}

	@Override
	public void unbind(Name name) throws NamingException {

		this.unbind(name.toString());
	}

	@Override
	public void unbind(String name) throws NamingException {

		this.map.remove(name);
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {

		this.rename(oldName.toString(), newName.toString());
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {

		this.map.put(newName, this.map.get(oldName));
		this.map.remove(oldName);
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name)
			throws NamingException {

		return this.list(name.toString());
	}

	@Override
	public NamingEnumeration<NameClassPair> list(String name)
			throws NamingException {

		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(Name name)
			throws NamingException {

		return null;
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name)
			throws NamingException {
		//
		return null;
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		//

	}

	@Override
	public void destroySubcontext(String name) throws NamingException {

	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		//
		return null;
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		//
		return null;
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		//
		return null;
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		//
		return null;
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {

		return null;
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		//
		return null;
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {

		return null;
	}

	@Override
	public String composeName(String name, String prefix)
			throws NamingException {

		return null;
	}

	@Override
	public Object addToEnvironment(String propName, Object propVal)
			throws NamingException {

		return null;
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {

		return null;
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {

		return null;
	}

	@Override
	public void close() throws NamingException {

	}

	@Override
	public String getNameInNamespace() throws NamingException {

		return null;
	}

	@Override
	public Context getInitialContext(Hashtable<?, ?> environment)
			throws NamingException {

		return CONTEXT;
	}

}
