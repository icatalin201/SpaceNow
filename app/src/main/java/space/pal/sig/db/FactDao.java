package space.pal.sig.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import space.pal.sig.model.Fact;

@Dao
public interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Fact... facts);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Fact... facts);

    @Delete
    void delete(Fact... facts);

    @Query("select * from fact")
    LiveData<List<Fact>> findAll();

}
