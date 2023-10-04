package com.example.newdigikala.Model;


import com.example.newdigikala.Login.Message;

import org.json.JSONObject;

import java.util.List;


import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("readamazing.php")
    Single<List<Product>> getProduct();
    @GET("readbanner.php")
    Single<List<Banner>> getBanners();
    @GET("timer.php")
    Single<MyTimer> getTimer();
    @FormUrlEncoded
    @POST("login.php")
    Single<Message> login(@Field("email") String email,@Field("pass") String pass);
    @FormUrlEncoded
    @POST("signup.php")
    Single<Message> signup(@Field("email") String email,@Field("pass")String pass);
    @GET("getdetail.php")
    Single<List<Detail>> getDetail(@Query("id") String id,@Query("user") String user);
    @GET("properties.php")
    Single<List<Properties>> getProperties();
    @GET("comments.php")
    Single<List<Comment>> getComments(@Query("id") String id);
    @GET("like.php")
    Single<Message> likeComment(@Query("id") String id);
    @GET("dislike.php")
    Single<Message> dislikeComment(@Query("id") String id);
    //@Body send Form json to server
    @POST("addcomment.php")
    Single<Message> sendPoint(@Body List<RatingModel> ratingModels);
    @GET("addcommentparam.php")
    Single<Message> sendParam(@Query("title") String title,@Query("positive") String positive,@Query("negative") String negative,@Query("passage") String passage,@Query("user") String user);
    @GET("search.php")
    Single<List<Product>> getSearchedProduct(@Query("search") String search);
    @GET("history.php")
    Single<List<HistoryModel>> getHistory(@Query("id") String id);
    @GET("getcat.php")
    Single<List<Cat>> getCats();
    @GET("gettab.php")
    Single<List<Product>> getTabItem(@Query("cat") String cat);
    @GET("filter.php")
    Single<List<Product>> getSortedList(@Query("cat") String cat,@Query("sort") int sort);
    @POST("filterparam.php")
    Single<Message> sendFilterParam(@Body List<JSONObject> jsonObjects);
    @GET("editprofile.php")
    Single<Message> updateProfile(@Query("email") String email,
                                  @Query("name") String name,
                                  @Query("family") String family,
                                  @Query("code") String code,
                                  @Query("home") String home,
                                  @Query("mobile") String mobile,
                                  @Query("birthday") String birthday,
                                  @Query("jensiat") int jensiat,
                                  @Query("khabarname") int khabarname,
                                  @Query("level") int level);
    @GET("favorite.php")
    Single<Message> addFavorite(@Query("email") String email,@Query("id") String id,@Query("parent") int parent,@Query("title") String title);
    @GET("getfavorite.php")
    Single<List<Favorite>> getFavorite(@Query("email") String email);
    @GET("deletefav.php")
    Single<Message> deleteFav(@Query("id") String id);
    @GET("addbasket.php")
    Single<Message> addToBasket(@Query("product_id") String id,
                                @Query("email") String email);

    @GET("getbasketcount.php")
    Single<Message> getBasketCount(@Query("email") String email);

    @GET("basketlist.php")
    Single<List<Basket>> getBasketList(@Query("email") String email);

    @GET("deletebasket.php")
    Single<Message> deleteBasketItem(@Query("id") String basketId);

    @GET("checkout.php")
    Single<List<Basket>> updateBasket(@Query("order_id") String orderId,
                                      @Query("email") String email);

}
