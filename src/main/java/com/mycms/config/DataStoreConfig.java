package com.mycms.config;

import java.io.Serializable;


import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;


import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mycms.domain.Contact;
import com.mycms.domain.Content;
import com.mycms.domain.Menu;
import com.mycms.domain.PageContent;
import com.mycms.domain.Role;
import com.mycms.domain.SiteInstance;
import com.mycms.domain.SitePage;
import com.mycms.domain.SiteUser;
import com.mycms.repository.ContactRepository;
import com.mycms.repository.ContentRepository;
import com.mycms.repository.MenuRepository;
import com.mycms.repository.PageContentRepository;
import com.mycms.repository.RoleRepository;
import com.mycms.repository.SiteInstanceRepository;
import com.mycms.repository.SitePageRepository;
import com.mycms.repository.SiteUserRepository;

@Configuration
@EnableMongoRepositories
public class DataStoreConfig {

	@Autowired
	BeanFactory beanFactory;
	@Autowired
	private Environment environment;

	@Bean
	public Mongo mongo() {
		Mongo db;
		try {
//			db = new Mongo(environment.getProperty("mongodb.host"),
//					Integer.parseInt(environment.getProperty("mongodb.port")));
			db = new Mongo();
		} catch (Exception e) {
			return null;
		}

		return db;
	}

//	@Bean
//	@Scope("prototype")
//	
//	public SitePage sitePage(){
//		SitePage sitePage = new SitePage();
//		sitePage.setPageContentRepository(pageContentRepository());
//		return sitePage;
//	}
	@Bean
	public MongoTemplate mongoTemplate() {
		MongoTemplate template = new MongoTemplate(mongo(),"mycmsdb");
		return template;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() {
		return new MongoDbFactory() {

			@Override
			public DB getDb(String dbName) throws DataAccessException {
				try {
					return mongo().getDB(dbName);
				} catch (Exception e) {
					return null;
				}

			}

			@Override
			public DB getDb() throws DataAccessException {
				try {
					return mongoTemplate().getDb();
				} catch (Exception e) {
					return null;
				}

			}
		};
	}
	
	@Bean
	public SiteInstanceRepository siteInstanceRepository() {
		return getNewMongoRepository(SiteInstanceRepository.class, SiteInstance.class,
				String.class);

	}
	@Bean
	public SiteUserRepository siteUserRepository() {
		return getNewMongoRepository(SiteUserRepository.class, SiteUser.class,
				String.class);

	}
	@Bean
	public SitePageRepository sitePageRepository() {
		return getNewMongoRepository(SitePageRepository.class, SitePage.class,
				String.class);

	}
	@Bean
	@DependsOn("annotationBeanConfigurerAspect")
	public PageContentRepository pageContentRepository() {
		return getNewMongoRepository(PageContentRepository.class, PageContent.class,
				String.class);

	}
	
	@Bean
	
	public AnnotationBeanConfigurerAspect annotationBeanConfigurerAspect(){
		return AnnotationBeanConfigurerAspect.aspectOf();
	}
	@Bean
	public ContactRepository contactRepository() {
		return getNewMongoRepository(ContactRepository.class, Contact.class,
				String.class);

	}

	@Bean
	public ContentRepository contentRepository() {
		return getNewMongoRepository(ContentRepository.class, Content.class,
				String.class);

	}

	@Bean
	public RoleRepository roleRepository() {
		return getNewMongoRepository(RoleRepository.class, Role.class,
				String.class);

	}

//	@Bean
//	public UserPasswordRecoveryRepository userPasswordRecoveryRepository() {
//		return getNewJpaRepository(UserPasswordRecoveryRepository.class,
//				UserPasswordRecovery.class, String.class);
//
//	}

	@Bean
	public MenuRepository menuRepository() {
		return getNewMongoRepository(MenuRepository.class, Menu.class,
				String.class);

	}

	


	

	private <K extends MongoRepository<T, TId>, T, TId extends Serializable> K getNewMongoRepository(
			Class<K> repositoryClass, Class<T> entityClass, Class<TId> idClass) {
		MongoRepositoryFactoryBean<K, T, TId> factory = new MongoRepositoryFactoryBean<K, T, TId>();
		factory.setMongoOperations(mongoTemplate());
		factory.setRepositoryInterface(repositoryClass);
		factory.afterPropertiesSet();

		return factory.getObject();
	}
}
