package space.pal.sig.old.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import space.pal.sig.old.model.Rocket;

@Dao
public interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Rocket... rockets);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Rocket> rockets);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Rocket... rockets);
    @Delete
    void delete(Rocket... rockets);

    @Query("select * from rocket")
    LiveData<List<Rocket>> findAll();

}
