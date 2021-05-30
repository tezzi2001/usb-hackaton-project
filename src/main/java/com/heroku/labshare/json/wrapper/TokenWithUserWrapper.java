package com.heroku.labshare.json.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenWithUserWrapper {

    private String token;
    private String username;
    private String email;
    private int faculty;
    private int specialty;
    private boolean isApproved;
    private Long id;
}
