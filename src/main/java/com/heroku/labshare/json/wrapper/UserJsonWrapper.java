package com.heroku.labshare.json.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.heroku.labshare.dto.UserJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserJsonWrapper implements Serializable {

    @JsonProperty("user")
    private UserJson userJson;
}
