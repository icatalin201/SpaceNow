package space.pal.sig.repository.service;

import retrofit2.Call;
import retrofit2.http.GET;
import space.pal.sig.old.model.dto.ApodDto;
import space.pal.sig.old.model.dto.FactDto;
import space.pal.sig.old.model.dto.SpaceResponse;

/**
 * SpaceNow
 * Created by Catalin on 7/19/2020
 **/
public interface SpaceService {

    @GET("/images.php")
    Call<SpaceResponse<ApodDto>> getImages();

    @GET("/facts.php")
    Call<SpaceResponse<FactDto>> getFacts();

}
