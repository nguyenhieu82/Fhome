package vn.itplus.AD0318E.Fhome.rest;

import vn.itplus.AD0318E.Fhome.model.ContactResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * chua cac apis trong du an
 */
public interface APIs {

    //xay dung api de lay contact
    @GET("contacts/")
    Call<ContactResponse> getContacts();
}
