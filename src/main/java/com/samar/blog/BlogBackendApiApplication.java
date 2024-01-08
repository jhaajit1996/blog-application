package com.samar.blog;

import java.util.ArrayList;
import java.util.List;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.samar.blog.config.AppConstants;
import com.samar.blog.entities.Role;
import com.samar.blog.repositories.RoleRepo;


@SpringBootApplication
public class BlogBackendApiApplication implements CommandLineRunner{
	
//	private HikariDataSource dataSource; 
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			
			if(this.roleRepo.findAll().size()==0) {
			
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			
			Role role2 = new Role();
			role.setId(AppConstants.NORMAL_USER);
			role.setName("NORMAL_USER");
			
			List<Role> roles = new ArrayList<Role>();
			
			roles.add(role);
			roles.add(role2);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(e->System.out.println(e.getName()));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
//	
//	@Bean
//    public HikariDataSource dataSource() {
//        HikariConfig config = new HikariConfig();
//        
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/blog-backend-api");
//        config.setUsername("root");
//        config.setPassword("root");
//       
//
//        dataSource = new HikariDataSource(config);
//        return dataSource;
//    }
//	
//	@PreDestroy
//	public void onShutdown() {
//		if(dataSource!=null) {
//			try {
//			dataSource.close();
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}

}
 