package com.hbhs.common.dam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor@AllArgsConstructor
public class IdDamRequest implements DamRequest {
    private String id;

    @Override
    public String uniqueId() {
        return id;
    }
}
