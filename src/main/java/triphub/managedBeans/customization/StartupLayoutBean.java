package triphub.managedBeans.customization;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import triphub.dao.customization.DatabaseLayoutDAO;

/**
 * This is a managed bean that gets initiated at startup and is responsible for any initialization 
 * tasks related to layouts in the system.
 * 
 * Being marked as {@code @Singleton} ensures that there's only one instance of this bean in the system.
 * The {@code @Startup} annotation ensures that this bean gets initialized upon application startup.
 */
@Singleton
@Startup
public class StartupLayoutBean {

	@Inject
	private DatabaseLayoutDAO layoutDAO;

	public StartupLayoutBean() {
	}

    /**
     * This method is called after the bean's construction and is used to perform any 
     * initialization tasks required. In this case, it initializes layouts using 
     * the {@code DatabaseLayoutDAO}.
     */
	@PostConstruct
	public void onStartup() {
		layoutDAO.initializeLayouts();
	}

}
