package space.pal.sig.model.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import space.pal.sig.model.Apod;

/**
 * SpaceNow
 * Created by Catalin on 7/24/2020
 **/
@Dao
public interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Apod apod);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Apod... apods);

    @Query("delete from apod")
    Completable delete();

    @Query("select * from apod order by date desc")
    DataSource.Factory<Integer, Apod> findAll();

    @Query("select * from apod order by date desc limit :limit")
    LiveData<List<Apod>> findAllWithLimit(int limit);

}
