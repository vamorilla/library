
package com.library.Library.repository;

import com.library.Library.entities.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Autor, Integer>{
  
}
