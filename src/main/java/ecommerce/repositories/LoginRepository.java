package ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ecommerce.models.UsersModel;


public interface LoginRepository extends JpaRepository<UsersModel, Long>{

	UsersModel findByEmail(String email);

}
