package vn.kms.rabbitholes.repository;


import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * @author kietlam
 */
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {


}
