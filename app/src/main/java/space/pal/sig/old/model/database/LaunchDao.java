package space.pal.sig.old.model.database;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import space.pal.sig.old.model.Launch;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
@Dao
public interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Launch launch);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Launch... launches);

    @Query("delete from launches")
    Completable delete();

    @Query("select * from launches where id = :id")
    LiveData<Launch> findById(String id);

    @Query("select * from launches where id = :id")
    Launch findByIdSync(String id);

    @Query("select * from launches where timestamp < :timestamp order by timestamp desc")
    DataSource.Factory<Integer, Launch> findAllPast(Long timestamp);

    @Query("select * from launches where timestamp > :timestamp order by timestamp asc")
    DataSource.Factory<Integer, Launch> findAllFuture(Long timestamp);

    @Query("select * from launches where favorite = :favorite order by timestamp desc")
    DataSource.Factory<Integer, Launch> findAllFavorite(boolean favorite);

}
