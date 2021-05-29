package com.heroku.labshare.json.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskIdWithUserIdWrapper {

    private Long userId;
    private Long taskId;
}
