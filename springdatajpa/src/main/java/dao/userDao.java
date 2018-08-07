package dao;


import entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
public interface userDao extends Repository<user,Integer> {
    user getById(Integer id);
    user findByIdAndName(Integer id,String name);


}
