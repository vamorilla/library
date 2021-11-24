
package com.library.Library.repository;

import com.library.Library.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer>{
    
}
