package space.pal.sig.model.database;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Completable;
import space.pal.sig.model.Launch;

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

    @Query("select * from launches where timestamp < :timestamp order by timestamp desc")
    DataSource.Factory<Integer, Launch> findAllByTimestampLowerThan(Long timestamp);

}
