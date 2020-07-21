package space.pal.sig.old.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import space.pal.sig.old.model.Apod;

@Dao
public interface ApodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Apod... apods);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Apod... apods);

    @Delete
    void delete(Apod... apods);

    @Query("select * from apod")
    LiveData<List<Apod>> findAll();

    @Query("select * from apod where date = :date limit 1")
    LiveData<Apod> findByDate(String date);

    @Query("select * from apod order by id desc limit 1")
    LiveData<Apod> findLast();

}
