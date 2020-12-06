package space.pal.sig.old.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import space.pal.sig.old.model.Fact;

/**
 * SpaceNow
 * Created by Catalin on 7/29/2020
 **/
@Dao
public interface FactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Fact fact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(Fact... facts);

    @Query("delete from facts")
    Completable delete();

    @Query("select * from facts")
    LiveData<List<Fact>> findAll();

}
