package com.qrpos.action.servlet;


public class LookupJNDI {

	// private static IProductService productService;
	//
	// // The app name is the application name of the deployed EJBs. This is
	// // typically the ear name
	// // without the .ear suffix. However, the application name could be
	// // overridden in the application.xml of the
	// // EJB deployment on the server.
	// // Since we haven't deployed the application as a .ear, the app name for
	// us
	// // will be an empty string
	// private static final String appName = "";
	//
	// // This is the module name of the deployed EJBs on the server. This is
	// // typically the jar name of the
	// // EJB deployment, without the .jar suffix, but can be overridden via the
	// // ejb-jar.xml
	// // In this example, we have deployed the EJBs in a
	// // jboss-as-ejb-remote-app.jar, so the module name is
	// // jboss-as-ejb-remote-app
	// private static final String moduleName = "caferes";
	//
	// // AS7 allows each deployment to have an (optional) distinct name. We
	// // haven't specified a distinct name for
	// // our EJB deployment, so this is an empty string
	// private static final String distinctName = "";
	//
	// public static ICategoryService lookupCategoryService() throws
	// NamingException {
	//
	// if (categoryService == null) {
	//
	// Context context = getInitialContext();
	//
	// // The EJB name which by default is the simple class name of the
	// // bean implementation class
	// final String beanName = CategoryService.class.getSimpleName();
	// // the remote view fully qualified class name
	// final String viewClassName = ICategoryService.class.getName();
	// // let's do the lookup
	// categoryService = (ICategoryService) context.lookup("ejb:" + appName +
	// "/" + moduleName + "/"
	// + distinctName + "/" + beanName + "!" + viewClassName);
	//
	// }
	//
	// return categoryService;
	//
	// }
	//
	// public static IProductService lookupProductService() throws
	// NamingException {
	//
	// if (productService == null) {
	// Context context = getInitialContext();
	//
	// // The EJB name which by default is the simple class name of the
	// // bean implementation class
	// final String beanName = ProductService.class.getSimpleName();
	// // the remote view fully qualified class name
	// final String viewClassName = IProductService.class.getName();
	// // let's do the lookup
	// productService = (IProductService) context.lookup("ejb:" + appName + "/"
	// + moduleName + "/" + distinctName
	// + "/" + beanName + "!" + viewClassName);
	//
	// }
	//
	// return productService;
	//
	// }
	//
	// private static Context getInitialContext() throws NamingException {
	// final Hashtable jndiProperties = new Hashtable();
	// // jndiProperties.put("jboss.naming.client.ejb.context", true);
	// jndiProperties.put(Context.URL_PKG_PREFIXES,
	// "org.jboss.ejb.client.naming");
	// final Context context = new InitialContext(jndiProperties);
	// return context;
	// }

}